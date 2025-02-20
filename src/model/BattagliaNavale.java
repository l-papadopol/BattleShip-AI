/*
 * BattagliaNavale.java modella il gioco Battaglia Navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class BattagliaNavale {
    private Giocatore giocatore1;
    private Giocatore giocatore2;
    private Giocatore currentPlayer;
    private boolean gameOver;

    /**
     * Costruttore che riceve i due giocatori già inizializzati e con le griglie
     * opportunamente configurate (inclusa la grigliaAttacco).
     */
    public BattagliaNavale(Giocatore giocatore1, Giocatore giocatore2) {
        this.giocatore1 = giocatore1;
        this.giocatore2 = giocatore2;
        // Il gioco inizia con il primo giocatore
        this.currentPlayer = giocatore1;
        this.gameOver = false;
    }

    /**
     * Esegue il turno del giocatore corrente.
     * 
     * @param coordinate la coordinata in cui sparare
     * @param proiettile il proiettile da utilizzare
     * @return true se il tiro ha colpito una nave, false altrimenti.
     * @throws IllegalStateException se il gioco è già terminato.
     */
    public boolean eseguiTurno(Point coordinate, Proiettile proiettile) {
        if (gameOver) {
            throw new IllegalStateException("Il gioco è terminato");
        }
        
        // Determiniamo l'avversario
        Giocatore opponent = (currentPlayer == giocatore1) ? giocatore2 : giocatore1;
        
        // Il giocatore corrente spara sull'avversario
        boolean hit = currentPlayer.spara(coordinate, proiettile, opponent);
        
        // Verifica se l'avversario ha perso (tutte le navi affondate)
        if (opponent.haPerso()) {
            gameOver = true;
        } else {
            // Se il gioco non è finito, il turno passa all'altro giocatore
            currentPlayer = opponent;
        }
        
        return hit;
    }

    /**
     * Restituisce true se il gioco è terminato.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Restituisce il giocatore vincitore, oppure null se il gioco non è ancora terminato.
     */
    public Giocatore getWinner() {
        return gameOver ? currentPlayer : null;
    }

    /**
     * Restituisce il giocatore il cui turno è corrente.
     */
    public Giocatore getCurrentPlayer() {
        return currentPlayer;
    }

    public Giocatore getGiocatore1() {
        return giocatore1;
    }

    public Giocatore getGiocatore2() {
        return giocatore2;
    }
}

