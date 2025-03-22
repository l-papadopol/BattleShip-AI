/**
 * Sceglie il proiettile da usare in base al tipo (1=Normale, 2=Potente, 3=Speciale).
 * Decrementa le scorte del giocatore se disponibili e mostra messaggi di errore se non disponibili.
 */

package controller.handlers;

import model.entities.Player;
import model.entities.PowerProjectile;
import model.entities.Projectile;
import model.entities.SpecialProjectile;
import model.entities.StandardProjectile;
import view.ViewInterface;
import view.components.Messages;

public class ProjectileHandler {
    private ViewInterface view;

    public ProjectileHandler(ViewInterface view) {
        this.view = view;
    }

    /*
     * Costruisce un proiettile
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
                // Se tipo non valido usa proiettile normale
                return new StandardProjectile();
        }
    }
}

