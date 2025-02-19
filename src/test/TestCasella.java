/*
 * TestCasella.java test per Casella.java
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import model.Casella;

class TestCasella {

	// Siamo sicuri che posso creare un oggetto casella?
	@Test
    public void testCasella() {
        Casella casella = new Casella(new Point(2, 3));

        assertEquals(new Point(2, 3), casella.getCoordinata(), "La coordinata della casella è (2,3)");
        assertEquals(0, casella.getLivelloDanno(), "Il livello di danno predefinito è 0");
        assertEquals(4, casella.getResistenzaMax(), "La resistenza massima predefinita è4");
        assertFalse(casella.getOccupata(), "La casella non è occupata");
        assertFalse(casella.getColpita(), "La casella non è colpita");
    }

	// Controllo il fatto di poter creare caselle con attributi customizzati
    @Test
    public void testCasellaCustom() {
        Casella casella = new Casella(new Point(1, 1), 2, 5, true, true);

        assertEquals(new Point(1, 1), casella.getCoordinata(), "La coordinata della casella è (1,1)");
        assertEquals(2, casella.getLivelloDanno(), "Il livello di danno è 2");
        assertEquals(5, casella.getResistenzaMax(), "La resistenza massima è 5");
        assertTrue(casella.getOccupata(), "La casella è occupata");
        assertTrue(casella.getColpita(), "La casella ècolpita");
    }

    // Verifico che una casella possa subire danni memorizzandoli
    @Test
    public void testDannoValido() {
        Casella casella = new Casella(new Point(0, 0));
        casella.setLivelloDanno(2);

        assertEquals(2, casella.getLivelloDanno(), "Il livello di danno dovrebbe essere impostato a 2");
    }
    
    // Verifico che io non possa assegnare alla casella un livello di danno negativo oppure oltre al limite massimo
    @Test
    public void testDannoFuoriLimite() {
        Casella casella = new Casella(new Point(0, 0));
        
        // Test valore negativo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            casella.setLivelloDanno(-1);
        });
        assertEquals("Fuori range", exception.getMessage());

        // Test valore superiore alla resistenza massima
        exception = assertThrows(IllegalArgumentException.class, () -> {
            casella.setLivelloDanno(5);
        });
        assertEquals("Fuori range", exception.getMessage());
    }
    
    // Verifico che una casella possa essere settata a occupata
    @Test
    public void testOccupata() {
        Casella casella = new Casella(new Point(4, 4));
        casella.setOccupata(true);

        assertTrue(casella.getOccupata(), "La casella dovrebbe essere occupata dopo la modifica");
    }

    // Verifico che una casella possa essere settata a colpita
    @Test
    public void testColpita() {
        Casella casella = new Casella(new Point(4, 4));
        casella.setColpita(true);

        assertTrue(casella.getColpita(), "La casella dovrebbe essere colpita dopo la modifica");
    }
}