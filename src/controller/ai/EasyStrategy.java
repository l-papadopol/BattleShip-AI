/*
 * classe che implementa la logica di gioco semplice, spara a caso colpi di tipo casuale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller.ai;

import java.awt.Point;
import java.util.Random;

import controller.AITurnStrategy;
import controller.handlers.ProjectileHandler;
import model.ModelInterface;
import model.entities.Player;
import model.entities.Projectile;
import view.ViewInterface;
import view.components.Messages;

public class EasyStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private Random random = new Random();

    public EasyStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    @Override
    public boolean executeTurn(Player currentPlayer) {
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point p = new Point(random.nextInt(dim), random.nextInt(dim));
        int type = random.nextInt(3) + 1;
        Projectile projectile = projectileHandler.makeProjectile(type, currentPlayer);

        view.showMsg(Messages.pcShootMsg(p.x, p.y));
        boolean hit = battle.executeTurn(p, projectile);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());

        return hit;
    }
}
