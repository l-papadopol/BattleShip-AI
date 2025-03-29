package model.entities;

/**
 * ProiettileNormale.java modella un proiettile che arreca 1 unità di danno.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe estende {@link Projectile} e rappresenta un proiettile standard che infligge 1 unità di danno.
 */
public class StandardProjectile extends Projectile {
    
    /**
     * Costruisce un nuovo StandardProjectile con danno impostato a 1.
     */
    public StandardProjectile() {
        this.damage = 1;
    }
}
