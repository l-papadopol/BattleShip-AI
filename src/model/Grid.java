/*
 * Griglia.java modella la griglia di gioco
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Grid {
	private int size;     
	private GridSquare[][] gridSquares; 
	private List<Ship> ships;       

	public Grid(int size) {
		this.size = size;
		this.gridSquares = new GridSquare[size][size];
		this.ships = new ArrayList<>();

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				gridSquares[x][y] = new GridSquare(new Point(x, y));
			}
		}
	}

	/*
	 *  Posiziono una nave nella griglia stando bene attento che non esca fuori.
	 */
	public boolean placeShip(Ship ship, Point coordinates, boolean isHorizontal) {
	    List<GridSquare> shipSquares = new ArrayList<>();
	    int x = coordinates.x;
	    int y = coordinates.y;
	    for (int i = 0; i < ship.getLenght(); i++) {
	        int xCoord = isHorizontal ? x + i : x;
	        int yCoord = isHorizontal ? y : y + i;

	        // La nave non deve uscire dai bordi dell'area di gioco
	        if (xCoord >= size || yCoord >= size || gridSquares[xCoord][yCoord].getIsOccupied()) {
	            throw new IllegalArgumentException("Posizionamento nave non valido: fuori dai limiti o casella già occupata.");
	        }
	        shipSquares.add(gridSquares[xCoord][yCoord]);
	    }

	    ship.getGridSquares().clear();
	    ship.getGridSquares().addAll(shipSquares);

	    for (GridSquare gridSquare : shipSquares) {
	        gridSquare.setOccupied(true);
	    }

	    ships.add(ship);
	    return true;
	}

	/*
	 *  Danneggia una casella che compone la nave oppure marchia come colpo andato a vuoto un colpo nel mare
	 */
	public boolean applyDamage(Point coordinates, Projectile projectile) {
		int x = coordinates.x;
		int y = coordinates.y;
		
		if (x < 0 || y < 0 || x >= size || y >= size) {
			throw new IllegalArgumentException("Coordinate fuori dai limiti della griglia.");
		}

		GridSquare gridSquare = gridSquares[x][y];

		// Nessuna nave colpita se non c'è una nave ad occupare la casella ma la marco come colpita (colpo a vuoto)
		if (!gridSquare.getIsOccupied()) {
			gridSquare.setIsHit(true);
			return false;
		}else if (gridSquare.getDamageLevel() >= gridSquare.getMaxResistance()) {
			return false;
		}else {
			int newDamage = gridSquare.getDamageLevel() + projectile.getDamage();

			// Se il nuovo danno supera la resistenza massima, lo setto al massimo consentito
			if (newDamage > gridSquare.getMaxResistance()) {
				newDamage = gridSquare.getMaxResistance();
			}

			gridSquare.setDamageLevel(newDamage);
		}
		return true;
	}

	/*
	 *  Restituisce true se tutte le navi sono affondate oppure false
	 */
	public boolean isEverythinkSunk() {
		for (Ship ship : ships) {
			if (!ship.isSunk()) {
				return false;
			}
		}
		return true;
	}

	/*
	 *  Quanto è grande la griglia quadrata???
	 */
	public int getSize() {
		return size;
	}
	
	/*
	 *  Restituisce le navi
	 */
	public List<Ship> getShips() {
	    return ships;
	}
	
	/*
	 *  Restituisce l'array bidimensionale di caselle che compone la griglia, utile per la text user interface
	 */
	public GridSquare[][] getGridSquares() {
	    return gridSquares;
	}



	

}
