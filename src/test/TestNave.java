/*
 * TestNave.java test per verificare cosa sto combinando in Nave.jave :P
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.List;

import org.junit.jupiter.api.Test;

class TestNave {
	
	// Creo una nava di tre caselle con danni parziali e verifico che non sia considerata affondata
	@Test
	public void testDannoParziale() {
		List<Casella> caselle = List.of(
				new Casella(new Point(0, 0), 2, 4, true, true),
				new Casella(new Point(0, 1), 3, 4, true, true),
				new Casella(new Point(0, 2), 1, 4, true, true)
				);

		Nave nave = new Nave(caselle);
		assertFalse(nave.isAffondata(), "La nave non deve affondare finchè tutte le caselle subiscono danno massimo.");
	}

	// Creo una nave di tre caselle con danni massivi che deve risultare affondata
	@Test
	public void testDannoAffonda() {
		List<Casella> caselle = List.of(
				new Casella(new Point(0, 0), 4, 4, true, true),
				new Casella(new Point(0, 1), 4, 4, true, true),
				new Casella(new Point(0, 2), 4, 4, true, true)
				);

		Nave nave = new Nave(caselle);
		assertTrue(nave.isAffondata(), "La nave deve affondare poichè ha subito i danni massivi.");
	}

	// Creo una nave di tre caselle e la prendo a cannonate finchè non affonda
	@Test
	public void testDannoTotaleAffonda() {
		Nave nave = CantiereNavale.creaNave(3, new Point(0, 0), true);
		List<Casella> caselle = nave.getCaselle();

		assertFalse(nave.isAffondata(), "La nave non dovrebbe essere affondata all'inizio.");
		ProiettileNormale proiettile = new ProiettileNormale();

		// Applico colpi incrementali: per i primi 3 colpi, la nave non deve essere affondata
		for (int colpo = 1; colpo < 4; colpo++) {
			for (Casella casella : caselle) {
				casella.setLivelloDanno(casella.getLivelloDanno() + proiettile.getDanno());
			}
			assertFalse(nave.isAffondata(), "La nave non dovrebbe essere affondata dopo " + colpo + " colpi per casella.");
		}

		// Al 4° colpo, ogni casella raggiunge il danno massimo
		for (Casella casella : caselle) {
			casella.setLivelloDanno(casella.getLivelloDanno() + proiettile.getDanno());
		}
		assertTrue(nave.isAffondata(), "La nave dovrebbe essere affondata solo quando tutte le caselle hanno raggiunto il danno massimo.");
	}



}