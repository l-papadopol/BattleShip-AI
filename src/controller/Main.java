/*
 * Main.java punto di ingresso al programma
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package controller;

import model.BattagliaNavale;
import model.Giocatore;
import view.Tui;


public class Main { 
	public static void main(String[] args) {
        
		// Creo due giocatori
        Giocatore player1 = new Giocatore("UMANO", 10);
        Giocatore player2 = new Giocatore("PC", 10);
        
        // Inizializzo il modello della battaglia navale, creo l'interfaccia testuale ed avvio il controllore del gioco
        BattagliaNavale game = new BattagliaNavale(player1, player2);
        Tui view = new Tui();
        GameController controller = new GameController(game, view);

        controller.gioca();
    }
}
