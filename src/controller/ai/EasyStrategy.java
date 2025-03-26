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

/**
 * Classe che implementa la logica di gioco semplice.
 * Spara a caso colpi di tipo casuale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class EasyStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private Random random = new Random();

    /**
     * Strategia "facile"
     *
     * @param battle il modello del gioco
     * @param view la vista del gioco
     * @param projectileHandler il gestore dei proiettili
     */
    public EasyStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    /**
     * Esegue il turno del giocatore controllato dall'IA.
     * Seleziona casualmente un punto e un tipo di proiettile da sparare.
     * Visualizza il messaggio di tiro e determina se il tiro ha colpito o mancato.
     *
     * @param currentPlayer il giocatore corrente per il quale eseguire il turno
     * @return {@code true} se il tiro ha colpito un bersaglio, {@code false} altrimenti
     */
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
