/*
 * costruisce navi di lunghezza ed orientamento determinato da un punto di origine
 * 
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ShipBuilder {
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
