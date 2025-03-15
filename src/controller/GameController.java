/*
 * Implementazione del controller del gioco che si frppone tra model e view
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Player;

import java.util.Random;

import model.ModelInterface;
import view.Messages;
import view.ViewInterface;

public class GameController implements ControllerInterface {
	private ModelInterface battle;
	private ViewInterface view;

	// Classi helper
	private ShipPlacingHandler shipPlacingHandler;
	private ProjectileHandler projectileHandler;
	private PcTurnHandler pcTurnHandler;
	private PlayerTurnHandler humanTurnHandler;

	public GameController(ModelInterface model, ViewInterface view, int difficultyLevel) {
		this.battle = model;
		this.view = view;

		// Inizializziamo i gestori (classi helper)
		this.shipPlacingHandler = new ShipPlacingHandler(view);
		this.projectileHandler = new ProjectileHandler(view);
		this.pcTurnHandler = new PcTurnHandler(battle, view, difficultyLevel, projectileHandler);
		this.humanTurnHandler = new PlayerTurnHandler(battle, view, projectileHandler);
	}
	
	/*
	 * Gestisce il piazzamento delle navi
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
	
	/*
	 * Gestisce l'avvio, la turnazione, la fine ed i bonus casuali della partita
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

	/*
	 * Gestisce i bonus casuali
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

