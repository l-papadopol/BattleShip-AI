/*
 * Nave.java modella una nave composta da una List di caselle che occupa
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package model;

import java.util.List;

public class Nave {
	private List<Casella> caselle;
	
	public Nave(List<Casella> caselle) {
		this.caselle = caselle;
	}
	
	// Restituisce true se tutte le caselle di cui si compone la nave hanno raggiunto il danno massimo (affondata!!!)
	public boolean eAffondata() {
	    return caselle.stream().allMatch(casella -> casella.getLivelloDanno() >= casella.getResistenzaMax());
	}

	public int getLunghezza() {
		return caselle.size();
	}

	

}
