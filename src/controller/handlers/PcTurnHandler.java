/*
 * Classe helper che contiene le strategie di gioco del PC livelli 1, 2 e 3.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller.handlers;

import controller.AITurnStrategy;
import controller.ai.EasyStrategy;
import controller.ai.HardStrategy;
import controller.ai.MediumStrategy;
import model.ModelInterface;
import model.entities.Player;
import view.ViewInterface;

public class PcTurnHandler {
    private AITurnStrategy strategy;

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

    public boolean handlePcTurn(Player currentPlayer) {
        return strategy.executeTurn(currentPlayer);
    }
}
