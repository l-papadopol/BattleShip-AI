/*
 * View.java è l'interfaccia della view
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import java.awt.Point;

import model.Griglia;

public interface View {
    void mostraMessaggio(String message);
    String prompt(String message);
    void stampa(Griglia griglia);
    Point leggiCoordinate(String message, int dim);
    boolean leggiOrientamento(String message);
    void chiudi();
}
