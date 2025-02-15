/*
 * CantiereNavale.java costruisce navi di lunghezza ed orientamento determinabile da un punto di origine
 * 
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CantiereNavale {
    public static Nave creaNave(int lunghezza, Point origine, boolean orizzontale) {
        List<Casella> caselleNave = new ArrayList<>();
        int x = origine.x;
        int y = origine.y;
        for (int i = 0; i < lunghezza; i++) {
            int xCoord = orizzontale ? x + i : x;
            int yCoord = orizzontale ? y : y + i;
            caselleNave.add(new Casella(new Point(xCoord, yCoord)));
        }
        return new Nave(caselleNave);
    }
}
