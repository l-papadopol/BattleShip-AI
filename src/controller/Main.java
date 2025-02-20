package controller;

import model.BattagliaNavale;
import model.CantiereNavale;
import model.Giocatore;
import view.TextView;
import java.awt.Point;

public class Main { 
	public static void main(String[] args) {
        
        Giocatore player1 = new Giocatore("Giocatore 1", 10);
        Giocatore player2 = new Giocatore("Giocatore 2", 10);

        player1.posizionaNave(CantiereNavale.creaNave(3, new Point(0, 0), true), new Point(0, 0), true);
        player2.posizionaNave(CantiereNavale.creaNave(3, new Point(0, 0), true), new Point(0, 0), true);

        BattagliaNavale game = new BattagliaNavale(player1, player2);
        TextView view = new TextView();
        GameController controller = new GameController(game, view);

        controller.startGame();
    }
}
