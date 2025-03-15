/*
 * Giocatore.java modello il giocatore e gli assegno gli oggetti che ha
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class Player {
    private String name;
    private int specialProjectileQty = 3;
    private int powerfullProjectileQty = 5;
    private Grid personalGrid;
    private Grid attackGrid;

    public Player(String name, int gridDimension) {
        this.name = name;
        this.personalGrid = new Grid(gridDimension);
        this.attackGrid = new Grid(gridDimension);
    }

    /*
     *  Restituisce il nome del giocatore
     */
    public String getName() {
        return name;
    }

    /*
     *  Restituisce la grisglia sulla quale il giocatore ha posizionato le sue navi
     */
    public Grid getPersonalGrid() {
        return personalGrid;
    }

    /*
     *  Restituisce la griglia dove il giocatore spara e memorizza i colpi andati a vuoto oppure a segno
     */
    public Grid getAttackGrid() {
        return attackGrid;
    }

    /*
     *  Posiziona una nave sulla propria griglia
     */
    public boolean placeShip(Ship ship, Point coordinate, boolean isHorizontal) {
        try {
            return personalGrid.placeShip(ship, coordinate, isHorizontal);
        } catch (IllegalArgumentException e) {
        	if(name == "PC") {
        		// Non deve stampare nulla sennò escono un sacco di errori quando il PC posiziona le navi a caso
        	} else {
            System.out.println("Errore nel posizionamento della nave: " + e.getMessage());
        	}
            return false;
        }
    }

    /*
     *  Spara sulla griglia dell'avversario
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
            // Se il tiro è a vuoto imposta solo il flag "colpita"
            this.attackGrid.applyDamage(coordinate, projectile);
        }
        return isHit;
    }


    /*
     *  Verifica se il giocatore ha perso (tutte le navi affondate)
     */
    public boolean heLost() {
        return personalGrid.isEverythinkSunk();
    }

    /*
     *  Restituisce una stringa che contiene il numero di proiettili potenti
     */
    public int getPowerfullProjectileQty(){
    	return this.powerfullProjectileQty;
    }
    
    /*
     *  Restituisce una stringa che contiene il numero di proiettili speciali
     */
    public int getSpecialProjectileQty(){
    	return this.specialProjectileQty;
    }
    
    /*
     *  Imposta la quantità di proiettili potenti disponibili
     */
    public void setPowerfullProjectileQty(int proiettiliPotenti) {
    	this.powerfullProjectileQty = proiettiliPotenti;
    }
    
    /*
     *  Imposta la quantità di proiettili speciali disponibili
     */
    public void setSpecialProjectileQty(int proiettiliSpeciali) {
    	this.specialProjectileQty = proiettiliSpeciali;
    }
    
    /*
     *  Decrementa di un unità la quantità di proiettili potenti disponibile
     */
    public void decPowerfullProjectileQty() {
    	this.powerfullProjectileQty--;
    }
    
    /*
     *  Decrementa di un unità la quantità di proiettili speciali disponibile
     */
    public void decSpecialProjectileQty() {
    	this.specialProjectileQty--;
    }
    
    /*
     *  Incrementa di un unità la quantità di proiettili speciali disponibile
     */
    public void addSpecialProjectileQty(int qty) {
        this.specialProjectileQty += qty;
    }

    /*
     *  Incrementa di un unità la quantità di proiettili potenti disponibile
     */
    public void addPowerfulProjectileQty(int qty) {
        this.powerfullProjectileQty += qty;
    }

}
