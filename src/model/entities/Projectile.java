package model.entities;

/**
 * Classe astratta che rappresenta il generico concetto di proiettile.
 * 
 * Molto semplicisticamente, un proiettile rappresenta un oggetto capace di arrecare danno,
 * quindi ha solo un campo "danno" che varia a seconda del tipo di proiettile.
 * Non ha senso instanziare un proiettile generico, da qui la scelta della classe astratta.
 * Non avrebbe altresì senso usare un'interfaccia poiché non c'è un comportamento da modellare,
 * ma solo una proprietà.
 *
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public abstract class Projectile {
    protected int damage;
    
    /**
     * Restituisce il valore del danno che questo proiettile infligge.
     *
     * @return il danno del proiettile
     */
    public int getDamage() {
        return damage;
    }
}
