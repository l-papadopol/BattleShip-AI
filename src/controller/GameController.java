package controller;

import model.*;
import view.TextView;
import java.awt.Point;

public class GameController {
    private BattagliaNavale game;
    private TextView view;
    
    public GameController(BattagliaNavale game, TextView view) {
        this.game = game;
        this.view = view;
    }
    
    public void startGame() {
        view.displayMessage("Inizio partita di Battaglia Navale");
        while (!game.isGameOver()) {
            Giocatore currentPlayer = game.getCurrentPlayer();
            view.displayMessage("Turno di " + currentPlayer.getNome());
            
            // Richiediamo all'utente le coordinate per il tiro
            String input = view.prompt("Inserisci coordinate (x y): ");
            String[] tokens = input.split(" ");
            int x = Integer.parseInt(tokens[0]);
            int y = Integer.parseInt(tokens[1]);
            
            // Scelta del tipo di proiettile
            input = view.prompt("Scegli tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale): ");
            int tipo = Integer.parseInt(input);
            Proiettile proiettile;
            switch (tipo) {
                case 2:
                    proiettile = new ProiettilePotente();
                    break;
                case 3:
                    proiettile = new ProiettileSpeciale();
                    break;
                case 1:
                default:
                    proiettile = new ProiettileNormale();
                    break;
            }
            
            // Esecuzione del turno
            boolean hit = game.eseguiTurno(new Point(x, y), proiettile);
            if (hit) {
                view.displayMessage("Colpito!");
            } else {
                view.displayMessage("Mancato!");
            }
        }
        
        view.displayMessage("Il gioco è terminato! Il vincitore è " + game.getWinner().getNome());
        view.close();
    }
}
