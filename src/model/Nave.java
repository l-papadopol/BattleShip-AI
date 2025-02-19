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
	public boolean isAffondata() {
	    return caselle.stream().allMatch(casella -> casella.getLivelloDanno() >= casella.getResistenzaMax());
	}

	// Restituisce la lunghezza della nave ovvero il numero delle caselle che occupa
	public int getLunghezza() {
		return caselle.size();
	}

	// Restituisce una lista contenete le caselle che compongono la nave
	public List<Casella> getCaselle() {
	    return caselle;
	}
	

}
