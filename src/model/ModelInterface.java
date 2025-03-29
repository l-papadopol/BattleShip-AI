package model;

import java.awt.Point;

import model.entities.Player;
import model.entities.Projectile;

/**
 * Interfaccia per il modello del gioco.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Definisce il contratto per la gestione del turno di gioco e l'accesso allo stato della partita.
 */
public interface ModelInterface {
    /**
     * Esegue il turno del giocatore corrente.
     *
     * @param coordinate le coordinate in cui sparare
     * @param proiettile il proiettile da utilizzare per il tiro
     * @return {@code true} se il tiro ha colpito il bersaglio, {@code false} altrimenti
     */
    boolean executeTurn(Point coordinate, Projectile proiettile);

    /**
     * Indica se il gioco è terminato.
     *
     * @return {@code true} se il gioco è finito, {@code false} altrimenti
     */
    boolean isGameOver();

    /**
     * Restituisce il giocatore vincitore, se il gioco è terminato.
     *
     * @return il giocatore vincente, oppure {@code null} se il gioco non è terminato
     */
    Player getWinner();

    /**
     * Restituisce il giocatore corrente.
     *
     * @return il giocatore attualmente in turno
     */
    Player getCurrentPlayer();

    /**
     * Restituisce il primo giocatore.
     *
     * @return il primo giocatore
     */
    Player getFirstPlayer();

    /**
     * Restituisce il secondo giocatore.
     *
     * @return il secondo giocatore
     */
    Player getSecondPlayer();
}
