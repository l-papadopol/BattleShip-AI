package controller.handlers;

import controller.AITurnStrategy;
import controller.ai.EasyStrategy;
import controller.ai.HardStrategy;
import controller.ai.MediumStrategy;
import model.ModelInterface;
import model.entities.Player;
import view.ViewInterface;

/**
 * Classe helper che contiene le strategie di gioco del PC per i livelli 1, 2 e 3.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 * 
 * Questa classe seleziona e istanzia la strategia di gioco appropriata in base al livello di difficoltà.
 */
public class PcTurnHandler {
    private AITurnStrategy strategy;

    /**
     * Costruisce un nuovo PcTurnHandler che gestisce il turno del PC selezionando la strategia appropriata.
     *
     * @param battle il modello di gioco
     * @param view la vista del gioco
     * @param difficultyLevel il livello di difficoltà (1: facile, 2: medio, 3: difficile)
     * @param projectileHandler il gestore dei proiettili
     */
    public PcTurnHandler(ModelInterface battle, ViewInterface view, int difficultyLevel, ProjectileHandler projectileHandler) {
        // In base al livello di difficoltà, istanzia la strategia appropriata.
        switch (difficultyLevel) {
            case 1:
                strategy = new EasyStrategy(battle, view, projectileHandler);
                break;
            case 2:
                strategy = new MediumStrategy(battle, view, projectileHandler);
                break;
            case 3:
                strategy = new HardStrategy(battle, view, projectileHandler);
                break;
            default:
                strategy = new EasyStrategy(battle, view, projectileHandler);
                break;
        }
    }

    /**
     * Gestisce il turno del PC eseguendo la strategia selezionata.
     *
     * @param currentPlayer il giocatore corrente controllato dal PC
     * @return {@code true} se il tiro eseguito dalla strategia ha colpito un bersaglio, {@code false} altrimenti
     */
    public boolean handlePcTurn(Player currentPlayer) {
        return strategy.executeTurn(currentPlayer);
    }
}
