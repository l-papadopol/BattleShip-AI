package controller;

import java.awt.Point;
import java.util.Random;

import model.CantiereNavale;
import model.Giocatore;
import model.Nave;
import view.Messaggi;
import view.View;

public class GestorePosizionamento {
    private View vista;
    private Random random;

    public GestorePosizionamento(View vista) {
        this.vista = vista;
        this.random = new Random();
    }

    /**
     * Posiziona tutte le navi per il giocatore umano, usando i messaggi e i metodi TUI/GUI.
     */
    public void posizionaNaviGiocatore(Giocatore giocatore, int[] lunghezzaNavi) {
        vista.mostraMessaggio(Messaggi.posizionamentoNavi(giocatore.getNome()));
        int dim = giocatore.getGrigliaPersonale().getDimensione();

        for (int lunghezza : lunghezzaNavi) {
            boolean piazzato = false;
            while (!piazzato) {
                // Mostra lo stato attuale della griglia
                vista.stampa(giocatore.getGrigliaPersonale());
                vista.mostraMessaggio(Messaggi.posizionaNave(lunghezza));

                // Legge coordinate e orientamento
                Point coord = vista.leggiCoordinate(Messaggi.inserisciCoordinatePartenza(), dim);
                boolean orientamento = vista.leggiOrientamento(Messaggi.inserisciOrientamento());

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
    }

    /**
     * Posiziona tutte le navi per il giocatore PC, in modo casuale.
     */
    public void posizionaNaviPC(Giocatore giocatore, int[] lunghezzaNavi) {
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
                    // Se la posizione è invalida, ritenta.
                }
            }
        }
    }
}

