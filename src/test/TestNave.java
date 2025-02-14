/*
 * TestNave.java test delle unità per verificare cosa sto combinando in Nave.jave :P
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestNave {

	@Test
	void test() {
		List<Casella> caselle = List.of(
	            new Casella(new Point(0, 0), 2, 4, true, true),
	            new Casella(new Point(0, 1), 3, 4, true, true),
	            new Casella(new Point(0, 2), 1, 4, true, true)
	        );

	        Nave nave = new Nave(caselle);
	        assertFalse(nave.isAffondata(), "La nave non dovrebbe essere affondata se alcune caselle non hanno danno massimo.");
	    }

	    @Test
	    public void testNaveAffondata() {
	        List<Casella> caselle = List.of(
	            new Casella(new Point(0, 0), 4, 4, true, true),
	            new Casella(new Point(0, 1), 4, 4, true, true),
	            new Casella(new Point(0, 2), 4, 4, true, true)
	        );

	        Nave nave = new Nave(caselle);
	        assertTrue(nave.isAffondata(), "La nave dovrebbe essere affondata se tutte le caselle hanno danno massimo.");
	    }

	    @Test
	    public void testNaveParzialmenteDanneggiata() {
	        List<Casella> caselle = List.of(
	            new Casella(new Point(0, 0), 4, 4, true, true),
	            new Casella(new Point(0, 1), 4, 4, true, true),
	            new Casella(new Point(0, 2), 3, 4, true, true)
	        );

	        Nave nave = new Nave(caselle);
	        assertFalse(nave.isAffondata(), "La nave non dovrebbe essere affondata se almeno una casella non ha il danno massimo.");
	    }

	    @Test
	    public void testNaveVuota() {
	        List<Casella> caselle = List.of();

	        Nave nave = new Nave(caselle);
	        assertTrue(nave.isAffondata(), "Una nave senza caselle dovrebbe essere considerata affondata.");
	    }
	}