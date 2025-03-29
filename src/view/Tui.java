package view;

import java.util.Scanner;

import model.entities.Grid;
import model.entities.GridSquare;

/**
 * Tui.java è l'interfaccia utente testuale per il gioco Battaglia Navale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe fornisce metodi per mostrare messaggi, acquisire input e disegnare la griglia di gioco tramite la console.
 */
public class Tui implements ViewInterface {
    private Scanner scanner;
    private String playerName;
    
    /**
     * Costruisce una nuova interfaccia utente testuale.
     * Inizializza lo scanner per l'input da console.
     */
    public Tui() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Stampa un messaggio su System.out.
     *
     * @param message il messaggio da mostrare
     */
    @Override
    public void showMsg(String message) {
        System.out.println(message);
    }
    
    /**
     * Acquisisce un input testuale da utente, visualizzando un prompt.
     *
     * @param message il messaggio di prompt da mostrare
     * @return la stringa inserita dall'utente
     */
    @Override
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    /**
     * Chiude lo scanner per liberare le risorse.
     */
    @Override
    public void close() {
        scanner.close();
    }
    
    /**
     * Disegna la griglia di gioco sulla console.
     * Visualizza l'intestazione della griglia, le etichette delle colonne e le righe della griglia.
     * Per ogni casella, viene visualizzato un simbolo che rappresenta lo stato della casella:
     * <ul>
     *   <li>'#' per una nave non danneggiata</li>
     *   <li>'X' per una nave completamente danneggiata</li>
     *   <li>'¼', '½', '¾' per livelli intermedi di danno</li>
     *   <li>'o' per un colpo a vuoto (casella colpita senza danno)</li>
     *   <li>'.' per una casella non colpita e libera</li>
     * </ul>
     *
     * @param grid la griglia da disegnare
     */
    @Override
    public void gridDrawing(Grid grid) {
        int dim = grid.getSize();
        GridSquare[][] gridSquares = grid.getGridSquares();

        // Intestazione con il nome del giocatore
        System.out.println(playerName);
        
        // Intestazione colonne
        System.out.print("    ");
        for (int x = 0; x < dim; x++) {
            System.out.printf("%2d ", x);
        }
        System.out.println();

        // Separatore
        System.out.print("    ");
        for (int x = 0; x < dim; x++) {
            System.out.print("---");
        }
        System.out.println();

        // Stampa delle righe
        for (int y = 0; y < dim; y++) {
            System.out.printf("%2d |", y);
            for (int x = 0; x < dim; x++) {
                GridSquare gridSquare = gridSquares[x][y];
                char damageSymbol;
                if (gridSquare.getIsOccupied()) {
                    int damage = gridSquare.getDamageLevel();
                    int resistance = gridSquare.getMaxResistance();
                    if (damage == 0) {
                        damageSymbol = '#';
                    } else if (damage == resistance) {
                        damageSymbol = 'X';
                    } else {
                        switch(damage) {
                            case 1: damageSymbol = '¼'; break;
                            case 2: damageSymbol = '½'; break;
                            case 3: damageSymbol = '¾'; break;
                            default: damageSymbol = '?'; break;
                        }
                    }
                } else {
                    if (gridSquare.getIsHit()) {
                        if (gridSquare.getDamageLevel() > 0) {
                            int damage = gridSquare.getDamageLevel();
                            switch(damage) {
                                case 1: damageSymbol = '¼'; break;
                                case 2: damageSymbol = '½'; break;
                                case 3: damageSymbol = '¾'; break;
                                case 4: damageSymbol = 'X'; break;
                                default: damageSymbol = '?'; break;
                            }
                        } else {
                            damageSymbol = 'o';
                        }
                    } else {
                        damageSymbol = '.';
                    }
                }
                System.out.print(" " + damageSymbol + " ");
            }
            System.out.println();
        }
    }
}

