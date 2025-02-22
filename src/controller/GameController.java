/*
 * GameController.java si interfaccia fra model e view e gestisce il flusso di gioco
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package controller;

import model.BattagliaNavale;
import model.CantiereNavale;
import model.Giocatore;
import model.Nave;
import model.Proiettile;
import model.ProiettileNormale;
import model.ProiettilePotente;
import model.ProiettileSpeciale;
import view.Tui;
import java.awt.Point;
import java.util.Random;

public class GameController {
    private BattagliaNavale gioco;
    private Tui vista;
    private Random random;
    
    public GameController(BattagliaNavale gioco, Tui vista) {
        this.gioco = gioco;
        this.vista = vista;
        this.random = new Random();
    }
    
    /*
     * Fase di setup: il giocatore umano dispone le proprie navi tramite input
     * mentre il PC posiziona le proprie navi in maniera casuale.
     * In questo esempio, vengono posizionate nove navi con lunghezze
     * 5, 4, 4, 3, 3, 3, 2, 2, 1.
     */
    public void posizionaNavi() {
        Giocatore[] giocatori = { gioco.getGiocatore1(), gioco.getGiocatore2() };
        int[] lunghezzaNavi = { 5, 4, 4, 3, 3, 3, 2, 2, 1 };
        
        for (int i = 0; i < giocatori.length; i++) {
            Giocatore giocatore = giocatori[i];
            if (i == 0) {
                vista.mostraMessaggio("Posizionamento navi per " + giocatore.getNome());
                int dim = giocatore.getGrigliaPersonale().getDimensione();
                for (int lunghezza : lunghezzaNavi) {
                    boolean piazzato = false;
                    while (!piazzato) {
                        vista.stampa(giocatore.getGrigliaPersonale());
                        vista.mostraMessaggio("Posiziona la nave di lunghezza " + lunghezza);
                        Point coord = leggiCoordinate("Inserisci coordinate di partenza (x y): ", dim);
                        boolean orientamento = leggiOrientamento("Inserisci orientamento (O per orizzontale, V per verticale): ");
                        try {
                            Nave nave = CantiereNavale.creaNave(lunghezza, coord, orientamento);
                            boolean risultato = giocatore.posizionaNave(nave, coord, orientamento);
                            if (risultato) {
                                vista.mostraMessaggio("Nave posizionata con successo.");
                                piazzato = true;
                            }
                        } catch (IllegalArgumentException e) {
                            vista.mostraMessaggio("Posizionamento non valido: " + e.getMessage());
                        }
                    }
                }
            } else {
                vista.mostraMessaggio("Il PC sta posizionando le proprie navi...");
                int dimensione = giocatore.getGrigliaPersonale().getDimensione();
                for (int lunghezza : lunghezzaNavi) {
                    boolean piazzato = false;
                    while (!piazzato) {
                        int x = random.nextInt(dimensione);
                        int y = random.nextInt(dimensione);
                        boolean orientamento = random.nextBoolean();
                        try {
                            Nave nave = CantiereNavale.creaNave(lunghezza, new Point(x, y), orientamento);
                            boolean risultato = giocatore.posizionaNave(nave, new Point(x, y), orientamento);
                            if (risultato) {
                                piazzato = true;
                            }
                        } catch (IllegalArgumentException e) {
                        }
                    }
                }
            }
        }
    }
    
    private Point leggiCoordinate(String message, int dim) {
        int x = -1, y = -1;
        boolean valid = false;
        while (!valid) {
            String input = vista.prompt(message);
            String[] tokens = input.trim().split("\\s+");
            if (tokens.length != 2) {
                vista.mostraMessaggio("Formato non valido. Inserisci due numeri separati da uno spazio.");
                continue;
            }
            try {
                x = Integer.parseInt(tokens[0]);
                y = Integer.parseInt(tokens[1]);
                if (x < 0 || x >= dim || y < 0 || y >= dim) {
                    vista.mostraMessaggio("Le coordinate devono essere comprese tra 0 e " + (dim - 1));
                    continue;
                }
                valid = true;
            } catch (NumberFormatException e) {
                vista.mostraMessaggio("Inserisci numeri validi.");
            }
        }
        return new Point(x, y);
    }
    
    private boolean leggiOrientamento(String message) {
        while (true) {
            String input = vista.prompt(message);
            if (input.trim().equalsIgnoreCase("O")) {
                return true;
            } else if (input.trim().equalsIgnoreCase("V")) {
                return false;
            } else {
                vista.mostraMessaggio("Inserisci un orientamento valido: O per orizzontale o V per verticale.");
            }
        }
    }
    
    /*
     * Metodo principale per l'esecuzione del gioco.
     * Il controller coordina il setup, il ciclo di gioco e comunica con la vista come da design pattern MVC.
     */
    public void gioca() {
        vista.mostraMessaggio("Inizio partita di Battaglia Navale");
        posizionaNavi();
        while (!gioco.isGameOver()) {
            Giocatore giocatoreCorrente = gioco.getGiocatoreCorrente();
            vista.mostraMessaggio("Turno di " + giocatoreCorrente.getNome());
            if (giocatoreCorrente.equals(gioco.getGiocatore1())) {
                vista.mostraMessaggio("\n GRIGLIA PERSONALE");
                vista.stampa(giocatoreCorrente.getGrigliaPersonale());
                vista.mostraMessaggio("\n GRIGLIA DI ATTACCO");
                vista.stampa(giocatoreCorrente.getGrigliaAttacco());
                vista.mostraMessaggio("Proiettili disponibili (Potenti Speciali): (" +
                        giocatoreCorrente.getProiettiliPotenti() + " " +
                        giocatoreCorrente.getProiettiliSpeciali() + ")");
                int dim = giocatoreCorrente.getGrigliaPersonale().getDimensione();
                Point coord = leggiCoordinate("Inserisci coordinate (x y): ", dim);
                int tipo = 0;
                boolean validTipo = false;
                while (!validTipo) {
                    String input = vista.prompt("Scegli tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale): ");
                    try {
                        tipo = Integer.parseInt(input.trim());
                        if (tipo < 1 || tipo > 3) {
                            vista.mostraMessaggio("Devi inserire 1, 2 o 3.");
                        } else {
                            validTipo = true;
                        }
                    } catch (NumberFormatException e) {
                        vista.mostraMessaggio("Inserisci un numero valido (1, 2 o 3).");
                    }
                }
                Proiettile proiettile;
                switch (tipo) {
                    case 2:
                        if (giocatoreCorrente.getProiettiliPotenti() > 0) {
                            proiettile = new ProiettilePotente();
                            giocatoreCorrente.decProiettiliPotenti();
                        } else {
                            vista.mostraMessaggio("Non hai proiettili potenti disponibili, utilizzo proiettile normale.");
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 3:
                        if (giocatoreCorrente.getProiettiliSpeciali() > 0) {
                            proiettile = new ProiettileSpeciale();
                            giocatoreCorrente.decProiettiliSpeciali();
                        } else {
                            vista.mostraMessaggio("Non hai proiettili speciali disponibili, utilizzo proiettile normale.");
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 1:
                    default:
                        proiettile = new ProiettileNormale();
                        break;
                }
                boolean impatto = gioco.eseguiTurno(coord, proiettile);
                if (impatto) {
                    vista.mostraMessaggio("Colpito!");
                } else {
                    vista.mostraMessaggio("Mancato!");
                }
            } else {
                int dim = giocatoreCorrente.getGrigliaPersonale().getDimensione();
                int x = random.nextInt(dim);
                int y = random.nextInt(dim);
                int tipo = random.nextInt(3) + 1;
                Proiettile proiettile;
                switch (tipo) {
                    case 2:
                        if (giocatoreCorrente.getProiettiliPotenti() > 0) {
                            proiettile = new ProiettilePotente();
                            giocatoreCorrente.decProiettiliPotenti();
                        } else {
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 3:
                        if (giocatoreCorrente.getProiettiliSpeciali() > 0) {
                            proiettile = new ProiettileSpeciale();
                            giocatoreCorrente.decProiettiliSpeciali();
                        } else {
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 1:
                    default:
                        proiettile = new ProiettileNormale();
                        break;
                }
                vista.mostraMessaggio("Il PC spara a (" + x + "," + y + ") con un proiettile " +
                        (tipo == 2 ? "Potente" : (tipo == 3 ? "Speciale" : "Normale")));
                boolean impatto = gioco.eseguiTurno(new Point(x, y), proiettile);
                if (impatto) {
                    vista.mostraMessaggio("Il PC ha colpito!");
                } else {
                    vista.mostraMessaggio("Il PC ha mancato!");
                }
            }
        }
        vista.mostraMessaggio("Il gioco è terminato! Il vincitore è " + gioco.getVincitore().getNome());
        vista.close();
    }
}

