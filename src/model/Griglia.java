/*
 * Griglia.java modella la griglia di gioco
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Griglia {
	private int dimensione;     
	private Casella[][] caselle; 
	private List<Nave> navi;       

	public Griglia(int dimensione) {
		this.dimensione = dimensione;
		this.caselle = new Casella[dimensione][dimensione];
		this.navi = new ArrayList<>();

		for (int x = 0; x < dimensione; x++) {
			for (int y = 0; y < dimensione; y++) {
				caselle[x][y] = new Casella(new Point(x, y));
			}
		}
	}

	// Posiziono una nave nella griglia stando bene attento che non esca fuori.
	public boolean posizionaNave(Nave nave, Point coordinata, boolean orizzontale) {
	    List<Casella> caselleNave = new ArrayList<>();
	    int x = coordinata.x;
	    int y = coordinata.y;
	    for (int i = 0; i < nave.getLunghezza(); i++) {
	        int xCoord = orizzontale ? x + i : x;
	        int yCoord = orizzontale ? y : y + i;

	        // La nave non deve uscire dai bordi dell'area di gioco
	        if (xCoord >= dimensione || yCoord >= dimensione || caselle[xCoord][yCoord].getOccupata()) {
	            throw new IllegalArgumentException("Posizionamento nave non valido: fuori dai limiti o casella già occupata.");
	        }
	        caselleNave.add(caselle[xCoord][yCoord]);
	    }

	    nave.getCaselle().clear();
	    nave.getCaselle().addAll(caselleNave);

	    for (Casella casella : caselleNave) {
	        casella.setOccupata(true);
	    }

	    navi.add(nave);
	    return true;
	}

	// Danneggia una casella che compone la nave oppure marchia come colpo andato a vuoto un colpo nel mare
	public boolean applicaDanno(Point coordinata, Proiettile proiettile) {
		int x = coordinata.x;
		int y = coordinata.y;
		
		if (x < 0 || y < 0 || x >= dimensione || y >= dimensione) {
			throw new IllegalArgumentException("Coordinate fuori dai limiti della griglia.");
		}

		Casella casella = caselle[x][y];

		// Nessuna nave colpita se non c'è una nave ad occupare la casella ma la marco come colpita (colpo a vuoto)
		if (!casella.getOccupata()) {
			casella.setColpita(true);
			return false;
		}else if (casella.getLivelloDanno() >= casella.getResistenzaMax()) {
			return false;
		}else {
			int nuovoDanno = casella.getLivelloDanno() + proiettile.getDanno();

			// Se il nuovo danno supera la resistenza massima, lo setto al massimo consentito
			if (nuovoDanno > casella.getResistenzaMax()) {
				nuovoDanno = casella.getResistenzaMax();
			}

			casella.setLivelloDanno(nuovoDanno);
		}
		return true;
	}

	// Restituisce true se tutte le navi sono affondate oppure false
	public boolean tutteNaviAffondate() {
		for (Nave nave : navi) {
			if (!nave.isAffondata()) {
				return false;
			}
		}
		return true;
	}

	// Quanto è grande la griglia quadrata???
	public int getDimensione() {
		return dimensione;
	}
	
	// Restituisce le navi
	public List<Nave> getNavi() {
	    return navi;
	}
	
	// Restituisce l'array bidimensionale di caselle che compone la griglia, utile per la text user interface
	public Casella[][] getCaselle() {
	    return caselle;
	}



	

}
