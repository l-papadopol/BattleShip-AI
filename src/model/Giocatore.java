/*
 * Giocatore.java modello il giocatore e gli assegno gli oggetti che ha
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class Giocatore {
    private String nome;
    private int proiettiliSpeciali = 3;
    private int proiettiliPotenti = 5;
    private Griglia grigliaPersonale;
    private Griglia grigliaAttacco;

    public Giocatore(String nome, int dimensioneGriglia) {
        this.nome = nome;
        this.grigliaPersonale = new Griglia(dimensioneGriglia);
        this.grigliaAttacco = new Griglia(dimensioneGriglia);
    }

    // Restituisce il nome del giocatore
    public String getNome() {
        return nome;
    }

    // Restituisce la grisglia sulla quale il giocatore ha posizionato le sue navi
    public Griglia getGrigliaPersonale() {
        return grigliaPersonale;
    }

    // Restituisce la griglia dove il giocatore spara e memorizza i colpi andati a vuoto oppure a segno
    public Griglia getGrigliaAttacco() {
        return grigliaAttacco;
    }

    // Posiziona una nave sulla propria griglia
    public boolean posizionaNave(Nave nave, Point coordinata, boolean orizzontale) {
        try {
            return grigliaPersonale.posizionaNave(nave, coordinata, orizzontale);
        } catch (IllegalArgumentException e) {
            System.out.println("Errore nel posizionamento della nave: " + e.getMessage());
            return false;
        }
    }

    // Spara sulla griglia dell'avversario
    public boolean spara(Point coordinata, Proiettile proiettile, Giocatore avversario) {
        // Applica il danno sulla griglia personale dell'avversario
        boolean colpito = avversario.getGrigliaPersonale().applicaDanno(coordinata, proiettile);
        
        if (colpito) {
            // Se il tiro ha colpito, copia il livello di danno dalla griglia personale dell'avversario
            int danno = avversario.getGrigliaPersonale().getCaselle()[coordinata.x][coordinata.y].getLivelloDanno();
            Casella cellaAttacco = this.grigliaAttacco.getCaselle()[coordinata.x][coordinata.y];
            cellaAttacco.setLivelloDanno(danno);
            cellaAttacco.setColpita(true);
        } else {
            // Se il tiro è a vuoto imposta solo il flag "colpita"
            this.grigliaAttacco.applicaDanno(coordinata, proiettile);
        }
        return colpito;
    }


    // Verifica se il giocatore ha perso (tutte le navi affondate)
    public boolean haPerso() {
        return grigliaPersonale.tutteNaviAffondate();
    }

    // Restituisce una stringa che contiene il numero di proiettili potenti
    public int getProiettiliPotenti(){
    	return this.proiettiliPotenti;
    }
    
    // Restituisce una stringa che contiene il numero di proiettili speciali
    public int getProiettiliSpeciali(){
    	return this.proiettiliSpeciali;
    }
    
    // Imposta la quantità di proiettili potenti disponibili
    public void setProiettiliPotenti(int proiettiliPotenti) {
    	this.proiettiliPotenti = proiettiliPotenti;
    }
    
    // Imposta la quantità di proiettili speciali disponibili
    public void setProiettiliSpeciali(int proiettiliSpeciali) {
    	this.proiettiliSpeciali = proiettiliSpeciali;
    }
    
    // Decremente di un unità la quantità di proiettili potenti disponibile
    public void decProiettiliPotenti() {
    	this.proiettiliPotenti--;
    }
    
    // Decremente di un unità la quantità di proiettili speciali disponibile
    public void decProiettiliSpeciali() {
    	this.proiettiliSpeciali--;
    }
}
