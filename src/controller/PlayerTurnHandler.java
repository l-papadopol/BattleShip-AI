/*
 * classe helper che gestisce il tuno di gioco del giocatore umano
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Player;
import model.ModelInterface;
import model.Projectile;
import view.Messages;
import view.ViewInterface;
import java.awt.Point;

public class PlayerTurnHandler {
    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;

    public PlayerTurnHandler(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    public boolean handleHumanTurn(Player currentPlayer) {
        // Visualizza le griglie e i proiettili disponibili
        view.showMsg(Messages.personalGrid());
        view.gridDrawing(currentPlayer.getPersonalGrid());
        view.showMsg(Messages.attackGrid());
        view.gridDrawing(currentPlayer.getAttackGrid());
        view.showMsg(Messages.availableProjectiles(
                currentPlayer.getPowerfullProjectileQty(),
                currentPlayer.getSpecialProjectileQty()));

        // Legge le coordinate inserite dall'utente
        int dim = currentPlayer.getPersonalGrid().getSize();
        Point coord = view.askCoordinates(Messages.askCoordinates(), dim);

        // Legge il tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale)
        int type = 0;
        boolean isTypeValid = false;
        while (!isTypeValid) {
            String input = view.prompt(Messages.chooseProjectile());
            try {
                type = Integer.parseInt(input.trim());
                if (type < 1 || type > 3) {
                    view.showMsg(Messages.projectileTypeErrorMsg());
                } else {
                    isTypeValid = true;
                }
            } catch (NumberFormatException e) {
                view.showMsg(Messages.invalidNumberMsg());
            }
        }

        // Crea il proiettile tramite il GestoreProiettili
        Projectile proiettile = projectileHandler.makeProjectile(type, currentPlayer);

        // Esegue il turno e mostra l'esito (Colpito / Mancato)
        boolean hit = battle.executeTurn(coord, proiettile);
        view.showMsg(hit ? Messages.hitMsg() : Messages.missMsg());
        return hit;
    }
}
