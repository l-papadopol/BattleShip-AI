/*
 * Interfaccia per l'AI del computer - design pattern Strategy
 */
package controller;

import model.entities.Player;

public interface AITurnStrategy {
    boolean executeTurn(Player currentPlayer);
}
