/*
 * classe astratta che rappresenta il generico concetto di proiettile
 * molto semplicisticamente un proiettile rappresenta un oggetto capace di arrecare danno quindi
 * ha solo un cammpo "danno" che varia a secondo del tipo di proiettile.
 * Non ha senso instanziare un proiettile generico da qui la scelta della classe astratta.
 * Non avrebbe altresì senso usare un interfaccia poichè non c'è un comportamento da modellare ma solo
 * una proprietà.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package model.entities;

public abstract class Projectile {
	protected int damage;
	
	public int getDamage() {
		return damage;
	}
}
