/*
 * GestoreTurnoGiocatore.java: classe helper che gestisce il tuno di gioco del giocatore umano
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Giocatore;
import model.Model;
import model.Proiettile;
import view.Messaggi;
import view.View;
import java.awt.Point;

public class GestoreTurnoGiocatore {
    private Model gioco;
    private View vista;
    private GestoreProiettili gestoreProiettili;

    public GestoreTurnoGiocatore(Model gioco, View vista, GestoreProiettili gestoreProiettili) {
        this.gioco = gioco;
        this.vista = vista;
        this.gestoreProiettili = gestoreProiettili;
    }

    public void gestisciTurnoGiocatore(Giocatore giocatoreCorrente) {
        // Visualizza le griglie e i proiettili disponibili
        vista.mostraMessaggio(Messaggi.grigliaPersonale());
        vista.stampa(giocatoreCorrente.getGrigliaPersonale());
        vista.mostraMessaggio(Messaggi.grigliaAttacco());
        vista.stampa(giocatoreCorrente.getGrigliaAttacco());
        vista.mostraMessaggio(Messaggi.proiettiliDisponibili(
                giocatoreCorrente.getProiettiliPotenti(),
                giocatoreCorrente.getProiettiliSpeciali()));

        // Legge le coordinate inserite dall'utente
        int dim = giocatoreCorrente.getGrigliaPersonale().getDimensione();
        Point coord = vista.leggiCoordinate(Messaggi.chiediCoordinate(), dim);

        // Legge il tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale)
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

        // Crea il proiettile tramite il GestoreProiettili
        Proiettile proiettile = gestoreProiettili.creaProiettile(tipo, giocatoreCorrente);

        // Esegue il turno e mostra l'esito (Colpito / Mancato)
        boolean impatto = gioco.eseguiTurno(coord, proiettile);
        vista.mostraMessaggio(impatto ? Messaggi.messaggioColpito() : Messaggi.messaggioMancato());
    }
}
