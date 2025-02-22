package view;

import java.util.Scanner;
import model.Casella;
import model.Griglia;

public class Tui {
    private Scanner scanner;
    
    public Tui() {
        scanner = new Scanner(System.in);
    }
    
    // Mostra un messaggio all'utente
    public void mostraMessaggio(String message) {
        System.out.println(message);
    }
    
    // Richiede un input testuale all'utente
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    // Chiude lo scanner
    public void close() {
        scanner.close();
    }
    
    /*
     * Stampa la griglia usando simboli per rappresentare lo stato di ogni casella:
     * - '.' per acqua non colpita.
     * - 'o' per tiro a vuoto (colpito ma nessun danno).
     * - '#' per una nave intatta (griglia personale).
     * - '¼', '½', '¾' per una nave parzialmente danneggiata.
     * - 'X' per una casella di nave affondata (danno massimo).
     */
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
                    // Visualizza lo stato della nave nella griglia personale
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
}

