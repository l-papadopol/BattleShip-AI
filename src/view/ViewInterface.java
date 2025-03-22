/*
 * l'interfaccia della view
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import java.awt.Point;

import model.entities.Grid;
import view.components.Messages;

public interface ViewInterface {
	
	/*
	 * Gestisco la stampa di messaggi, il loro inserimento e la chiusura dell'applicazione
	 */
    void showMsg(String message);
    
    /*
     *  Acquisice un input testuale da utente come fosse il prompt della Bash di Linux
     */
    String prompt(String message);
    
    /*
     *  Chiude tutto
     */
    void close();
    
    /*
     * Gestisco il disegno della griglia di gioco e chiedo coordinate/orientamento con validazione stretta
     */
    void gridDrawing(Grid griglia);
    
    /*
     *  Chiede di fornire le coordinate
     */
    default Point askCoordinates(String message, int dim) {
    	while (true) {
            String input = prompt(message);          
            String[] stringTrim = input.trim().split("\\s+");
            if (stringTrim.length != 2) {
            	showMsg(Messages.invalidFormat());
                continue;
            }
            try {
                int x = Integer.parseInt(stringTrim[0]);
                int y = Integer.parseInt(stringTrim[1]);
                if (x < 0 || x >= dim || y < 0 || y >= dim) {
                	showMsg(Messages.outOfRange(dim));
                    continue;
                }
                return new Point(x, y);
            } catch (NumberFormatException e) {
            	showMsg(Messages.invalidNumber());
            }
        }
    }
    
    /*
     *  Chiede di fornire l'orientamento di una nave
     */
    default boolean askOrientation(String message) {
    	 while (true) {
             String input = prompt(message);             
             input = input.trim();
             
             if (input.equalsIgnoreCase("H")) {
                 return true;
             } else if (input.equalsIgnoreCase("V")) {
                 return false;
             } else {
             	showMsg(Messages.orientationError());
             }
         }
    }

}
