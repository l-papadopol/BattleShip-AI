package controller;

/**
 * Interfaccia per i controller del gioco.
 * Fornisce le operazioni fondamentali per gestire il flusso di gioco.
 */
public interface ControllerInterface {
    
    /**
     * Gestisce il posizionamento delle navi da parte del giocatore o dell'IA.
     */
    void placeShips();
    
    /**
     * Avvia il flusso di gioco.
     */
    void startBattle();
}
