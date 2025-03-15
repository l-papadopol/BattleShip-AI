/*
 * Nave.java modella una nave composta da una List di caselle che occupa
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package model;

import java.util.List;

public class Ship {
	private List<GridSquare> gridSquares;
	
	public Ship(List<GridSquare> gridSquares) {
		this.gridSquares = gridSquares;
	}
	
	/*
	 *  Restituisce true se tutte le caselle di cui si compone la nave hanno raggiunto il danno massimo (affondata!!!)
	 */
	public boolean isSunk() {
	    return gridSquares.stream().allMatch(gridSquare -> gridSquare.getDamageLevel() >= gridSquare.getMaxResistance());
	}

	/*
	 *  Restituisce la lunghezza della nave ovvero il numero delle caselle che occupa
	 */
	public int getLenght() {
		return gridSquares.size();
	}

	/*
	 *  Restituisce una lista contenete le caselle che compongono la nave
	 */
	public List<GridSquare> getGridSquares() {
	    return gridSquares;
	}
	

}
