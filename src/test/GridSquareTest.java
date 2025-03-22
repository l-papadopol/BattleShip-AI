/*
 * GridSquareTest.java test per Casella.java
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import model.GridSquare;

class GridSquareTest {

	// Siamo sicuri che posso creare un oggetto casella?
	@Test
    public void gridSquareCreationTest() {
        GridSquare gridSquare = new GridSquare(new Point(2, 3));

        assertEquals(new Point(2, 3), gridSquare.getCoordinates(), "La coordinata della casella è (2,3)");
        assertEquals(0, gridSquare.getDamageLevel(), "Il livello di danno predefinito è 0");
        assertEquals(4, gridSquare.getMaxResistance(), "La resistenza massima predefinita è4");
        assertFalse(gridSquare.getIsOccupied(), "La casella non è occupata");
        assertFalse(gridSquare.getIsHit(), "La casella non è colpita");
    }

	// Controllo il fatto di poter creare caselle con attributi customizzati
    @Test
    public void customGridSquareCreationTest() {
        GridSquare gridSquare = new GridSquare(new Point(1, 1), 2, 5, true, true);

        assertEquals(new Point(1, 1), gridSquare.getCoordinates(), "La coordinata della casella è (1,1)");
        assertEquals(2, gridSquare.getDamageLevel(), "Il livello di danno è 2");
        assertEquals(5, gridSquare.getMaxResistance(), "La resistenza massima è 5");
        assertTrue(gridSquare.getIsOccupied(), "La casella è occupata");
        assertTrue(gridSquare.getIsHit(), "La casella ècolpita");
    }

    // Verifico che una casella possa subire danni memorizzandoli
    @Test
    public void validDamageTest() {
        GridSquare gridSquare = new GridSquare(new Point(0, 0));
        gridSquare.setDamageLevel(2);

        assertEquals(2, gridSquare.getDamageLevel(), "Il livello di danno dovrebbe essere impostato a 2");
    }
    
    // Verifico che io non possa assegnare alla casella un livello di danno negativo oppure oltre al limite massimo
    @Test
    public void invalidDamageTest() {
        GridSquare gridSquare = new GridSquare(new Point(0, 0));
        
        // Test valore negativo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquare.setDamageLevel(-1);
        });
        assertEquals("Fuori range", exception.getMessage());

        // Test valore superiore alla resistenza massima
        exception = assertThrows(IllegalArgumentException.class, () -> {
            gridSquare.setDamageLevel(5);
        });
        assertEquals("Fuori range", exception.getMessage());
    }
    
    // Verifico che una casella possa essere settata a occupata
    @Test
    public void isOccupiedTest() {
        GridSquare gridSquare = new GridSquare(new Point(4, 4));
        gridSquare.setOccupied(true);

        assertTrue(gridSquare.getIsOccupied(), "La casella dovrebbe essere occupata dopo la modifica");
    }

    // Verifico che una casella possa essere settata a colpita
    @Test
    public void isHitTest() {
        GridSquare gridSquare = new GridSquare(new Point(4, 4));
        gridSquare.setIsHit(true);

        assertTrue(gridSquare.getIsHit(), "La casella dovrebbe essere colpita dopo la modifica");
    }
}