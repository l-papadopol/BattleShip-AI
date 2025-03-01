/*
 * Main.java punto di ingresso al programma
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package controller;

import model.BattagliaNavale;
import model.Giocatore;
import model.Model;
import view.Tui;
import view.View;
import java.util.Scanner;

public class Main { 
    public static void main(String[] args) {

        // Creo due giocatori
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome del giocatore:");
        String nome = scanner.nextLine();
        Giocatore player1 = new Giocatore(nome, 10);
        Giocatore player2 = new Giocatore("PC", 10);
        
        // Inizializzo il modello della battaglia navale
        Model model = new BattagliaNavale(player1, player2);
        
        // Chiedo all'utente quale interfaccia utilizzare
        System.out.println("Scegli interfaccia: 1 = TUI, 2 = GUI");
        int scelta = scanner.nextInt();
        scanner.nextLine();
        
        View view;
        if (scelta == 1) {
            view = new Tui();
        } else {
            // STUP, lancia la view->GUI
        	view = new Tui();
        }
        
        // Avvio il controller
        Controller controller = new ControllerGioco(model, view);
        controller.gioca();
        
        scanner.close();
    }
}
