package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import model.*;

public class TestBattagliaNavale {

    private Giocatore player1;
    private Giocatore player2;
    private BattagliaNavale game;

    @BeforeEach
    public void setUp() {
        // Per i test si assume che il costruttore di Giocatore sia stato aggiornato per inizializzare anche grigliaAttacco.
        player1 = new Giocatore("Player1", 10);
        player2 = new Giocatore("Player2", 10);
        
        // Posizioniamo una nave di lunghezza 1 su player2 nella cella (0,0).
        Nave ship = CantiereNavale.creaNave(1, new Point(0, 0), true);
        player2.posizionaNave(ship, new Point(0, 0), true);
        
        // Inizializziamo la partita con i due giocatori.
        game = new BattagliaNavale(player1, player2);
    }
    
    @Test
    public void testTurnoNonTerminale() {
        // Simuliamo un tiro a vuoto, ad esempio in una cella non occupata (5,5).
        boolean hit = game.eseguiTurno(new Point(5, 5), new ProiettileNormale());
        assertFalse(hit, "Il tiro in (5,5) dovrebbe mancare");
        
        // Dopo un tiro a vuoto, il turno dovrebbe passare all'avversario.
        assertEquals(player2, game.getCurrentPlayer(), "Il turno dovrebbe passare a Player2");
        assertFalse(game.isGameOver(), "Il gioco non dovrebbe essere terminato dopo un tiro a vuoto");
    }
    
    @Test
    public void testTurnoTerminale() {
        // Simuliamo un tiro che affonda la nave di player2.
        // Utilizziamo un ProiettileSpeciale che, infliggendo 4 danni (uguali alla resistenza massima), affonda la nave.
        boolean hit = game.eseguiTurno(new Point(0, 0), new ProiettileSpeciale());
        assertTrue(hit, "Il tiro in (0,0) dovrebbe colpire la nave");
        
        // A seguito del tiro, player2 dovrebbe aver perso tutte le navi e il gioco essere terminato.
        assertTrue(game.isGameOver(), "Il gioco dovrebbe essere terminato dopo aver affondato le navi di Player2");
        assertEquals(player1, game.getWinner(), "Il vincitore dovrebbe essere Player1");
    }
    
    @Test
    public void testEseguiTurnoAfterGameOver() {
        // Affondiamo la nave di player2.
        game.eseguiTurno(new Point(0, 0), new ProiettileSpeciale());
        assertTrue(game.isGameOver(), "Il gioco deve essere terminato dopo aver affondato le navi di Player2");
        
        // Tentare di eseguire un ulteriore turno deve lanciare un'eccezione.
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            game.eseguiTurno(new Point(1, 1), new ProiettileNormale());
        });
        assertNotNull(exception, "Dopo che il gioco è terminato, eseguiTurno deve lanciare un'eccezione");
    }
}
