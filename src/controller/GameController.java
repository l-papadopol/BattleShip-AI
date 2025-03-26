package controller;

import java.util.Random;

import controller.handlers.PcTurnHandler;
import controller.handlers.PlayerTurnHandler;
import controller.handlers.ProjectileHandler;
import controller.handlers.ShipPlacingHandler;
import model.ModelInterface;
import model.entities.Player;
import view.ViewInterface;
import view.components.Messages;

/**
 * Implementazione del controller del gioco che si frppone tra model e view.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe gestisce il flusso della partita, il posizionamento delle navi,
 * la turnazione e la gestione dei bonus, coordinando le operazioni tra il modello e la vista.
 */
public class GameController implements ControllerInterface {
    private ModelInterface battle;
    private ViewInterface view;

    // Classi helper per la gestione delle operazioni di gioco
    private ShipPlacingHandler shipPlacingHandler;
    private ProjectileHandler projectileHandler;
    private PcTurnHandler pcTurnHandler;
    private PlayerTurnHandler humanTurnHandler;

    /**
     * Costruisce un nuovo GameController.
     *
     * @param model il modello del gioco
     * @param view la vista del gioco
     * @param difficultyLevel il livello di difficoltà per l'IA
     */
    public GameController(ModelInterface model, ViewInterface view, int difficultyLevel) {
        this.battle = model;
        this.view = view;

        // Inizializziamo i gestori (classi helper)
        this.shipPlacingHandler = new ShipPlacingHandler(view);
        this.projectileHandler = new ProjectileHandler(view);
        this.pcTurnHandler = new PcTurnHandler(battle, view, difficultyLevel, projectileHandler);
        this.humanTurnHandler = new PlayerTurnHandler(battle, view, projectileHandler);
    }
    
    /**
     * Gestisce il piazzamento delle navi per entrambi i giocatori.
     * Invoca i metodi di posizionamento per il giocatore umano e per il PC.
     */
    @Override
    public void placeShips() {
        Player g1 = battle.getFirstPlayer();
        Player g2 = battle.getSecondPlayer();

        int[] defaultShipsLenght = {5, 4, 4, 3, 3, 3, 2, 2, 1};

        // Posizionamento navi per il giocatore umano
        shipPlacingHandler.placeHumanFleet(g1, defaultShipsLenght);

        // Posizionamento navi per il giocatore PC
        shipPlacingHandler.placePcFleet(g2, defaultShipsLenght);
    }
    
    /**
     * Avvia la battaglia.
     * Visualizza il messaggio di inizio partita, piazza le navi e gestisce il turno di gioco,
     * alternando il turno tra il giocatore umano e il PC. Al termine della partita,
     * mostra il vincitore e chiude la vista.
     */
    @Override
    public void startBattle() {
        view.showMsg(Messages.gameStart());
        placeShips();

        while (!battle.isGameOver()) {
            Player currentPlayer = battle.getCurrentPlayer();
            view.showMsg(Messages.humanTurn(currentPlayer.getName()));
            boolean hit;
            if (currentPlayer.equals(battle.getFirstPlayer())) {
                hit = humanTurnHandler.handleHumanTurn(currentPlayer);
            } else {
                hit = pcTurnHandler.handlePcTurn(currentPlayer);
            }
            // Se il tiro è andato a vuoto, valuta e applica il bonus delle casse rifornimenti random
            if (!hit) {
                applyBonus(currentPlayer);
            }
        }

        view.showMsg(Messages.gameOver(battle.getWinner().getName()));
        view.close();
    }

    /**
     * Applica un bonus casuale al giocatore corrente se il tiro è andato a vuoto.
     * Il bonus viene attivato con una probabilità del 10% e può essere:
     * <ul>
     *   <li>2 colpi speciali, oppure</li>
     *   <li>3 colpi potenti.</li>
     * </ul>
     *
     * @param currentPlayer il giocatore corrente a cui applicare il bonus
     */
    private void applyBonus(Player currentPlayer) {
        Random random = new Random();
        // Bonus attivo con probabilità del 10%
        if (random.nextDouble() < 0.1) { 
            if (random.nextBoolean()) {
                // Bonus: 2 colpi speciali
                currentPlayer.addSpecialProjectileQty(2);
                view.showMsg(Messages.bonusReceived("colpi speciali", 2));
            } else {
                // Bonus: 3 colpi potenti
                currentPlayer.addPowerfulProjectileQty(3);
                view.showMsg(Messages.bonusReceived("colpi potenti", 3));
            }
        }
    }
}
