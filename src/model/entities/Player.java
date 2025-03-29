package model.entities;

import java.awt.Point;

/**
 * Giocatore.java modella il giocatore e gli assegna gli oggetti che ha.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe rappresenta un giocatore nel gioco Battaglia Navale.
 * Contiene il nome del giocatore, le quantità di proiettili speciali e potenti e le griglie personale e d'attacco.
 */
public class Player {
    private String name;
    private int specialProjectileQty = 3;
    private int powerfullProjectileQty = 5;
    private Grid personalGrid;
    private Grid attackGrid;

    /**
     * Costruisce un nuovo giocatore con il nome specificato e una griglia di dimensione data.
     *
     * @param name il nome del giocatore
     * @param gridDimension la dimensione delle griglie di gioco
     */
    public Player(String name, int gridDimension) {
        this.name = name;
        this.personalGrid = new Grid(gridDimension);
        this.attackGrid = new Grid(gridDimension);
    }

    /**
     * Restituisce il nome del giocatore.
     *
     * @return il nome del giocatore
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la griglia personale del giocatore, su cui sono posizionate le navi.
     *
     * @return la griglia personale
     */
    public Grid getPersonalGrid() {
        return personalGrid;
    }

    /**
     * Restituisce la griglia di attacco del giocatore, utilizzata per memorizzare i tiri.
     *
     * @return la griglia di attacco
     */
    public Grid getAttackGrid() {
        return attackGrid;
    }

    /**
     * Posiziona una nave sulla griglia personale del giocatore.
     *
     * @param ship la nave da posizionare
     * @param coordinate il punto di partenza per il posizionamento
     * @param isHorizontal {@code true} se la nave deve essere posizionata in orizzontale, {@code false} per il verticale
     * @return {@code true} se la nave viene posizionata correttamente, {@code false} altrimenti
     */
    public boolean placeShip(Ship ship, Point coordinate, boolean isHorizontal) {
        try {
            return personalGrid.placeShip(ship, coordinate, isHorizontal);
        } catch (IllegalArgumentException e) {
            if (name.equals("PC")) {
                // Non deve stampare nulla se il PC posiziona le navi a caso
            } else {
                System.out.println("Errore nel posizionamento della nave: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Esegue un tiro sulla griglia dell'avversario.
     * Applica il danno sulla griglia personale dell'avversario e aggiorna la griglia d'attacco del giocatore.
     *
     * @param coordinate le coordinate del tiro
     * @param projectile il proiettile utilizzato per il tiro
     * @param enemy il giocatore avversario
     * @return {@code true} se il tiro ha colpito una nave, {@code false} altrimenti
     */
    public boolean shoot(Point coordinate, Projectile projectile, Player enemy) {
        // Applica il danno sulla griglia personale dell'avversario
        boolean isHit = enemy.getPersonalGrid().applyDamage(coordinate, projectile);
        
        if (isHit) {
            // Se il tiro ha colpito, copia il livello di danno dalla griglia personale dell'avversario
            int damage = enemy.getPersonalGrid().getGridSquares()[coordinate.x][coordinate.y].getDamageLevel();
            GridSquare attackGridSquare = this.attackGrid.getGridSquares()[coordinate.x][coordinate.y];
            attackGridSquare.setDamageLevel(damage);
            attackGridSquare.setIsHit(true);
        } else {
            // Se il tiro è a vuoto, imposta solo il flag "colpita"
            this.attackGrid.applyDamage(coordinate, projectile);
        }
        return isHit;
    }

    /**
     * Verifica se il giocatore ha perso, ovvero se tutte le navi sono affondate.
     *
     * @return {@code true} se tutte le navi sono affondate, {@code false} altrimenti
     */
    public boolean heLost() {
        return personalGrid.isEverythingSunk();
    }

    /**
     * Restituisce la quantità di proiettili potenti disponibili.
     *
     * @return il numero di proiettili potenti
     */
    public int getPowerfullProjectileQty() {
        return this.powerfullProjectileQty;
    }
    
    /**
     * Restituisce la quantità di proiettili speciali disponibili.
     *
     * @return il numero di proiettili speciali
     */
    public int getSpecialProjectileQty() {
        return this.specialProjectileQty;
    }
    
    /**
     * Imposta la quantità di proiettili potenti disponibili.
     *
     * @param proiettiliPotenti il nuovo numero di proiettili potenti
     */
    public void setPowerfullProjectileQty(int proiettiliPotenti) {
        this.powerfullProjectileQty = proiettiliPotenti;
    }
    
    /**
     * Imposta la quantità di proiettili speciali disponibili.
     *
     * @param proiettiliSpeciali il nuovo numero di proiettili speciali
     */
    public void setSpecialProjectileQty(int proiettiliSpeciali) {
        this.specialProjectileQty = proiettiliSpeciali;
    }
    
    /**
     * Decrementa di una unità la quantità di proiettili potenti disponibili.
     */
    public void decPowerfullProjectileQty() {
        this.powerfullProjectileQty--;
    }
    
    /**
     * Decrementa di una unità la quantità di proiettili speciali disponibili.
     */
    public void decSpecialProjectileQty() {
        this.specialProjectileQty--;
    }
    
    /**
     * Incrementa la quantità di proiettili speciali disponibili di una data quantità.
     *
     * @param qty la quantità da aggiungere
     */
    public void addSpecialProjectileQty(int qty) {
        this.specialProjectileQty += qty;
    }

    /**
     * Incrementa la quantità di proiettili potenti disponibili di una data quantità.
     *
     * @param qty la quantità da aggiungere
     */
    public void addPowerfulProjectileQty(int qty) {
        this.powerfullProjectileQty += qty;
    }
}

