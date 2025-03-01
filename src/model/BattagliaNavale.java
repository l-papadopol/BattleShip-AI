/*
 * BattagliaNavale.java modella il gioco Battaglia Navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class BattagliaNavale implements Model {
    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private Giocatore giocatoreCorrente;
    private boolean gameOver;

    public BattagliaNavale(Giocatore giocatore1, Giocatore giocatore2) {
        this.giocatore1 = giocatore1;
        this.giocatore2 = giocatore2;
        this.giocatoreCorrente = giocatore1;
        this.gameOver = false;
    }

    /*
     * Esegue il turno del giocatore corrente.
     * Il metodo si occupa di far sparare il giocatore corrente sull’avversario,
     * di verificare la condizione di vittoria e di cambiare turno.
     */
    public boolean eseguiTurno(Point coordinate, Proiettile proiettile) {
        if (gameOver) {
            throw new IllegalStateException("Il gioco è terminato");
        }
        
        Giocatore avversario = (giocatoreCorrente == giocatore1) ? giocatore2 : giocatore1;
        boolean impatto = giocatoreCorrente.spara(coordinate, proiettile, avversario);
        
        if (avversario.haPerso()) {
            gameOver = true;
        } else {
            cambiaTurno();
        }
        return impatto;
    }
    
    private void cambiaTurno() {
        giocatoreCorrente = (giocatoreCorrente == giocatore1) ? giocatore2 : giocatore1;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public Giocatore getVincitore() {
        return gameOver ? giocatoreCorrente : null;
    }
    
    public Giocatore getGiocatoreCorrente() {
        return giocatoreCorrente;
    }
    
    public Giocatore getGiocatore1() {
        return giocatore1;
    }
    
    public Giocatore getGiocatore2() {
        return giocatore2;
    }
}


