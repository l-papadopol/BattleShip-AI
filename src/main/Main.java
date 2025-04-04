package main;

import model.Battle;
import model.ModelInterface;
import model.entities.Player;
import view.Gui;
import view.Tui;
import view.ViewInterface;
import view.components.Messages;

import java.util.Scanner;

import controller.ControllerInterface;
import controller.GameController;

/**
 * Main.java - Punto di ingresso al programma.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe contiene il metodo main che inizializza la battaglia navale.
 * Crea i giocatori, chiede le impostazioni all'utente (nome, livello di difficoltà e interfaccia),
 * e avvia il controller per eseguire la partita.
 */
public class Main { 
    /**
     * Metodo principale, punto di ingresso del programma.
     *
     * @param args argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {

        // Creo due giocatori
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.askPlayerName());
        String name = scanner.nextLine();
        Player player1 = new Player(name, 10);
        Player player2 = new Player("PC", 10);
        
        // Richiedo anche il livello di difficoltà per il PC
        System.out.println(Messages.askDifficultyLevel());
        int difficultyLevel = scanner.nextInt();
        scanner.nextLine();
        
        // Inizializzo il modello della battaglia navale
        ModelInterface model = new Battle(player1, player2);
        
        // Chiedo all'utente quale interfaccia utilizzare
        System.out.println(Messages.askView());
        int choose = scanner.nextInt();
        scanner.nextLine();
        
        // Lancio la TUI o la GUI a seconda di cosa sceglie l'utente
        ViewInterface view;
        if (choose == 1) {
            view = new Tui();
        } else {
            view = new Gui(name);
        }
        
        // Avvio il controller e la partita
        ControllerInterface controller = new GameController(model, view, difficultyLevel);
        controller.startBattle();
        
        scanner.close();
    }
}
