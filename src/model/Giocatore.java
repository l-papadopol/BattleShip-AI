/*
 * Giocatore.java modello il giocatore
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package model;

import java.awt.Point;

public class Giocatore {
    private String nome;
    private Griglia grigliaPersonale;
    private Griglia grigliaAttacco;

    public Giocatore(String nome, int dimensioneGriglia) {
        this.nome = nome;
        this.grigliaPersonale = new Griglia(dimensioneGriglia);
        this.grigliaAttacco = new Griglia(dimensioneGriglia);
    }

    public String getNome() {
        return nome;
    }

    public Griglia getGrigliaPersonale() {
        return grigliaPersonale;
    }

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
    	int x = coordinata.x;
    	int y = coordinata.y;
    	
        boolean colpito = avversario.getGrigliaPersonale().applicaDanno(coordinata, proiettile);

        if (colpito) {
            System.out.println(nome + " ha colpito una nave in (" + x + "," + y + ")!");
        } else {
            System.out.println(nome + " ha mancato il bersaglio in (" + x + "," + y + ").");
        }

        return colpito;
    }

    // Verifica se il giocatore ha perso (tutte le navi affondate)
    public boolean haPerso() {
        return grigliaPersonale.tutteNaviAffondate();
    }

}
