/*
 * GridTest.java test per verificare se la griglia funziona
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import model.*;

public class GridTest {

	// La griglia accetta il posizionamento di una nave in un punto libero da navi e dentro la griglia?
    @Test
    public void validShipPositioningTest() {
        Grid gridSquares = new Grid(10);

        Ship ship = ShipBuilder.buildShip(3, new Point(0, 0), true);
        boolean isPlaced = gridSquares.placeShip(ship, new Point(0, 0), true);
        assertTrue(isPlaced, "La nave deve essere posizionata correttamente");
        assertTrue(gridSquares.getShips().contains(ship), "La griglia deve contenere la nave posizionata");
    }
    
	// La griglia accetta il posizionamento di una nave in un punto che esce fuori griglia?
    @Test
    public void outOfGridPositioningTest() {
        Grid gridSquares = new Grid(10);

        Ship ship = ShipBuilder.buildShip(3, new Point(0, 0), true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquares.placeShip(ship, new Point(10, 4), true);
        });
        assertNotNull(exception);
    }
    
	// La griglia accetta il posizionamento di una nave in un punto già occupato?
    @Test
    public void placeOverOccupiedTest() {
        Grid gridSquares = new Grid(10);

        Ship ship1 = ShipBuilder.buildShip(3, new Point(0, 0), true);
        gridSquares.placeShip(ship1, new Point(0, 0), true);

        Ship ship2 = ShipBuilder.buildShip(3, new Point(0, 0), false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquares.placeShip(ship2, new Point(0, 0), false);
        });
        assertNotNull(exception);
    }
    
	// La griglia capisce quando tutte le navi sono affondate?
    @Test
    public void everythingIsSunkTest() {
        Grid gridSquares = new Grid(10);
        assertFalse(gridSquares.isEverythingSunk(), "La griglia non ha tutte le navi affondate oppure la griglia è vuota.");
        
        Ship ship1 = ShipBuilder.buildShip(3, new Point(0, 0), true);
        gridSquares.placeShip(ship1, new Point(0, 0), true);
        assertFalse(gridSquares.isEverythingSunk(), "La griglia non ha tutte le navi affondate.");
        
    }
}

