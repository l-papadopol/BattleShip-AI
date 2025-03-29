package model;

import java.awt.Point;

import model.entities.Player;
import model.entities.Projectile;

/**
 * BattagliaNavale.java modella il gioco Battaglia Navale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe implementa l'interfaccia ModelInterface e gestisce lo stato del gioco,
 * inclusi i turni dei giocatori, l'esecuzione dei tiri e la verifica della condizione di vittoria.
 */
public class Battle implements ModelInterface {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    /**
     * Costruisce una nuova battaglia navale tra due giocatori.
     *
     * @param player1 il primo giocatore
     * @param player2 il secondo giocatore
     */
    public Battle(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameOver = false;
    }

    /**
     * Esegue il turno del giocatore corrente.
     * Il metodo si occupa di far sparare il giocatore corrente sull’avversario di verificare la condizione di vittoria e di cambiare turno.
     *
     * @param coordinates le coordinate in cui sparare
     * @param projectile il proiettile da utilizzare
     * @return {@code true} se il tiro ha colpito il bersaglio, {@code false} altrimenti
     * @throws IllegalStateException se il gioco è terminato
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
    
    /**
     * Cambia il turno passando al giocatore successivo.
     */
    private void changeTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
    
    /**
     * Verifica se il gioco è terminato.
     *
     * @return {@code true} se il gioco è finito, {@code false} altrimenti
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * Restituisce il vincitore della partita.
     *
     * @return il giocatore vincente se il gioco è terminato, {@code null} altrimenti
     */
    public Player getWinner() {
        return gameOver ? currentPlayer : null;
    }
    
    /**
     * Restituisce il giocatore il cui turno è attualmente in corso.
     *
     * @return il giocatore corrente
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Restituisce il primo giocatore.
     *
     * @return il primo giocatore
     */
    public Player getFirstPlayer() {
        return player1;
    }
    
    /**
     * Restituisce il secondo giocatore.
     *
     * @return il secondo giocatore
     */
    public Player getSecondPlayer() {
        return player2;
    }
}


