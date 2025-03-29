package controller.ai;

import java.awt.Point;
import java.util.LinkedHashSet;
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
 * Classe che implementa la logica di gioco di difficoltà livello "medio" basata su pattern di attacco ottimizzati.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class MediumStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private LinkedHashSet<Point> targets = new LinkedHashSet<>();
    private Random random = new Random();

    /**
     * Costruisce una nuova istanza di MediumStrategy.
     *
     * @param battle il modello del gioco
     * @param view la vista del gioco
     * @param projectileHandler il gestore dei proiettili
     */
    public MediumStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    /**
     * Esegue il turno del giocatore controllato dall'IA in modalità media.
     * Se esistono target precedentemente individuati, seleziona il primo; altrimenti, sceglie un target casuale e lo aggiunge alla lista.
     * Determina il tipo di proiettile da utilizzare in base a una probabilità e allo stato della cella bersaglio.
     * Dopo il tiro se il colpo ha avuto successo, aggiunge le celle adiacenti come nuovi target per i turni successivi.
     *
     * @param currentPlayer il giocatore corrente controllato dall'IA
     * @return {@code true} se il tiro ha colpito il bersaglio, {@code false} altrimenti
     */
    @Override
    public boolean executeTurn(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point target;
        if (!targets.isEmpty()) {
            target = targets.iterator().next();
            targets.remove(target);
        } else {
            target = new Point(random.nextInt(dim), random.nextInt(dim));
            targets.add(target);
        }

        Player enemy = (currentPlayer == battle.getFirstPlayer()) ? battle.getSecondPlayer() : battle.getFirstPlayer();
        GridSquare gs = enemy.getPersonalGrid().getGridSquares()[target.x][target.y];
        int type = (random.nextDouble() < 0.25)
                   ? 3
                   : (gs.getIsHit() && gs.getDamageLevel() < gs.getMaxResistance() ? 2 : 1);

        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());

        // Se il tiro ha colpito, aggiunge le celle adiacenti come nuovi target.
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
}

