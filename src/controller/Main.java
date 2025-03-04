/*
 * Main.java punto di ingresso al programma
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package controller;

import model.BattagliaNavale;
import model.Giocatore;
import model.Model;
import view.Gui;
import view.Tui;
import view.View;
import java.util.Scanner;

public class Main { 
    public static void main(String[] args) {

        // Creo due giocatori
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome del giocatore:");
        String nome = scanner.nextLine();
        Giocatore giocatore1 = new Giocatore(nome, 10);
        Giocatore giocatore2 = new Giocatore("PC", 10);
        
        // Richiedo anche il livello di difficoltà per il PC
        System.out.println("Scegli livello di difficoltà: 1 = Semplice, 2 = Medio, 3 = Difficile");
        int difficolta = scanner.nextInt();
        scanner.nextLine();
        
        // Inizializzo il modello della battaglia navale
        Model model = new BattagliaNavale(giocatore1, giocatore2);
        
        // Chiedo all'utente quale interfaccia utilizzare
        System.out.println("Scegli interfaccia: 1 = TUI, 2 = GUI");
        int scelta = scanner.nextInt();
        scanner.nextLine();
        
        // Lancio la TUI o la GUI a seconda di cosa l'utente sceglie
        View view;
        if (scelta == 1) {
            view = new Tui();
        } else {
        	view = new Gui();
        }
        
        
        // Avvio il controller
        Controller controller = new ControllerGioco(model, view, difficolta);
        controller.gioca();
        
        scanner.close();
    }
}
