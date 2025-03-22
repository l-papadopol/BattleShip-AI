package controller.handlers;

import java.awt.Point;
import java.util.Random;

import model.builders.ShipBuilder;
import model.entities.Player;
import model.entities.Ship;
import view.ViewInterface;
import view.components.Messages;

public class ShipPlacingHandler {
    private ViewInterface view;
    private Random random;

    public ShipPlacingHandler(ViewInterface view) {
        this.view = view;
        this.random = new Random();
    }

    /*
     * Posiziona tutte le navi per il giocatore umano
     */
    public void placeHumanFleet(Player player, int[] shipLenghts) {
        view.showMsg(Messages.fleetPlacing(player.getName()));;
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

    /*
     * Posiziona tutte le navi per il giocatore PC, in modo casuale.
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

