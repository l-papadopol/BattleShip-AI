package model.entities;

import java.util.List;

/**
 * Nave.java modella una nave composta da una lista di caselle che occupa.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe rappresenta una nave nel gioco Battaglia Navale, definita come una lista di
 * {@link GridSquare} che indicano le caselle occupate dalla nave.
 */
public class Ship {
    private List<GridSquare> gridSquares;
    
    /**
     * Costruisce una nuova nave con le caselle specificate.
     *
     * @param gridSquares la lista di caselle che compongono la nave
     */
    public Ship(List<GridSquare> gridSquares) {
        this.gridSquares = gridSquares;
    }
    
    /**
     * Verifica se la nave è affondata.
     * La nave è considerata affondata se tutte le caselle che la compongono hanno raggiunto il danno massimo.
     *
     * @return {@code true} se la nave è affondata, {@code false} altrimenti
     */
    public boolean isSunk() {
        return gridSquares.stream()
                          .allMatch(gridSquare -> gridSquare.getDamageLevel() >= gridSquare.getMaxResistance());
    }
    
    /**
     * Restituisce la lunghezza della nave, ovvero il numero di caselle che occupa.
     *
     * @return la lunghezza della nave
     */
    public int getLenght() {
        return gridSquares.size();
    }
    
    /**
     * Restituisce la lista delle caselle che compongono la nave.
     *
     * @return la lista di {@link GridSquare} che compongono la nave
     */
    public List<GridSquare> getGridSquares() {
        return gridSquares;
    }
}
