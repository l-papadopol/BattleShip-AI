/*
 * 	Tui.java è l'interfaccia utente testuale
 *  * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import java.util.Scanner;
import model.GridSquare;
import model.Grid;

public class Tui implements ViewInterface {
    private Scanner scanner;
    
    public Tui() {
        scanner = new Scanner(System.in);
    }
    
    // Stampa un messaggio su System.out
    @Override
    public void showMsg(String message) {
        System.out.println(message);
    }
    
    // Acquisice un input testuale da utente come fosse il prompt della Bash di Linux
    @Override
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    // Chiude scanner
    @Override
    public void close() {
        scanner.close();
    }
    
    // Disegna la griglia di gioco
    @Override
    public void gridDrawing(Grid grid) {
        int dim = grid.getSize();
        GridSquare[][] gridSquares = grid.getGridSquares();

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

        // Stampa righe
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


