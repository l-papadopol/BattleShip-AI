/*
 * classe helper che contiene le strategie di gioco del PC
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Player;
import model.ModelInterface;
import model.Projectile;
import model.GridSquare;
import view.Messages;
import view.ViewInterface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PcTurnHandler {
    private ModelInterface battle;
    private ViewInterface view;
    private Random random;
    private int difficultyLevel;
    private ProjectileHandler projectileHandler;
    
    // Strategie per modalità media ("hunt and target")
    private Set<Point> mediumFired = new HashSet<>();
    private List<Point> targetQueue = new ArrayList<>();
    
    // Strategie per modalità alta (basata sulla probabilità)
    private Set<Point> hardFired = new HashSet<>();
    private Set<Point> hardHits = new HashSet<>();

    public PcTurnHandler(ModelInterface battle, ViewInterface view, int difficultyLevel, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.difficultyLevel = difficultyLevel;
        this.projectileHandler = projectileHandler;
        this.random = new Random();
    }
    
    public boolean handlePcTurn(Player currentPlayer) {
    	boolean hit = false;
        if (difficultyLevel == 1) {
            hit = playEasyOpponent(currentPlayer);
        } else if (difficultyLevel == 2) {
            hit = playMediumOpponent(currentPlayer);
        } else if (difficultyLevel == 3) {
            hit = playHardOpponent(currentPlayer);
        }
        return hit;
    }
    
    // Difficoltà Facile: il PC spara a caso, scegliendo casualmente tra i proiettili (Normale, Potente, Speciale).
    private boolean playEasyOpponent(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        int x = random.nextInt(dim);
        int y = random.nextInt(dim);
        int type = random.nextInt(3) + 1; // 1: Normale, 2: Potente, 3: Speciale
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        
        view.showMsg(Messages.pcShootMsg(x, y));
        boolean hit = battle.executeTurn(new Point(x, y), projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        return hit;
    }
    
    // Difficoltà Media: modalità "hunt and target"
    private boolean playMediumOpponent(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point target = null;
        boolean inTargetMode = false;
        
        // Se abbiamo già candidati nella targetQueue, siamo in target mode.
        if (!targetQueue.isEmpty()) {
            inTargetMode = true;
            target = targetQueue.remove(0);
            while (target != null && mediumFired.contains(target) && !targetQueue.isEmpty()) {
                target = targetQueue.remove(0);
            }
            if (target != null && mediumFired.contains(target)) {
                target = null;
                inTargetMode = false;
            }
        }
        
        // Se non siamo in target mode, scegliamo una cella casuale non ancora colpita.
        if (target == null) {
            inTargetMode = false;
            do {
                int x = random.nextInt(dim);
                int y = random.nextInt(dim);
                target = new Point(x, y);
            } while (mediumFired.contains(target));
        }
        mediumFired.add(target);
        
        int type;
        if (inTargetMode) {
            // Recuperiamo l'avversario
            Player enemy = (currentPlayer == battle.getFirstPlayer()) ? battle.getSecondPlayer() : battle.getFirstPlayer();
            // Otteniamo la griglia personale dell'avversario
            GridSquare[][] enemySquares = enemy.getPersonalGrid().getGridSquares();
            int currentDamage = enemySquares[target.x][target.y].getDamageLevel();
            // Se la cella ha subito solo 1/4 di danno (damage == 1), usiamo il proiettile Potente per "finire" la sezione;
            // altrimenti, se il danno è maggiore (cioè sono necessari colpi multipli), usiamo il proiettile Normale.
            // Inoltre, con probabilità del 20%, forziamo l'uso del proiettile Speciale.
            if (random.nextDouble() < 0.2) {
                type = 3; // Speciale
            } else {
                type = (currentDamage == 1) ? 2 : 1;
            }
        } else {
            // Modalità hunt: uso il proiettile Normale.
            type = 1;
        }
        
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        
        // Se il colpo va a segno, aggiungiamo le celle adiacenti alla targetQueue.
        if (hit) {
            int x = target.x;
            int y = target.y;
            Point[] neighbors = new Point[] {
                new Point(x - 1, y),
                new Point(x + 1, y),
                new Point(x, y - 1),
                new Point(x, y + 1)
            };
            for (Point p : neighbors) {
                if (p.x >= 0 && p.x < dim && p.y >= 0 && p.y < dim &&
                    !mediumFired.contains(p) && !targetQueue.contains(p)) {
                    targetQueue.add(p);
                }
            }
        }
        return hit;
    }
    
    // Difficoltà Alta: pseudo-AI basata sulla probabilità.
    private boolean playHardOpponent(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point bestTarget = null;
        int bestScore = -1;
        
        // Costruiamo la mappa delle probabilità per ogni cella non ancora colpita.
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                Point p = new Point(x, y);
                if (hardFired.contains(p))
                    continue;
                int score = 1; // punteggio base
                Point[] neighbors = new Point[] {
                    new Point(x - 1, y),
                    new Point(x + 1, y),
                    new Point(x, y - 1),
                    new Point(x, y + 1)
                };
                for (Point n : neighbors) {
                    if (n.x >= 0 && n.x < dim && n.y >= 0 && n.y < dim && hardHits.contains(n)) {
                        score += 3;
                    }
                }
                // Aggiungiamo un piccolo termine casuale per variare la scelta (0 o 1)
                score += random.nextInt(2);
                if (score > bestScore) {
                    bestScore = score;
                    bestTarget = p;
                }
            }
        }
        
        if (bestTarget == null) {
            bestTarget = new Point(random.nextInt(dim), random.nextInt(dim));
        }
        hardFired.add(bestTarget);
        
        int type;
        if (bestScore <= 1) {
            // Se il punteggio è basso, la cella è probabilmente vuota: sonda con colpi Normali.
            type = 1;
        } else {
            // Se il punteggio è maggiore di 1, c'è probabilmente una nave nelle vicinanze: usa il proiettile Potente (tipo 2).
            type = 2;
            // Se il punteggio è particolarmente elevato (≥ 4) e con probabilità del 20%, prova a sparare un Speciale (tipo 3).
            if (bestScore >= 4 && random.nextDouble() < 0.2) {
                type = 3;
            }
        }
        
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        view.showMsg(Messages.pcShootMsg(bestTarget.x, bestTarget.y));
        boolean hit = battle.executeTurn(bestTarget, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        
        if (hit) {
            hardHits.add(bestTarget);
        }
        return hit;
    }
}

