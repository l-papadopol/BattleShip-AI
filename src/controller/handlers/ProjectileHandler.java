package controller.handlers;

import model.entities.Player;
import model.entities.PowerProjectile;
import model.entities.Projectile;
import model.entities.SpecialProjectile;
import model.entities.StandardProjectile;
import view.ViewInterface;
import view.components.Messages;

/**
 * Classe helper che sceglie il proiettile da usare in base al tipo 1=Normale, 2=Potente, 3=Speciale.
 * Decrementa le scorte del giocatore se disponibili e mostra messaggi di errore se non disponibili.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class ProjectileHandler {
    private ViewInterface view;

    /**
     * Costruisce un nuovo ProjectileHandler.
     *
     * @param view la vista del gioco, usata per mostrare i messaggi
     */
    public ProjectileHandler(ViewInterface view) {
        this.view = view;
    }

    /**
     * Costruisce un proiettile in base al tipo specificato.
     * Se il giocatore ha le scorte sufficienti per il tipo richiesto, le decrementa e restituisce il proiettile corrispondente altrimenti
     *  mostra un messaggio di errore e restituisce un proiettile standard.
     *
     * @param type il tipo di proiettile da creare 1 = Normale, 2 = Potente, 3 = Speciale
     * @param player il giocatore che utilizza il proiettile, da cui decrementare le scorte se necessario
     * @return un'istanza di {@link Projectile} corrispondente al tipo richiesto, oppure un proiettile standard se non disponibile
     */
    public Projectile makeProjectile(int type, Player player) {
        switch (type) {
            case 2:
                if (player.getPowerfullProjectileQty() > 0) {
                    player.decPowerfullProjectileQty();
                    return new PowerProjectile();
                } else {
                    view.showMsg(Messages.powerfulProjectileUnavailable());
                    return new StandardProjectile();
                }
            case 3:
                if (player.getSpecialProjectileQty() > 0) {
                    player.decSpecialProjectileQty();
                    return new SpecialProjectile();
                } else {
                    view.showMsg(Messages.specialProjectileUnavailable());
                    return new StandardProjectile();
                }
            case 1:
            default:
                // Se il tipo non è valido o è 1, utilizza il proiettile normale
                return new StandardProjectile();
        }
    }
}
