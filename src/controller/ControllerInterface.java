/*
 * Interfaccia per i controller del gioco
 */
package controller;

public interface ControllerInterface {
     // Avvia il flusso di gioco.
    void startBattle();

     // Gestisce il posizionamento delle navi.
    void placeShips();
}
