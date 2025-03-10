/*
 * TestGriglia.java test per verificare se la griglia funziona
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
        Grid gridSquare = new Grid(10);

        Ship ship = ShipBuilder.buildShip(3, new Point(0, 0), true);
        boolean isPlaced = gridSquare.placeShip(ship, new Point(0, 0), true);
        assertTrue(isPlaced, "La nave deve essere posizionata correttamente");
        assertTrue(gridSquare.getShips().contains(ship), "La griglia deve contenere la nave posizionata");
    }
    
	// La griglia accetta il posizionamento di una nave in un punto che esce fuori griglia?
    @Test
    public void outOfGridPositioningTest() {
        Grid gridSquare = new Grid(10);

        Ship ship = ShipBuilder.buildShip(3, new Point(0, 0), true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquare.placeShip(ship, new Point(10, 4), true);
        });
        assertNotNull(exception);
    }
    
	// La griglia accetta il posizionamento di una nave in un punto già occupato?
    @Test
    public void placeOverOccupiedTest() {
        Grid gridSquare = new Grid(10);

        Ship ship1 = ShipBuilder.buildShip(3, new Point(0, 0), true);
        gridSquare.placeShip(ship1, new Point(0, 0), true);

        Ship ship2 = ShipBuilder.buildShip(3, new Point(0, 0), false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquare.placeShip(ship2, new Point(0, 0), false);
        });
        assertNotNull(exception);
    }
    
	// La griglia capisce quando tutte le navi sono affondate?
    @Test
    public void everythingIsSunkTest() {
        Grid gridSquare = new Grid(10);
        assertTrue(gridSquare.isEverythinkSunk(), "Una griglia priva di navi ha tutte le navi affondate poichè il gioco non comincia senza navi!!!");
    }
}

