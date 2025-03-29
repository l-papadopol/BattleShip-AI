package model.builders;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.entities.GridSquare;
import model.entities.Ship;

/**
 * Costruisce navi di lunghezza ed orientamento determinato da un punto di origine.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe fornisce un metodo statico per costruire una nave a partire da un punto di origine e specificare l'orientamento (orizzontale o verticale).
 */
public class ShipBuilder {
	
    /**
     * Costruisce una nave.
     *
     * @param lenght la lunghezza della nave
     * @param pointOfOrigin il punto di origine da cui iniziare a costruire la nave
     * @param isHorizontal indica se la nave deve essere posizionata in senso orizzontale (true) o verticale (false)
     * @return un'istanza di {@link Ship} costituita da una lista di {@link GridSquare} disposti secondo l'orientamento specificato
     */
    public static Ship buildShip(int lenght, Point pointOfOrigin, boolean isHorizontal) {
        List<GridSquare> gridSquares = new ArrayList<>();
        int x = pointOfOrigin.x;
        int y = pointOfOrigin.y;
        for (int i = 0; i < lenght; i++) {
            int xCoord = isHorizontal ? x + i : x;
            int yCoord = isHorizontal ? y : y + i;
            gridSquares.add(new GridSquare(new Point(xCoord, yCoord)));
        }
        return new Ship(gridSquares);
    }
}
