/*
 * Main.java punto di ingresso al programma
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
*/
package controller;

import model.Battle;
import model.Player;
import model.ModelInterface;
import view.Gui;
import view.Tui;
import view.ViewInterface;
import java.util.Scanner;

public class Main { 
    public static void main(String[] args) {

        // Creo due giocatori
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome del giocatore:");
        String name = scanner.nextLine();
        Player player1 = new Player(name, 10);
        Player player2 = new Player("PC", 10);
        
        // Richiedo anche il livello di difficoltà per il PC
        System.out.println("Scegli livello di difficoltà: 1 = Semplice, 2 = Medio, 3 = Difficile");
        int difficultyLevel = scanner.nextInt();
        scanner.nextLine();
        
        // Inizializzo il modello della battaglia navale
        ModelInterface model = new Battle(player1, player2);
        
        // Chiedo all'utente quale interfaccia utilizzare
        System.out.println("Scegli interfaccia: 1 = TUI, 2 = GUI");
        int choose = scanner.nextInt();
        scanner.nextLine();
        
        // Lancio la TUI o la GUI a seconda di cosa l'utente sceglie
        ViewInterface view;
        if (choose == 1) {
            view = new Tui();
        } else {
        	view = new Gui();
        }
        
        
        // Avvio il controller
        ControllerInterface controller = new GameController(model, view, difficultyLevel);
        controller.startBattle();
        
        scanner.close();
    }
}
