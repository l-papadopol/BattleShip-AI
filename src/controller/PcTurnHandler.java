/*
 * Classe helper che contiene le strategie di gioco del PC livelli 1, 2 e 3.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import model.GridSquare;
import model.ModelInterface;
import model.Player;
import model.Projectile;
import view.Messages;
import view.ViewInterface;

public class PcTurnHandler {
    private ModelInterface battle;
    private ViewInterface view;
    private Random random;
    private int difficultyLevel;
    private ProjectileHandler projectileHandler;
    private LinkedHashSet<Point> targets = new LinkedHashSet<>();

    public PcTurnHandler(ModelInterface battle, ViewInterface view, int difficultyLevel, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.difficultyLevel = difficultyLevel;
        this.projectileHandler = projectileHandler;
        this.random = new Random();
    }

    // Metodo principale per gestire il turno del PC in base al livello di difficoltà
    public boolean handlePcTurn(Player currentPlayer) {
        switch (difficultyLevel) {
            case 1:
                return playEasy(currentPlayer);
            case 2:
                return playMedium(currentPlayer);
            case 3:
                return playHard(currentPlayer);
            default:
                return playEasy(currentPlayer);
        }
    }

    /*
     * Livello 1: Modalità facile - tiro completamente casuale
     */
    private boolean playEasy(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point p = new Point(random.nextInt(dim), random.nextInt(dim));
        int type = random.nextInt(3) + 1; // 1: Normale, 2: Potente, 3: Speciale
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        
        view.showMsg(Messages.pcShootMsg(p.x, p.y));
        boolean hit = battle.executeTurn(p, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        return hit;
    }

    /*
     * Livello 2: Modalità "hunt and target"
     */
    private boolean playMedium(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point target;
        if (!targets.isEmpty()) {
            // Preleva il primo elemento e lo rimuove
            target = targets.iterator().next();
            targets.remove(target);
        } else {
            target = new Point(random.nextInt(dim), random.nextInt(dim));
            targets.add(target);
        }

        // Determina il tipo di proiettile:
        // Se la cella è già stata colpita (ma non completamente danneggiata), usa il proiettile potente;
        // con il 25% di probabilità forza il proiettile speciale, altrimenti quello normale.
        Player enemy = (currentPlayer == battle.getFirstPlayer()) ? battle.getSecondPlayer() : battle.getFirstPlayer();
        GridSquare gs = enemy.getPersonalGrid().getGridSquares()[target.x][target.y];
        int type = (random.nextDouble() < 0.25)
                   ? 3
                   : (gs.getIsHit() && gs.getDamageLevel() < gs.getMaxResistance() ? 2 : 1);
        
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        
        // Se il tiro va a segno, aggiunge le celle adiacenti (solo orizzontali e verticali) a targets
        if (hit) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (Math.abs(dx) + Math.abs(dy) == 1) {
                        int nx = target.x + dx, ny = target.y + dy;
                        if (nx >= 0 && nx < dim && ny >= 0 && ny < dim) {
                            targets.add(new Point(nx, ny));
                        }
                    }
                }
            }
        }
        return hit;
    }

    /*
     * Livello 3: Pseudo-AI
     */
    private boolean playHard(Player currentPlayer) {
        Player enemy = (currentPlayer == battle.getFirstPlayer())
                       ? battle.getSecondPlayer()
                       : battle.getFirstPlayer();
        int dim = currentPlayer.getPersonalGrid().getSize();

        // Verifica se esiste una "traccia di nave":
        // Cella colpita con danno > 0 (quindi non un miss) e non affondata.
        Point traccia = findPartialHitCellHard(enemy, dim);
        if (traccia != null) {
            // Modalità Target: continua a colpire le celle adiacenti alla traccia
            while (true) {
                List<Point> adjacentCandidates = getAdjacentCandidates(traccia, enemy, dim);
                if (adjacentCandidates.isEmpty()) {
                    break; // non ci sono candidati, esci dalla modalità Target
                }
                // Seleziona il candidato migliore in base al punteggio euristico
                Point target = chooseBestCandidate(adjacentCandidates, enemy, dim);
                int projectileType = (currentPlayer.getPowerfullProjectileQty() > 0) ? 2 : 1;
                boolean hit = doPcShot(currentPlayer, target, projectileType);
                if (hit) {
                    // Aggiorna la traccia con il target appena colpito
                    traccia = target;
                } else {
                    // Se il tiro va a vuoto, interrompi la modalità Target
                    break;
                }
            }
            return true;
        } else {
            // Modalità Hunt:
            Point target = huntModeHard(enemy, dim);
            double probability = estimateProbabilityHard(enemy, target, dim);
            int projectileType = (probability > 0.5 && currentPlayer.getSpecialProjectileQty() > 0) ? 3 : 1;
            return doPcShot(currentPlayer, target, projectileType);
        }
    }

    // Restituisce la prima cella della griglia nemica che è stata colpita con danno > 0 ma non affondata.
    private Point findPartialHitCellHard(Player enemy, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                if (squares[x][y].getIsHit() && squares[x][y].getDamageLevel() > 0 &&
                    squares[x][y].getDamageLevel() < squares[x][y].getMaxResistance()) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    // Restituisce una lista di celle adiacenti (orizzontali e verticali) alla cella "traccia"
    // che non sono state colpite e che non hanno raggiunto il danno massimo.
    private List<Point> getAdjacentCandidates(Point traccia, Player enemy, int dim) {
        List<Point> candidates = new ArrayList<>();
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        int[][] offsets = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for (int[] offset : offsets) {
            int nx = traccia.x + offset[0];
            int ny = traccia.y + offset[1];
            if (nx >= 0 && nx < dim && ny >= 0 && ny < dim) {
                // Aggiungi solo se la cella non è stata colpita e non è affondata
                if (!squares[nx][ny].getIsHit() &&
                    squares[nx][ny].getDamageLevel() < squares[nx][ny].getMaxResistance()) {
                    candidates.add(new Point(nx, ny));
                }
            }
        }
        return candidates;
    }

    // Seleziona il candidato con il punteggio più alto tra quelli passati.
    private Point chooseBestCandidate(List<Point> candidates, Player enemy, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> bestCandidates = new ArrayList<>();
        for (Point p : candidates) {
            int score = computePlacementScore(p.x, p.y, squares, dim);
            if (score > bestScore) {
                bestScore = score;
                bestCandidates.clear();
                bestCandidates.add(p);
            } else if (score == bestScore) {
                bestCandidates.add(p);
            }
        }
        if (!bestCandidates.isEmpty()) {
            return bestCandidates.get(random.nextInt(bestCandidates.size()));
        }
        return null;
    }

    // Calcola un punteggio per la cella (x,y) basato sul numero di posizionamenti possibili
    // in orizzontale e verticale intorno ad essa.
    private int computePlacementScore(int x, int y, GridSquare[][] squares, int dim) {
        int countLeft = 0;
        for (int i = x - 1; i >= 0; i--) {
            if (!squares[i][y].getIsHit())
                countLeft++;
            else break;
        }
        int countRight = 0;
        for (int i = x + 1; i < dim; i++) {
            if (!squares[i][y].getIsHit())
                countRight++;
            else break;
        }
        int horizontal = countLeft + countRight + 1;
        
        int countUp = 0;
        for (int i = y - 1; i >= 0; i--) {
            if (!squares[x][i].getIsHit())
                countUp++;
            else break;
        }
        int countDown = 0;
        for (int i = y + 1; i < dim; i++) {
            if (!squares[x][i].getIsHit())
                countDown++;
            else break;
        }
        int vertical = countUp + countDown + 1;
        
        return Math.max(horizontal, vertical);
    }

    // Modalità Hunt: valuta tutte le celle non colpite (escludendo i miss in acqua)
    // e restituisce quella con il punteggio massimo.
    private Point huntModeHard(Player enemy, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> bestCells = new ArrayList<>();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                // Escludi le celle già colpite (i miss in acqua hanno isHit == true con damageLevel == 0)
                if (!squares[x][y].getIsHit()) {
                    int score = computePlacementScore(x, y, squares, dim);
                    if (score > bestScore) {
                        bestScore = score;
                        bestCells.clear();
                        bestCells.add(new Point(x, y));
                    } else if (score == bestScore) {
                        bestCells.add(new Point(x, y));
                    }
                }
            }
        }
        if (!bestCells.isEmpty()) {
            return bestCells.get(random.nextInt(bestCells.size()));
        }
        // Fallback: se non ci sono candidati, scegli una cella casuale.
        return new Point(random.nextInt(dim), random.nextInt(dim));
    }

    // Stima la probabilità che la cella target contenga parte di una nave, normalizzando il punteggio.
    private double estimateProbabilityHard(Player enemy, Point target, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        int score = computePlacementScore(target.x, target.y, squares, dim);
        return (double) score / dim;
    }

    // Esegue il tiro sul bersaglio scelto, mostrando i messaggi appropriati.
    private boolean doPcShot(Player currentPlayer, Point target, int projectileType) {
        Projectile projectile = projectileHandler.makeProjectile(projectileType, currentPlayer);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        return hit;
    }
}

