/*
 * Casella.java modella una singola casella del tabellone di gioco di battaglia navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class Casella {
	
	private Point coordinata;
	private int livelloDanno;
	private int resistenzaMax;
	Boolean occupata;
	Boolean colpita;
	
	// Costruttore dettagliato
	public Casella(Point coordinata, int livelloDanno, int resistenzaMax, Boolean occupata, Boolean colpita) {
		this.coordinata = coordinata;
		this.livelloDanno = livelloDanno;
		this.resistenzaMax = resistenzaMax;
		this.occupata = occupata;
		this.colpita = colpita;
	}
	
	// Costruttore generico con campi specifici inizializzati a valore di default
	public Casella(Point coordinata) {
		this.coordinata = coordinata;
		this.livelloDanno = 0;
		this.resistenzaMax = 4;
		this.occupata = false;
		this.colpita = false;
	}

	public Point getCoordinata() {
		return coordinata;
	}

	public void setCoordinata(Point coordinata) {
		this.coordinata = coordinata;
	}

	public int getLivelloDanno() {
		return livelloDanno;
	}

	public void setLivelloDanno(int livelloDanno) {
		if(livelloDanno >=0 && livelloDanno <=4) {
			this.livelloDanno = livelloDanno;
		} else {
			
		}
	}

	public int getResistenzaMax() {
		return resistenzaMax;
	}

	public void setResistenzaMax(int resistenzaMax) {
		this.resistenzaMax = resistenzaMax;
	}

	public Boolean getOccupata() {
		return occupata;
	}

	public void setOccupata(Boolean occupata) {
		this.occupata = occupata;
	}

	public Boolean getColpita() {
		return colpita;
	}

	public void setColpita(Boolean colpita) {
		this.colpita = colpita;
	}

}
