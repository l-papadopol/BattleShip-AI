/*
 * GestoreTurnoPC.java: classe helper che contiene le strategie di gioco del PC
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Giocatore;
import model.Model;
import model.Proiettile;
import model.ProiettilePotente;
import model.ProiettileSpeciale;
import view.Messaggi;
import view.View;

import java.awt.Point;
import java.util.Random;

public class GestoreTurnoPC {
    private Model gioco;
    private View vista;
    private Random random;
    private int difficolta;
    private GestoreProiettili gestoreProiettili;

    public GestoreTurnoPC(Model gioco, View vista, int difficolta, GestoreProiettili gestoreProiettili) {
        this.gioco = gioco;
        this.vista = vista;
        this.difficolta = difficolta;
        this.gestoreProiettili = gestoreProiettili;
        this.random = new Random();
    }

    public void gestisciTurnoPC(Giocatore giocatoreCorrente) {
        if (difficolta == 1) {
            gestisciTurnoPCFacile(giocatoreCorrente);
        } else if (difficolta == 2) {
            gestisciTurnoPCMedio(giocatoreCorrente);
        } else if (difficolta == 3) {
            gestisciTurnoPCDifficile(giocatoreCorrente);
        }
    }

    // Difficoltà 1: il PC spara a caso
    private void gestisciTurnoPCFacile(Giocatore giocatoreCorrente) {
        int dim = giocatoreCorrente.getGrigliaPersonale().getDimensione();

        // Coordinate casuali
        int x = random.nextInt(dim);
        int y = random.nextInt(dim);

        // Tipo di proiettile casuale
        int tipo = random.nextInt(3) + 1;

        // Creo il proiettile
        Proiettile proiettile = gestoreProiettili.creaProiettile(tipo, giocatoreCorrente);

        // Determino la stringa per il messaggio in base all'istanza di proiettile
        String tipoProiettile;
        if (proiettile instanceof ProiettilePotente) {
            tipoProiettile = "Potente";
        } else if (proiettile instanceof ProiettileSpeciale) {
            tipoProiettile = "Speciale";
        } else {
            tipoProiettile = "Normale";
        }

        // Mostro il messaggio di sparo
        vista.mostraMessaggio(Messaggi.messaggioPCSpara(x, y, tipoProiettile));

        // Eseguo il turno
        boolean impatto = gioco.eseguiTurno(new Point(x, y), proiettile);

        // Mostro l'esito (Colpito / Mancato)
        vista.mostraMessaggio(impatto ? Messaggi.messaggioPCColpito() : Messaggi.messaggioPCMancato());
    }

    // Difficoltà 2: stub per logica più avanzata (da implementare)
    private void gestisciTurnoPCMedio(Giocatore giocatoreCorrente) {
        // Logica più sofisticata da implementare in futuro
        gestisciTurnoPCFacile(giocatoreCorrente);
    }

    // Difficoltà 3: stub per una pseudo-AI (da implementare)
    private void gestisciTurnoPCDifficile(Giocatore giocatoreCorrente) {
        // Logica pseudo-AI da implementare in futuro
        gestisciTurnoPCFacile(giocatoreCorrente);
    }
}
