/*
 * TestBBattagliaNavale.java test di integrazione del model
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import model.*;

public class TestBattagliaNavale {

    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private BattagliaNavale partita; //private Model partita ??? Sono confuso se testare BattagliaNavale direttamente o passare per l'interfaccia

    @BeforeEach
    public void setUp() {
        // Per i test si assume che il costruttore di Giocatore sia stato aggiornato per inizializzare anche grigliaAttacco.
        giocatore1 = new Giocatore("giocatore1", 10);
        giocatore2 = new Giocatore("giocatore2", 10);
        
        // Posizioniamo una nave di lunghezza 1 su player2 nella cella (0,0).
        Nave ship = CantiereNavale.creaNave(1, new Point(0, 0), true);
        giocatore2.posizionaNave(ship, new Point(0, 0), true);
        
        // Inizializziamo la partita con i due giocatori.
        partita = new BattagliaNavale(giocatore1, giocatore2);
    }
    
    @Test
    public void testTurnoNonTerminale() {
        // Simuliamo un tiro a vuoto, ad esempio in una cella non occupata (5,5).
        boolean hit = partita.eseguiTurno(new Point(5, 5), new ProiettileNormale());
        assertFalse(hit, "Il tiro in (5,5) dovrebbe mancare");
        
        // Dopo un tiro a vuoto, il turno dovrebbe passare all'avversario.
        assertEquals(giocatore2, partita.getGiocatoreCorrente(), "Il turno dovrebbe passare a giocatore2");
        assertFalse(partita.isGameOver(), "Il gioco non dovrebbe essere terminato dopo un tiro a vuoto");
    }
    
    @Test
    public void testTurnoTerminale() {
        // Simuliamo un tiro che affonda la nave di player2.
        // Utilizziamo un ProiettileSpeciale che, infliggendo 4 danni (uguali alla resistenza massima), affonda la nave.
        boolean hit = partita.eseguiTurno(new Point(0, 0), new ProiettileSpeciale());
        assertTrue(hit, "Il tiro in (0,0) dovrebbe colpire la nave");
        
        // A seguito del tiro, player2 dovrebbe aver perso tutte le navi e il gioco essere terminato.
        assertTrue(partita.isGameOver(), "Il gioco dovrebbe essere terminato dopo aver affondato le navi di giocatore2");
        assertEquals(giocatore1, partita.getVincitore(), "Il vincitore dovrebbe essere giocatore1");
    }
    
    @Test
    public void testEseguiTurnoAfterGameOver() {
        // Affondiamo la nave di player2.
        partita.eseguiTurno(new Point(0, 0), new ProiettileSpeciale());
        assertTrue(partita.isGameOver(), "Il gioco deve essere terminato dopo aver affondato le navi di giocatore2");
        
        // Tentare di eseguire un ulteriore turno deve lanciare un'eccezione.
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            partita.eseguiTurno(new Point(1, 1), new ProiettileNormale());
        });
        assertNotNull(exception, "Dopo che il gioco è terminato, eseguiTurno deve lanciare un'eccezione");
    }
}
