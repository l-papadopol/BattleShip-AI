/*
 * TestCasella.java test delle unità per Casella.java
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import model.Casella;

class TestCasella {

	@Test
    public void testCreazioneCasellaDefault() {
        Casella casella = new Casella(new Point(2, 3));

        assertEquals(new Point(2, 3), casella.getCoordinata(), "La coordinata della casella è (2,3)");
        assertEquals(0, casella.getLivelloDanno(), "Il livello di danno predefinito è 0");
        assertEquals(4, casella.getResistenzaMax(), "La resistenza massima predefinita è4");
        assertFalse(casella.getOccupata(), "La casella non è occupata");
        assertFalse(casella.getColpita(), "La casella non è colpita");
    }

    @Test
    public void testCreazioneCasellaPersonalizzata() {
        Casella casella = new Casella(new Point(1, 1), 2, 5, true, true);

        assertEquals(new Point(1, 1), casella.getCoordinata(), "La coordinata della casella è (1,1)");
        assertEquals(2, casella.getLivelloDanno(), "Il livello di danno è 2");
        assertEquals(5, casella.getResistenzaMax(), "La resistenza massima è 5");
        assertTrue(casella.getOccupata(), "La casella è occupata");
        assertTrue(casella.getColpita(), "La casella ècolpita");
    }

    @Test
    public void testAumentoDannoValido() {
        Casella casella = new Casella(new Point(0, 0));
        casella.setLivelloDanno(2);

        assertEquals(2, casella.getLivelloDanno(), "Il livello di danno dovrebbe essere impostato a 2");
    }

    @Test
    public void testSetLivelloDannoFuoriLimite() {
        Casella casella = new Casella(new Point(0, 0));
        
        // Test valore negativo
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            casella.setLivelloDanno(-1);
        });
        assertEquals("Il livello di danno non può essere negativo", exception.getMessage());

        // Test valore superiore alla resistenza massima
        exception = assertThrows(IllegalArgumentException.class, () -> {
            casella.setLivelloDanno(5);
        });
        assertEquals("Il livello di danno non può essere > 4", exception.getMessage());
    }

    @Test
    public void testModificaOccupata() {
        Casella casella = new Casella(new Point(4, 4));
        casella.setOccupata(true);

        assertTrue(casella.getOccupata(), "La casella dovrebbe essere occupata dopo la modifica");
    }

    @Test
    public void testModificaColpita() {
        Casella casella = new Casella(new Point(4, 4));
        casella.setColpita(true);

        assertTrue(casella.getColpita(), "La casella dovrebbe essere colpita dopo la modifica");
    }
}