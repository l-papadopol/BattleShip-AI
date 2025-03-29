package model.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Griglia.java modella la griglia di gioco.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe rappresenta la griglia di gioco utilizzata per posizionare le navi e gestire i tiri durante la partita.
 */
public class Grid {
    private int size;
    private GridSquare[][] gridSquares;
    private List<Ship> ships;

    /**
     * Costruisce una nuova griglia quadrata di dimensione specificata.
     * Inizializza tutte le caselle della griglia e la lista delle navi.
     *
     * @param size la dimensione della griglia (numero di righe/colonne)
     */
    public Grid(int size) {
        this.size = size;
        this.gridSquares = new GridSquare[size][size];
        this.ships = new ArrayList<>();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                gridSquares[x][y] = new GridSquare(new Point(x, y));
            }
        }
    }

    /**
     * Posiziona una nave nella griglia a partire da un punto di coordinate specificato e con l'orientamento indicato.
     * Verifica che la nave non esca dai bordi della griglia e che le caselle siano libere.
     *
     * @param ship la nave da posizionare
     * @param coordinates il punto di partenza per il posizionamento della nave
     * @param isHorizontal {@code true} se la nave deve essere posizionata in orizzontale, {@code false} per il verticale
     * @return {@code true} se la nave viene posizionata correttamente
     * @throws IllegalArgumentException se il posizionamento è invalido (fuori limiti o casella già occupata)
     */
    public boolean placeShip(Ship ship, Point coordinates, boolean isHorizontal) {
        List<GridSquare> shipSquares = new ArrayList<>();
        int x = coordinates.x;
        int y = coordinates.y;
        for (int i = 0; i < ship.getLenght(); i++) {
            int xCoord = isHorizontal ? x + i : x;
            int yCoord = isHorizontal ? y : y + i;

            // La nave non deve uscire dai bordi dell'area di gioco e la casella non deve essere già occupata
            if (xCoord >= size || yCoord >= size || gridSquares[xCoord][yCoord].getIsOccupied()) {
                throw new IllegalArgumentException("Posizionamento nave non valido: fuori dai limiti o casella già occupata.");
            }
            shipSquares.add(gridSquares[xCoord][yCoord]);
        }

        ship.getGridSquares().clear();
        ship.getGridSquares().addAll(shipSquares);

        for (GridSquare gridSquare : shipSquares) {
            gridSquare.setOccupied(true);
        }

        ships.add(ship);
        return true;
    }

    /**
     * Applica il danno a una casella della griglia in seguito a un tiro.
     * Se la casella non è occupata, viene marcata come colpita (colpo a vuoto).
     * Se è occupata, viene incrementato il livello di danno in base al danno del proiettile.
     *
     * @param coordinates le coordinate della casella da colpire
     * @param projectile il proiettile utilizzato per il tiro
     * @return {@code true} se una nave è stata colpita, {@code false} se il tiro è stato a vuoto
     * @throws IllegalArgumentException se le coordinate sono fuori dai limiti della griglia
     */
    public boolean applyDamage(Point coordinates, Projectile projectile) {
        int x = coordinates.x;
        int y = coordinates.y;
        
        if (x < 0 || y < 0 || x >= size || y >= size) {
            throw new IllegalArgumentException("Coordinate fuori dai limiti della griglia.");
        }

        GridSquare gridSquare = gridSquares[x][y];

        // Se non c'è una nave nella casella, la marca come colpita (colpo a vuoto)
        if (!gridSquare.getIsOccupied()) {
            gridSquare.setIsHit(true);
            return false;
        } else if (gridSquare.getDamageLevel() >= gridSquare.getMaxResistance()) {
            return false;
        } else {
            int newDamage = gridSquare.getDamageLevel() + projectile.getDamage();

            // Se il nuovo danno supera la resistenza massima, lo setta al massimo consentito
            if (newDamage > gridSquare.getMaxResistance()) {
                newDamage = gridSquare.getMaxResistance();
            }

            gridSquare.setDamageLevel(newDamage);
        }
        return true;
    }

    /**
     * Verifica se tutte le navi posizionate nella griglia sono affondate.
     *
     * @return {@code true} se tutte le navi sono affondate, {@code false} altrimenti
     */
    public boolean isEverythingSunk() {
        if (ships.isEmpty()) {
            return false; // Se non ci sono navi, il giocatore non può essere considerato sconfitto.
        }

        return ships.stream()
                    .allMatch(ship -> ship.isSunk());
    }

    /**
     * Restituisce la dimensione della griglia.
     *
     * @return la dimensione della griglia
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Restituisce la lista delle navi posizionate nella griglia.
     *
     * @return la lista delle navi
     */
    public List<Ship> getShips() {
        return ships;
    }
    
    /**
     * Restituisce l'array bidimensionale di caselle che compongono la griglia.
     * Utile per interfacce testuali e per visualizzare lo stato della griglia.
     *
     * @return l'array di {@link GridSquare} della griglia
     */
    public GridSquare[][] getGridSquares() {
        return gridSquares;
    }
}
