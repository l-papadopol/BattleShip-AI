/*
 * classe che implementa la logica di gioco di difficoltà media che si basa su pattern di attacco ottimizzati
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
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

public class MediumStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private LinkedHashSet<Point> targets = new LinkedHashSet<>();
    private Random random = new Random();

    public MediumStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

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
