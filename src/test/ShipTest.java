/*
 * ShipTest.java test per verificare cosa sto combinando in Nave.jave :P
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;
import model.builders.ShipBuilder;
import model.entities.GridSquare;
import model.entities.Ship;
import model.entities.StandardProjectile;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.List;

import org.junit.jupiter.api.Test;

class ShipTest {
	
	// Creo una nava di tre caselle con danni parziali e verifico che non sia considerata affondata
	@Test
	public void partialDamageTest() {
		List<GridSquare> gridSquares = List.of(
				new GridSquare(new Point(0, 0), 2, 4, true, true),
				new GridSquare(new Point(0, 1), 3, 4, true, true),
				new GridSquare(new Point(0, 2), 1, 4, true, true)
				);

		Ship ship = new Ship(gridSquares);
		assertFalse(ship.isSunk(), "La nave non deve affondare finchè tutte le caselle subiscono danno massimo.");
	}

	// Creo una nave di tre caselle con danni massivi che deve risultare affondata
	@Test
	public void isSunkTest() {
		List<GridSquare> gridSquares = List.of(
				new GridSquare(new Point(0, 0), 4, 4, true, true),
				new GridSquare(new Point(0, 1), 4, 4, true, true),
				new GridSquare(new Point(0, 2), 4, 4, true, true)
				);

		Ship ship = new Ship(gridSquares);
		assertTrue(ship.isSunk(), "La nave deve affondare poichè ha subito i danni massivi.");
	}

	// Creo una nave di tre caselle e la prendo a cannonate finchè non affonda
	@Test
	public void fullDamageSunkTest() {
		Ship ship = ShipBuilder.buildShip(3, new Point(0, 0), true);
		List<GridSquare> gridSquares = ship.getGridSquares();

		assertFalse(ship.isSunk(), "La nave non dovrebbe essere affondata all'inizio.");
		StandardProjectile projectile = new StandardProjectile();

		// Applico colpi incrementali: per i primi 3 colpi, la nave non deve essere affondata
		for (int hit = 1; hit < 4; hit++) {
			for (GridSquare gridSquare : gridSquares) {
				gridSquare.setDamageLevel(gridSquare.getDamageLevel() + projectile.getDamage());
			}
			assertFalse(ship.isSunk(), "La nave non dovrebbe essere affondata dopo " + hit + " colpi per casella.");
		}

		// Al 4° colpo, ogni casella raggiunge il danno massimo
		for (GridSquare gridSquare : gridSquares) {
			gridSquare.setDamageLevel(gridSquare.getDamageLevel() + projectile.getDamage());
		}
		assertTrue(ship.isSunk(), "La nave dovrebbe essere affondata solo quando tutte le caselle hanno raggiunto il danno massimo.");
	}



}