package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import model.*;

public class TestGriglia {

	// La griglia accetta il posizionamento di una nave in un punto libero da navi e dentro la griglia?
    @Test
    public void testPosizionaNaveValida() {
        Griglia griglia = new Griglia(10);

        Nave nave = CantiereNavale.creaNave(3, new Point(0, 0), true);
        boolean placed = griglia.posizionaNave(nave, new Point(0, 0), true);
        assertTrue(placed, "La nave deve essere posizionata correttamente");
        assertTrue(griglia.getNavi().contains(nave), "La griglia deve contenere la nave posizionata");
    }
    
	// La griglia accetta il posizionamento di una nave in un punto che esce fuori griglia?
    @Test
    public void testPosizionaNaveFuoriGriglia() {
        Griglia griglia = new Griglia(10);

        Nave nave = CantiereNavale.creaNave(3, new Point(0, 0), true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            griglia.posizionaNave(nave, new Point(10, 4), true);
        });
        assertNotNull(exception);
    }
    
	// La griglia accetta il posizionamento di una nave in un punto già occupato?
    @Test
    public void testPosizionaNaveCasellaOccupata() {
        Griglia griglia = new Griglia(10);

        Nave nave1 = CantiereNavale.creaNave(3, new Point(0, 0), true);
        griglia.posizionaNave(nave1, new Point(0, 0), true);

        Nave nave2 = CantiereNavale.creaNave(3, new Point(0, 0), false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            griglia.posizionaNave(nave2, new Point(0, 0), false);
        });
        assertNotNull(exception);
    }
    
	// La griglia capisce quando tutte le navi sono affondate?
    @Test
    public void testTutteNaviAffondate() {
        Griglia griglia = new Griglia(10);
        assertTrue(griglia.tutteNaviAffondate(), "Una griglia priva di navi ha tutte le navi affondate");
    }
}

