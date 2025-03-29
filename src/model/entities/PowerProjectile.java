package model.entities;

/**
 * ProiettilePotente.java modella un proiettile che arreca 3 unità di danno.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe estende {@link Projectile} e rappresenta un proiettile potente che infligge 3 unità di danno.
 */
public class PowerProjectile extends Projectile {
    
    /**
     * Costruisce un nuovo PowerProjectile con danno impostato a 3.
     */
    public PowerProjectile(){
        this.damage = 3;
    }
}
