package model.entities;

import java.awt.Point;

/**
 * GridSquare.java modella una singola casella del tabellone di gioco di battaglia navale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe rappresenta una casella della griglia di gioco. 
 * Essa mantiene le coordinate, il livello di danno, la resistenza massima e lo stato di occupazione e di colpito.
 */
public class GridSquare {
	
	private Point coordinates;
	private int damageLevel;
	private int maxResistance;
	Boolean isOccupied;
	Boolean isHit;
	
	/**
	 * Costruttore completo per creare una casella con valori specificati.
	 *
	 * @param coordinates le coordinate della casella
	 * @param damageLevel il livello iniziale di danno
	 * @param maxResistance la resistenza massima della casella
	 * @param isOccupied {@code true} se la casella è occupata da una nave, {@code false} altrimenti
	 * @param isHit {@code true} se la casella è già stata colpita, {@code false} altrimenti
	 */
	public GridSquare(Point coordinates, int damageLevel, int maxResistance, Boolean isOccupied, Boolean isHit) {
		this.coordinates = coordinates;
		this.damageLevel = damageLevel;
		this.maxResistance = maxResistance;
		this.isOccupied = isOccupied;
		this.isHit = isHit;
	}
	
	/**
	 * Costruttore generico che inizializza i campi della casella con valori di default.
	 *
	 * @param coordinates le coordinate della casella
	 */
	public GridSquare(Point coordinates) {
		this.coordinates = coordinates;
		this.damageLevel = 0;
		this.maxResistance = 4;
		this.isOccupied = false;
		this.isHit = false;
	}

	/**
	 * Restituisce le coordinate della casella.
	 *
	 * @return le coordinate della casella
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * Imposta le coordinate della casella.
	 *
	 * @param coordinates le nuove coordinate della casella
	 */
	public void setCoordinata(Point coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Restituisce il livello di danno della casella.
	 *
	 * @return il livello di danno
	 */
	public int getDamageLevel() {
		return damageLevel;
	}

	/**
	 * Imposta il livello di danno della casella.
	 * Se il livello di danno è fuori dal range consentito [0, maxResistance] viene lanciata un'eccezione.
	 *
	 * @param damageLevel il nuovo livello di danno
	 * @throws IllegalArgumentException se il livello di danno è inferiore a 0 o superiore a maxResistance
	 */
	public void setDamageLevel(int damageLevel) {
	    if (damageLevel < 0 || damageLevel > maxResistance) {
	        throw new IllegalArgumentException("Fuori range");
	    }
	    this.damageLevel = damageLevel;
	}

	/**
	 * Restituisce la resistenza massima della casella.
	 *
	 * @return la resistenza massima
	 */
	public int getMaxResistance() {
		return maxResistance;
	}

	/**
	 * Imposta la resistenza massima della casella.
	 *
	 * @param maxResistance la nuova resistenza massima
	 */
	public void setResistenzaMax(int maxResistance) {
		this.maxResistance = maxResistance;
	}

	/**
	 * Verifica se la casella è occupata.
	 *
	 * @return {@code true} se la casella è occupata, {@code false} altrimenti
	 */
	public Boolean getIsOccupied() {
		return isOccupied;
	}

	/**
	 * Imposta lo stato di occupazione della casella.
	 *
	 * @param isOccupied {@code true} se la casella deve essere marcata come occupata, {@code false} altrimenti
	 */
	public void setOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	/**
	 * Verifica se la casella è già stata colpita.
	 *
	 * @return {@code true} se la casella è colpita, {@code false} altrimenti
	 */
	public Boolean getIsHit() {
		return isHit;
	}

	/**
	 * Imposta lo stato di colpito della casella.
	 *
	 * @param isHit {@code true} se la casella deve essere marcata come colpita, {@code false} altrimenti
	 */
	public void setIsHit(Boolean isHit) {
		this.isHit = isHit;
	}
}
