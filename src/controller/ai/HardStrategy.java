package controller.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.AITurnStrategy;
import controller.handlers.ProjectileHandler;
import model.ModelInterface;
import model.entities.GridSquare;
import model.entities.Player;
import model.entities.Projectile;
import view.ViewInterface;
import view.components.Messages;

/**
 * Classe che implementa la logica di gioco livello "difficile".
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa strategia analizza la griglia del nemico per identificare celle parzialmente colpite e, 
 * in base a ciò, decide il prossimo tiro. Se non viene individuato alcun bersaglio parziale,
 * viene utilizzata la modalità di "caccia" (hunt mode) per selezionare il tiro in base a un punteggio.
 */
public class HardStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private Random random = new Random();

    /**
     * Costruisce una nuova istanza di HardStrategy.
     *
     * @param battle il modello del gioco
     * @param view la vista del gioco
     * @param projectileHandler il gestore dei proiettili
     */
    public HardStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    /**
     * Esegue il turno del giocatore controllato dall'IA in modalità "difficile".
     * Se viene individuata una cella con danni parziali, si tenta di colpire le celle adiacenti
     * alttrimenti, viene attivata la modalità di "caccia" per selezionare il bersaglio migliore.
     *
     * @param currentPlayer il giocatore corrente controllato dall'IA
     * @return {@code true} se il tiro ha colpito un bersaglio, {@code false} altrimenti
     */
    @Override
    public boolean executeTurn(Player currentPlayer) {
        Player enemy = (currentPlayer == battle.getFirstPlayer()) ? battle.getSecondPlayer() : battle.getFirstPlayer();
        int dim = currentPlayer.getPersonalGrid().getSize();

        Point knownTarget = findPartialHitCell(enemy, dim);
        if (knownTarget != null) {
            while (true) {
                List<Point> adjacent = getAdjacentCandidates(knownTarget, enemy, dim);
                if (adjacent.isEmpty()) break;
                Point target = chooseBestCandidate(adjacent, enemy, dim);
                int type = (currentPlayer.getPowerfullProjectileQty() > 0) ? 2 : 1;
                boolean hit = doPcShot(currentPlayer, target, type);
                if (hit) {
                    knownTarget = target;
                } else {
                    break;
                }
            }
            return true;
        } else {
            Point target = huntMode(enemy, dim);
            double prob = estimateProbability(enemy, target, dim);
            int type = (prob > 0.5 && currentPlayer.getSpecialProjectileQty() > 0) ? 3 : 1;
            return doPcShot(currentPlayer, target, type);
        }
    }

    /**
     * Cerca una cella del nemico che è stata colpita parzialmente, cioè che ha subito danni ma non è ancora completamente distrutta.
     *
     * @param enemy il giocatore nemico
     * @param dim la dimensione della griglia
     * @return un {@link Point} che rappresenta le coordinate della cella parzialmente colpita ooppure {@code null} se non viene trovata alcuna cella idonea
     */
    private Point findPartialHitCell(Player enemy, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                GridSquare cell = squares[x][y];
                if (cell.getIsHit() && cell.getDamageLevel() > 0 && cell.getDamageLevel() < cell.getMaxResistance()) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Restituisce le celle adiacenti a una data posizione che possono essere considerate come bersagli.
     *
     * @param origin il punto di origine da cui calcolare le celle adiacenti
     * @param enemy il giocatore nemico
     * @param dim la dimensione della griglia
     * @return una lista di {@link Point} contenente le coordinate delle celle adiacenti non ancora colpite
     */
    private List<Point> getAdjacentCandidates(Point origin, Player enemy, int dim) {
        List<Point> result = new ArrayList<>();
        GridSquare[][] grid = enemy.getPersonalGrid().getGridSquares();
        int[][] deltas = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for (int[] d : deltas) {
            int nx = origin.x + d[0], ny = origin.y + d[1];
            if (nx >= 0 && nx < dim && ny >= 0 && ny < dim) {
                GridSquare g = grid[nx][ny];
                if (!g.getIsHit() && g.getDamageLevel() < g.getMaxResistance()) {
                    result.add(new Point(nx, ny));
                }
            }
        }
        return result;
    }

    /**
     * Seleziona il miglior candidato tra quelli disponibili basandosi su un punteggio calcolato.
     *
     * @param candidates la lista dei punti candidati
     * @param enemy il giocatore nemico
     * @param dim la dimensione della griglia
     * @return il {@link Point} migliore da prendere come bersaglio
     */
    private Point chooseBestCandidate(List<Point> candidates, Player enemy, int dim) {
        GridSquare[][] grid = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> best = new ArrayList<>();
        for (Point p : candidates) {
            int score = computePlacementScore(p.x, p.y, grid, dim);
            if (score > bestScore) {
                bestScore = score;
                best.clear();
                best.add(p);
            } else if (score == bestScore) {
                best.add(p);
            }
        }
        return best.get(random.nextInt(best.size()));
    }

    /**
     * Calcola un punteggio per la posizione specificata in base alla disponibilità delle celle adiacenti.
     *
     * @param x la coordinata x della cella
     * @param y la coordinata y della cella
     * @param g la matrice di {@link GridSquare} della griglia
     * @param dim la dimensione della griglia
     * @return un punteggio intero che rappresenta la bontà della posizione
     */
    private int computePlacementScore(int x, int y, GridSquare[][] g, int dim) {
        int left = 0, right = 0, up = 0, down = 0;
        for (int i = x - 1; i >= 0 && !g[i][y].getIsHit(); i--) {
            left++;
        }
        for (int i = x + 1; i < dim && !g[i][y].getIsHit(); i++) {
            right++;
        }
        for (int i = y - 1; i >= 0 && !g[x][i].getIsHit(); i--) {
            up++;
        }
        for (int i = y + 1; i < dim && !g[x][i].getIsHit(); i++) {
            down++;
        }
        return Math.max(left + right + 1, up + down + 1);
    }

    /**
     * Modalità di "caccia" per individuare il bersaglio migliore.
     * Analizza tutta la griglia del nemico e sceglie la cella con il punteggio più alto.
     *
     * @param enemy il giocatore nemico
     * @param dim la dimensione della griglia
     * @return un {@link Point} che rappresenta il bersaglio selezionato
     */
    private Point huntMode(Player enemy, int dim) {
        GridSquare[][] g = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> best = new ArrayList<>();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                if (!g[x][y].getIsHit()) {
                    int score = computePlacementScore(x, y, g, dim);
                    if (score > bestScore) {
                        bestScore = score;
                        best.clear();
                        best.add(new Point(x, y));
                    } else if (score == bestScore) {
                        best.add(new Point(x, y));
                    }
                }
            }
        }
        return best.isEmpty() ? new Point(random.nextInt(dim), random.nextInt(dim))
                : best.get(random.nextInt(best.size()));
    }

    /**
     * Stima la probabilità che il bersaglio individuato sia un buon candidato,
     * basandosi sul punteggio della posizione.
     *
     * @param enemy il giocatore nemico
     * @param p il punto candidato
     * @param dim la dimensione della griglia
     * @return un valore {@code double} che rappresenta la probabilità stimata
     */
    private double estimateProbability(Player enemy, Point p, int dim) {
        GridSquare[][] g = enemy.getPersonalGrid().getGridSquares();
        return (double) computePlacementScore(p.x, p.y, g, dim) / dim;
    }

    /**
     * Esegue il tiro dell'IA.
     * Crea un proiettile in base al tipo specificato, mostra il messaggio di tiro e determina se il tiro ha colpito.
     *
     * @param player il giocatore corrente che esegue il tiro
     * @param target il punto bersaglio
     * @param type il tipo di proiettile da utilizzare
     * @return {@code true} se il tiro ha colpito un bersaglio, {@code false} altrimenti
     */
    private boolean doPcShot(Player player, Point target, int type) {
        Projectile p = projectileHandler.makeProjectile(type, player);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, p);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        return hit;
    }
}
