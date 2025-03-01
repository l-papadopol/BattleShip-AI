/*
 * 	Tui.java è l'interfaccia utente testuale
 *  * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import java.awt.Point;
import java.util.Scanner;
import model.Casella;
import model.Griglia;

public class Tui implements View {
    private Scanner scanner;
    
    public Tui() {
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void mostraMessaggio(String message) {
        System.out.println(message);
    }
    
    @Override
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    @Override
    public void chiudi() {
        scanner.close();
    }
    
    @Override
    public void stampa(Griglia griglia) {
        int dim = griglia.getDimensione();
        Casella[][] caselle = griglia.getCaselle();

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
                Casella casella = caselle[x][y];
                char simbolo;
                if (casella.getOccupata()) {
                    int danno = casella.getLivelloDanno();
                    int resistenza = casella.getResistenzaMax();
                    if (danno == 0) {
                        simbolo = '#';
                    } else if (danno == resistenza) {
                        simbolo = 'X';
                    } else {
                        switch(danno) {
                            case 1: simbolo = '¼'; break;
                            case 2: simbolo = '½'; break;
                            case 3: simbolo = '¾'; break;
                            default: simbolo = '?'; break;
                        }
                    }
                } else {
                    if (casella.getColpita()) {
                        if (casella.getLivelloDanno() > 0) {
                            int danno = casella.getLivelloDanno();
                            switch(danno) {
                                case 1: simbolo = '¼'; break;
                                case 2: simbolo = '½'; break;
                                case 3: simbolo = '¾'; break;
                                case 4: simbolo = 'X'; break;
                                default: simbolo = '?'; break;
                            }
                        } else {
                            simbolo = 'o';
                        }
                    } else {
                        simbolo = '.';
                    }
                }
                System.out.print(" " + simbolo + " ");
            }
            System.out.println();
        }
    }
    
    @Override
    public Point leggiCoordinate(String message, int dim) {
        int x = -1, y = -1;
        boolean valid = false;
        while (!valid) {
            String input = prompt(message);
            String[] tokens = input.trim().split("\\s+");
            if (tokens.length != 2) {
                mostraMessaggio("Formato non valido. Inserisci due numeri separati da uno spazio.");
                continue;
            }
            try {
                x = Integer.parseInt(tokens[0]);
                y = Integer.parseInt(tokens[1]);
                if (x < 0 || x >= dim || y < 0 || y >= dim) {
                    mostraMessaggio("Le coordinate devono essere comprese tra 0 e " + (dim - 1));
                    continue;
                }
                valid = true;
            } catch (NumberFormatException e) {
                mostraMessaggio("Inserisci numeri validi.");
            }
        }
        return new Point(x, y);
    }
    
    @Override
    public boolean leggiOrientamento(String message) {
        while (true) {
            String input = prompt(message);
            if (input.trim().equalsIgnoreCase("O")) {
                return true;
            } else if (input.trim().equalsIgnoreCase("V")) {
                return false;
            } else {
                mostraMessaggio("Inserisci un orientamento valido: O per orizzontale o V per verticale.");
            }
        }
    }
}


