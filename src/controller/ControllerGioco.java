/*
 * ControllerGioco.java: Implementazione del controller del gioco che si interfaccia fra model e view
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.CantiereNavale;
import model.Giocatore;
import model.Model;
import model.Nave;
import model.Proiettile;
import model.ProiettileNormale;
import model.ProiettilePotente;
import model.ProiettileSpeciale;
import view.Messaggi;
import view.View;

import java.awt.Point;
import java.util.Random;

public class ControllerGioco implements Controller {
    private Model gioco;
    private View vista;
    private Random random;
    
    public ControllerGioco(Model model, View vista) {
        this.gioco = model;
        this.vista = vista;
        this.random = new Random();
    }
    
    @Override
    public void gioca() {
        vista.mostraMessaggio(Messaggi.inizioPartita());
        posizionaNavi();
        while (!gioco.isGameOver()) {
            Giocatore giocatoreCorrente = gioco.getGiocatoreCorrente();
            vista.mostraMessaggio(Messaggi.turnoGiocatore(giocatoreCorrente.getNome()));
            if (giocatoreCorrente.equals(gioco.getGiocatore1())) {
                vista.mostraMessaggio(Messaggi.grigliaPersonale());
                vista.stampa(giocatoreCorrente.getGrigliaPersonale());
                vista.mostraMessaggio(Messaggi.grigliaAttacco());
                vista.stampa(giocatoreCorrente.getGrigliaAttacco());
                vista.mostraMessaggio(Messaggi.proiettiliDisponibili(
                        giocatoreCorrente.getProiettiliPotenti(),
                        giocatoreCorrente.getProiettiliSpeciali()));
                int dim = giocatoreCorrente.getGrigliaPersonale().getDimensione();
                Point coord = vista.leggiCoordinate(Messaggi.chiediCoordinate(), dim);
                int tipo = 0;
                boolean validTipo = false;
                while (!validTipo) {
                    String input = vista.prompt(Messaggi.scegliTipoProiettile());
                    try {
                        tipo = Integer.parseInt(input.trim());
                        if (tipo < 1 || tipo > 3) {
                            vista.mostraMessaggio(Messaggi.messaggioErroreTipo());
                        } else {
                            validTipo = true;
                        }
                    } catch (NumberFormatException e) {
                        vista.mostraMessaggio(Messaggi.messaggioErroreNumero());
                    }
                }
                Proiettile proiettile;
                switch (tipo) {
                    case 2:
                        if (giocatoreCorrente.getProiettiliPotenti() > 0) {
                            proiettile = new ProiettilePotente();
                            giocatoreCorrente.decProiettiliPotenti();
                        } else {
                            vista.mostraMessaggio(Messaggi.messaggioProiettilePotenteNonDisponibile());
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 3:
                        if (giocatoreCorrente.getProiettiliSpeciali() > 0) {
                            proiettile = new ProiettileSpeciale();
                            giocatoreCorrente.decProiettiliSpeciali();
                        } else {
                            vista.mostraMessaggio(Messaggi.messaggioProiettileSpecialeNonDisponibile());
                            proiettile = new ProiettileNormale();
                        }
                        break;
                    case 1:
                    default:
                        proiettile = new ProiettileNormale();
                        break;
                }
                boolean impatto = gioco.eseguiTurno(coord, proiettile);
                vista.mostraMessaggio(impatto ? Messaggi.messaggioColpito() : Messaggi.messaggioMancato());
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
                String tipoString = (tipo == 2) ? "Potente" : (tipo == 3) ? "Speciale" : "Normale";
                vista.mostraMessaggio(Messaggi.messaggioPCSpara(x, y, tipoString));
                boolean impatto = gioco.eseguiTurno(new Point(x, y), proiettile);
                vista.mostraMessaggio(impatto ? Messaggi.messaggioPCColpito() : Messaggi.messaggioPCMancato());
            }
        }
        vista.mostraMessaggio(Messaggi.partitaTerminata(gioco.getVincitore().getNome()));
        vista.chiudi();
    }
    
    @Override
    public void posizionaNavi() {
        Giocatore[] giocatori = { gioco.getGiocatore1(), gioco.getGiocatore2() };
        int[] lunghezzaNavi = { 5, 4, 4, 3, 3, 3, 2, 2, 1 };
        
        for (int i = 0; i < giocatori.length; i++) {
            Giocatore giocatore = giocatori[i];
            if (i == 0) {
                vista.mostraMessaggio(Messaggi.posizionamentoNavi(giocatore.getNome()));
                int dim = giocatore.getGrigliaPersonale().getDimensione();
                for (int lunghezza : lunghezzaNavi) {
                    boolean piazzato = false;
                    while (!piazzato) {
                        vista.stampa(giocatore.getGrigliaPersonale());
                        vista.mostraMessaggio(Messaggi.posizionaNave(lunghezza));
                        Point coord = vista.leggiCoordinate("Inserisci coordinate di partenza (x y): ", dim);
                        boolean orientamento = vista.leggiOrientamento("Inserisci orientamento (O per orizzontale, V per verticale): ");
                        try {
                            Nave nave = CantiereNavale.creaNave(lunghezza, coord, orientamento);
                            boolean risultato = giocatore.posizionaNave(nave, coord, orientamento);
                            if (risultato) {
                                vista.mostraMessaggio(Messaggi.navePosizionata());
                                piazzato = true;
                            }
                        } catch (IllegalArgumentException e) {
                            vista.mostraMessaggio(Messaggi.posizionamentoNonValido(e.getMessage()));
                        }
                    }
                }
            } else {
                vista.mostraMessaggio(Messaggi.pcPosizionamentoNavi());
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
                            // Ignora e riprova
                        }
                    }
                }
            }
        }
    }
}
