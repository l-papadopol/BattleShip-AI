package model.entities;

/**
 * ProiettileSpeciale.java modella un proiettile che arreca il danno massimo.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe estende {@link Projectile} e rappresenta un proiettile speciale che infligge il danno massimo, impostato a 4 unità.
 */
public class SpecialProjectile extends Projectile {

    /**
     * Costruisce un nuovo SpecialProjectile con danno impostato a 4.
     */
    public SpecialProjectile() {
        this.damage = 4;
    }
}
