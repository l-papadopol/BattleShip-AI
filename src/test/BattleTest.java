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

public class BattleTest {

    private Player player1;
    private Player player2;
    private Battle battle; 

    @BeforeEach
    public void setUp() {
        // Per i test si assume che il costruttore di Giocatore sia stato aggiornato per inizializzare anche grigliaAttacco.
        player1 = new Player("giocatore1", 10);
        player2 = new Player("giocatore2", 10);
        
        // Posizioniamo una nave di lunghezza 1 su player2 nella cella (0,0).
        Ship ship = ShipBuilder.buildShip(1, new Point(0, 0), true);
        player2.placeShip(ship, new Point(0, 0), true);
        
        // Inizializziamo la partita con i due giocatori.
        battle = new Battle(player1, player2);
    }
    
    @Test
    public void notEndingTurnTest() {
        // Simuliamo un tiro a vuoto, ad esempio in una cella non occupata (5,5).
        boolean hit = battle.executeTurn(new Point(5, 5), new StandardProjectile());
        assertFalse(hit, "Il tiro in (5,5) dovrebbe mancare il bersaglio");
        
        // Dopo un tiro a vuoto, il turno dovrebbe passare all'avversario.
        assertEquals(player2, battle.getCurrentPlayer(), "Il turno dovrebbe passare a giocatore2");
        assertFalse(battle.isGameOver(), "Il gioco non dovrebbe essere terminato dopo un tiro a vuoto");
    }
    
    @Test
    public void endingTurnTest() {
        // Simuliamo un tiro che affonda la nave di player2.
        // Utilizziamo un ProiettileSpeciale che, infliggendo 4 danni (uguali alla resistenza massima), affonda la nave.
        boolean hit = battle.executeTurn(new Point(0, 0), new SpecialProjectile());
        assertTrue(hit, "Il tiro in (0,0) dovrebbe colpire la nave");
        
        // A seguito del tiro, player2 dovrebbe aver perso tutte le navi e il gioco essere terminato.
        assertTrue(battle.isGameOver(), "Il gioco dovrebbe essere terminato dopo aver affondato le navi di giocatore2");
        assertEquals(player1, battle.getWinner(), "Il vincitore dovrebbe essere giocatore1");
    }
    
    @Test
    public void executeTurnAfterGameOverTest() {
        // Affondiamo la nave di player2.
        battle.executeTurn(new Point(0, 0), new SpecialProjectile());
        assertTrue(battle.isGameOver(), "Il gioco deve essere terminato dopo aver affondato le navi di giocatore2");
        
        // Tentare di eseguire un ulteriore turno deve lanciare un'eccezione.
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            battle.executeTurn(new Point(1, 1), new StandardProjectile());
        });
        assertNotNull(exception, "Dopo che il gioco è terminato, eseguiTurno deve lanciare un'eccezione");
    }
}
