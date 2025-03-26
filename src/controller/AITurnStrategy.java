package controller;

import model.entities.Player;

/**
 * Interfaccia per l'AI del computer che implementa il design pattern Strategy.
 * Le classi che implementano questa interfaccia definiscono la logica per eseguire il turno
 * del giocatore controllato dall'AI.
 */
public interface AITurnStrategy {

    /**
     * Esegue il turno per il giocatore corrente.
     *
     * @param currentPlayer il giocatore corrente per il quale eseguire il turno
     * @return {@code true} se il turno viene eseguito con successo, {@code false} altrimenti
     */
    boolean executeTurn(Player currentPlayer);
}
