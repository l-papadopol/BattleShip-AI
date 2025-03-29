package controller.handlers;

import model.ModelInterface;
import model.entities.Player;
import model.entities.Projectile;
import view.ViewInterface;
import view.components.Messages;

import java.awt.Point;

/**
 * Classe helper che gestisce il turno di gioco del giocatore umano.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class PlayerTurnHandler {
    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;

    /**
     * Costruisce una nuova istanza di PlayerTurnHandler.
     *
     * @param battle il modello di gioco
     * @param view la vista del gioco
     * @param projectileHandler il gestore dei proiettili
     */
    public PlayerTurnHandler(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    /**
     * Gestisce il turno del giocatore umano.
     * Visualizza le griglie e i proiettili disponibili, chiede le coordinate e il tipo di proiettile da utilizzare e 
     * infine esegue il turno mostrando il messaggio appropriato in caso di colpo o mancato.
     *
     * @param currentPlayer il giocatore umano corrente
     * @return {@code true} se il tiro ha colpito il bersaglio, {@code false} altrimenti
     */
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

        // Crea il proiettile tramite il gestore dei proiettili
        Projectile proiettile = projectileHandler.makeProjectile(type, currentPlayer);

        // Esegue il turno e mostra l'esito: colpito o mancato
        boolean hit = battle.executeTurn(coord, proiettile);
        view.showMsg(hit ? Messages.hitMsg() : Messages.missMsg());
        return hit;
    }
}

