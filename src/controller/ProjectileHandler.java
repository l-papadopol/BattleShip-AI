/**
 * Sceglie il proiettile da usare in base al tipo (1=Normale, 2=Potente, 3=Speciale).
 * Decrementa le scorte del giocatore se disponibili e mostra messaggi di errore se non disponibili.
 */

package controller;

import model.Player;
import model.Projectile;
import model.StandardProjectile;
import model.PowerProjectile;
import model.SpecialProjectile;
import view.Messages;
import view.ViewInterface;

public class ProjectileHandler {
    private ViewInterface view;

    public ProjectileHandler(ViewInterface view) {
        this.view = view;
    }

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
                // Se tipo non valido o == 1, usa proiettile normale
                return new StandardProjectile();
        }
    }
}

