/*
 * Classe per la centralizzazione dei messaggi da mostrare all'utente.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

public class Messages {

    public static String gameStart() {
        return "Inizio partita di Battaglia Navale";
    }

    public static String humanTurn(String name) {
        return "Turno di " + name;
    }

    public static String personalGrid() {
        return "\n GRIGLIA PERSONALE";
    }

    public static String attackGrid() {
        return "\n GRIGLIA DI ATTACCO";
    }

    public static String availableProjectiles(int powerfull, int special) {
        return "Proiettili disponibili (Potenti Speciali): (" + powerfull + " " + special + ")";
    }

    public static String askCoordinates() {
        return "Inserisci coordinate (x y): ";
    }

    public static String chooseProjectile() {
        return "Scegli tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale): ";
    }

    public static String projectileTypeErrorMsg() {
        return "Devi inserire 1, 2 o 3.";
    }

    public static String invalidNumberMsg() {
        return "Inserisci un numero valido (1, 2 o 3).";
    }

    public static String powerfulProjectileUnavailable() {
        return "Non hai proiettili potenti disponibili, utilizzo proiettile normale.";
    }

    public static String specialProjectileUnavailable() {
        return "Non hai proiettili speciali disponibili, utilizzo proiettile normale.";
    }

    public static String hitMsg() {
        return "Colpito!";
    }

    public static String missMsg() {
        return "Mancato!";
    }

    public static String pcShootMsg(int x, int y) {
        return "Il PC spara a (" + x + "," + y + ")";
    }

    public static String pcHasHitMsg() {
        return "Il PC ha colpito!";
    }

    public static String pcHasMissMsg() {
        return "Il PC ha mancato!";
    }

    public static String gameOver(String winner) {
        return "Il gioco è terminato! Il vincitore è " + winner;
    }
    
    public static String fleetPlacing(String name) {
        return "Posizionamento della flotta di " + name;
    }
    
    public static String placeShip(int lenght) {
        return "Posiziona la nave di lunghezza " + lenght;
    }
    
    public static String shipSuccessfullyPlaced() {
        return "Nave posizionata con successo.";
    }
    
    public static String invalidPositioning(String error) {
        return "Posizionamento non valido: " + error;
    }
    
    public static String pcIsPositioningFleet() {
        return "Il PC sta posizionando le proprie navi...";
    }
    
    public static String askStartCoordinates() {
        return "Inserisci coordinate di partenza (x y): ";
    }
    
    public static String invalidFormat() {
    	return "Formato non valido. Inserisci due numeri separati da uno spazio.";
    }
    
    public static String outOfRange(int dim) {
    	return "Le coordinate devono essere comprese tra 0 e " + (dim - 1);
    }
  
    public static String invalidNumber() {
    	return "Inserisci numeri validi.";
    }

	public static String orientationError() {
		// TODO Auto-generated method stub
		return "Errore: inserisci H oppure V!";
	}

	public static String askOrientation() {
		return "H orientamento orizzontale | V orientamento verticale.";
	}
    
	public static String bonusReceived(String bonusType, int qty) {
		return "Hai ricevuto in bonus " + qty + " " + bonusType;
	}
	
	public static String askPlayerName() {
		return "Inserisci il nome del giocatore:";
	}

	public static String askDifficultyLevel() {
		return "Scegli livello di difficoltà: 1 = Semplice, 2 = Medio, 3 = Difficile";
	}

	public static String askView() {
		return "Scegli interfaccia: 1 = TUI, 2 = GUI";
	}


}
