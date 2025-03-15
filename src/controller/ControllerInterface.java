/*
 * Interfaccia per i controller del gioco
 */
package controller;

public interface ControllerInterface {
	
    // Gestisce il posizionamento delle navi.
   void placeShips();
   
     // Avvia il flusso di gioco.
    void startBattle();
}
