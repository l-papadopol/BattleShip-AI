/*
 * Casella.java modella una singola casella del tabellone di gioco di battaglia navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class GridSquare {
	
	private Point coordinates;
	private int damageLevel;
	private int maxResistance;
	Boolean isOccupied;
	Boolean isHit;
	
	// Costruttore dettagliato
	public GridSquare(Point coordinates, int damageLevel, int maxResistance, Boolean isOccupied, Boolean isHit) {
		this.coordinates = coordinates;
		this.damageLevel = damageLevel;
		this.maxResistance = maxResistance;
		this.isOccupied = isOccupied;
		this.isHit = isHit;
	}
	
	// Costruttore generico con campi specifici inizializzati a valore di default
	public GridSquare(Point coordinates) {
		this.coordinates = coordinates;
		this.damageLevel = 0;
		this.maxResistance = 4;
		this.isOccupied = false;
		this.isHit = false;
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinata(Point coordinates) {
		this.coordinates = coordinates;
	}

	public int getDamageLevel() {
		return damageLevel;
	}

	public void setDamageLevel(int damageLevel) {
	    if (damageLevel < 0 || damageLevel > maxResistance) {
	        throw new IllegalArgumentException("Fuori range");
	    }
	    this.damageLevel = damageLevel;
	}


	public int getMaxResistance() {
		return maxResistance;
	}

	public void setResistenzaMax(int maxResistance) {
		this.maxResistance = maxResistance;
	}

	public Boolean getIsOccupied() {
		return isOccupied;
	}

	public void setOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Boolean getIsHit() {
		return isHit;
	}

	public void setIsHit(Boolean isHit) {
		this.isHit = isHit;
	}

}
