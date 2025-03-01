/*
 * Model.java: Interfaccia per il modello del gioco
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public interface Model {
     // Esegue il turno del giocatore corrente.

    boolean eseguiTurno(Point coordinate, Proiettile proiettile);

     // Indica se il gioco è terminato.
    boolean isGameOver();

     // Restituisce il giocatore vincitore, se il gioco è terminato.
    Giocatore getVincitore();

    // Restituisce il giocatore corrente.
    Giocatore getGiocatoreCorrente();

     // Restituisce il primo giocatore.
    Giocatore getGiocatore1();

     // Restituisce il secondo giocatore.
    Giocatore getGiocatore2();
}
