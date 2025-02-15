/*
 * BattagliaNavale.java modella il gioco Battaglia Navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class BattagliaNavale {
    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private int turno; // 1 = giocatore1, 2 = giocatore2

    public BattagliaNavale(String nomeGiocatore1, String nomeGiocatore2, int dimensioneGriglia) {
        this.giocatore1 = new Giocatore(nomeGiocatore1, dimensioneGriglia);
        this.giocatore2 = new Giocatore(nomeGiocatore2, dimensioneGriglia);
        this.turno = 1;
    }

    // Getter forse utili
    public Giocatore getGiocatore1() {
        return giocatore1;
    }

    public Giocatore getGiocatore2() {
        return giocatore2;
    }

    public int getTurno() {
        return turno;
    }

    
    // Cambia il turno tra i due giocatori
    public void cambiaTurno() {
        turno = (turno == 1) ? 2 : 1;
    }

    // Restituisce il giocatore attuale
    public Giocatore getGiocatoreAttuale() {
        return (turno == 1) ? giocatore1 : giocatore2;
    }

    // Restituisce l'avversario
    public Giocatore getAvversario() {
        return (turno == 1) ? giocatore2 : giocatore1;
    }

    // Spara un colpo per ogni turno e verifica se hai colpito qualche cosa
    public boolean eseguiTurno(int x, int y, Proiettile proiettile) {
        Giocatore attuale = getGiocatoreAttuale();
        Giocatore avversario = getAvversario();
        Point coordinataAttacco = new Point(x,y);
        boolean colpito = attuale.spara(coordinataAttacco, proiettile, avversario);
        cambiaTurno();
        return colpito;
    }

    // Controlla se c'è un vincitore
    public String verificaVincitore() {
        if (giocatore1.haPerso()) {
            return giocatore2.getNome();
        }
        if (giocatore2.haPerso()) {
            return giocatore1.getNome();
        }
        return null;
    }
}
