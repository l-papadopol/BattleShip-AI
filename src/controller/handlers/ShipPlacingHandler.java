package controller.handlers;

import java.awt.Point;
import java.util.Random;

import model.builders.ShipBuilder;
import model.entities.Player;
import model.entities.Ship;
import view.ViewInterface;
import view.components.Messages;

/**
 * Classe helper che gestisce il posizionamento delle navi per i giocatori.
 * Fornisce metodi per posizionare la flotta del giocatore umano e quella del giocatore PC.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class ShipPlacingHandler {
    private ViewInterface view;
    private Random random;

    /**
     * Costruisce un nuovo ShipPlacingHandler.
     *
     * @param view la vista del gioco, utilizzata per mostrare messaggi e lo stato della griglia
     */
    public ShipPlacingHandler(ViewInterface view) {
        this.view = view;
        this.random = new Random();
    }

    /**
     * Posiziona tutte le navi per il giocatore umano.
     * Visualizza il messaggio di posizionamento della flotta, mostra la griglia attualee 
     * chiede all'utente di inserire le coordinate di partenza e l'orientamento per ciascuna nave.
     *
     * @param player il giocatore umano che posiziona la flotta
     * @param shipLenghts un array di interi che rappresenta le lunghezze delle navi da posizionare
     */
    public void placeHumanFleet(Player player, int[] shipLenghts) {
        view.showMsg(Messages.fleetPlacing(player.getName()));
        int dim = player.getPersonalGrid().getSize();

        for (int lenght : shipLenghts) {
            boolean isPlaced = false;
            while (!isPlaced) {
                // Mostra lo stato attuale della griglia
                view.gridDrawing(player.getPersonalGrid());
                view.showMsg(Messages.placeShip(lenght));

                // Legge coordinate e orientamento
                Point coord = view.askCoordinates(Messages.askStartCoordinates(), dim);
                boolean orientering = view.askOrientation(Messages.askOrientation());

                try {
                    Ship ship = ShipBuilder.buildShip(lenght, coord, orientering);
                    boolean isBuiltAndPlaced = player.placeShip(ship, coord, orientering);
                    if (isBuiltAndPlaced) {
                        view.showMsg(Messages.shipSuccessfullyPlaced());
                        isPlaced = true;
                    }
                } catch (IllegalArgumentException e) {
                    view.showMsg(Messages.invalidPositioning(e.getMessage()));
                }
            }
        }
    }

    /**
     * Posiziona tutte le navi per il giocatore PC in modo casuale.
     * Viene visualizzato un messaggio indicante che il PC sta posizionando la flotta, e per ciascuna nave viene scelto casualmente 
     * un punto e un orientamento fino a trovare una posizione valida.
     *
     * @param player il giocatore PC che posiziona la flotta
     * @param shipLenghts un array di interi che rappresenta le lunghezze delle navi da posizionare
     */
    public void placePcFleet(Player player, int[] shipLenghts) {
        view.showMsg(Messages.pcIsPositioningFleet());
        int size = player.getPersonalGrid().getSize();

        for (int lenght : shipLenghts) {
            boolean isPlaced = false;
            while (!isPlaced) {
                int x = random.nextInt(size);
                int y = random.nextInt(size);
                boolean orientering = random.nextBoolean();
                try {
                    Ship ship = ShipBuilder.buildShip(lenght, new Point(x, y), orientering);
                    boolean result = player.placeShip(ship, new Point(x, y), orientering);
                    if (result) {
                        isPlaced = true;
                    }
                } catch (IllegalArgumentException e) {
                    // Se la posizione è invalida, ritenta.
                }
            }
        }
    }
}


