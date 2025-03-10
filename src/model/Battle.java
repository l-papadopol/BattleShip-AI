/*
 * BattagliaNavale.java modella il gioco Battaglia Navale
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class Battle implements ModelInterface {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameOver = false;
    }

    /*
     * Esegue il turno del giocatore corrente.
     * Il metodo si occupa di far sparare il giocatore corrente sull’avversario,
     * di verificare la condizione di vittoria e di cambiare turno.
     */
    public boolean executeTurn(Point coordinates, Projectile projectile) {
        if (gameOver) {
            throw new IllegalStateException("Il gioco è terminato");
        }
        
        Player enemy = (currentPlayer == player1) ? player2 : player1;
        boolean hit = currentPlayer.shoot(coordinates, projectile, enemy);
        
        if (enemy.heLost()) {
            gameOver = true;
        } else {
            changeTurn();
        }
        return hit;
    }
    
    private void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public Player getWinner() {
        return gameOver ? currentPlayer : null;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Player getFirstPlayer() {
        return player1;
    }
    
    public Player getSecondPlayer() {
        return player2;
    }
}


