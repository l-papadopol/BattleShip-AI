package view;

import java.awt.Point;

import model.entities.Grid;
import view.components.Messages;

/**
 * L'interfaccia della view per il gioco Battaglia Navale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Definisce i metodi necessari per la gestione dell'interfaccia utente, tra cui la visualizzazione
 * dei messaggi, l'acquisizione dell'input, il disegno della griglia e la chiusura dell'applicazione.
 */
public interface ViewInterface {
    
    /**
     * Visualizza un messaggio all'utente.
     *
     * @param message il messaggio da mostrare
     */
    void showMsg(String message);
    
    /**
     * Acquisisce un input testuale dall'utente, analogamente al prompt della Bash di Linux.
     *
     * @param message il messaggio di prompt da visualizzare
     * @return la stringa inserita dall'utente
     */
    String prompt(String message);
    
    /**
     * Chiude l'applicazione e libera le risorse associate alla view.
     */
    void close();
    
    /**
     * Gestisce il disegno della griglia di gioco.
     *
     * @param griglia la griglia da disegnare
     */
    void gridDrawing(Grid griglia);
    
    /**
     * Chiede all'utente di fornire le coordinate.
     * Il metodo continua a richiedere input finché non viene inserito un formato valido e le coordinate
     * sono all'interno del range della griglia.
     *
     * @param message il messaggio di prompt da visualizzare
     * @param dim la dimensione della griglia (necessaria per la validazione)
     * @return un {@link Point} contenente le coordinate inserite
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
    
    /**
     * Chiede all'utente di fornire l'orientamento di una nave.
     * L'input deve essere "H" per orizzontale o "V" per verticale (case-insensitive).
     *
     * @param message il messaggio di prompt da visualizzare
     * @return {@code true} se l'orientamento è orizzontale, {@code false} se è verticale
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

