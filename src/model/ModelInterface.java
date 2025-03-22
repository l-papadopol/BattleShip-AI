/*
 * Interfaccia per il modello del gioco
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

import model.entities.Player;
import model.entities.Projectile;

public interface ModelInterface {
     // Esegue il turno del giocatore corrente.
    boolean executeTurn(Point coordinate, Projectile proiettile);

     // Indica se il gioco è terminato.
    boolean isGameOver();

     // Restituisce il giocatore vincitore, se il gioco è terminato.
    Player getWinner();

    // Restituisce il giocatore corrente.
    Player getCurrentPlayer();

     // Restituisce il primo giocatore.
    Player getFirstPlayer();

     // Restituisce il secondo giocatore.
    Player getSecondPlayer();
}
