package view;

import java.util.Scanner;

public class TextView {
    private Scanner scanner;
    
    public TextView() {
        scanner = new Scanner(System.in);
    }
    
    // Mostra un messaggio all'utente
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    // Richiede un input testuale all'utente
    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
    
    // Chiude lo scanner
    public void close() {
        scanner.close();
    }
}
