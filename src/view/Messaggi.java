/*
 * Messaggi.java
 * Classe per la centralizzazione dei messaggi da mostrare all'utente.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

public class Messaggi {

    public static String inizioPartita() {
        return "Inizio partita di Battaglia Navale";
    }

    public static String turnoGiocatore(String nome) {
        return "Turno di " + nome;
    }

    public static String grigliaPersonale() {
        return "\n GRIGLIA PERSONALE";
    }

    public static String grigliaAttacco() {
        return "\n GRIGLIA DI ATTACCO";
    }

    public static String proiettiliDisponibili(int potenti, int speciali) {
        return "Proiettili disponibili (Potenti Speciali): (" + potenti + " " + speciali + ")";
    }

    public static String chiediCoordinate() {
        return "Inserisci coordinate (x y): ";
    }

    public static String scegliTipoProiettile() {
        return "Scegli tipo di proiettile (1 = Normale, 2 = Potente, 3 = Speciale): ";
    }

    public static String messaggioErroreTipo() {
        return "Devi inserire 1, 2 o 3.";
    }

    public static String messaggioErroreNumero() {
        return "Inserisci un numero valido (1, 2 o 3).";
    }

    public static String messaggioProiettilePotenteNonDisponibile() {
        return "Non hai proiettili potenti disponibili, utilizzo proiettile normale.";
    }

    public static String messaggioProiettileSpecialeNonDisponibile() {
        return "Non hai proiettili speciali disponibili, utilizzo proiettile normale.";
    }

    public static String messaggioColpito() {
        return "Colpito!";
    }

    public static String messaggioMancato() {
        return "Mancato!";
    }

    public static String messaggioPCSpara(int x, int y, String tipo) {
        return "Il PC spara a (" + x + "," + y + ") con un proiettile " + tipo;
    }

    public static String messaggioPCColpito() {
        return "Il PC ha colpito!";
    }

    public static String messaggioPCMancato() {
        return "Il PC ha mancato!";
    }

    public static String partitaTerminata(String vincitore) {
        return "Il gioco è terminato! Il vincitore è " + vincitore;
    }
    
    public static String posizionamentoNavi(String nome) {
        return "Posizionamento navi per " + nome;
    }
    
    public static String posizionaNave(int lunghezza) {
        return "Posiziona la nave di lunghezza " + lunghezza;
    }
    
    public static String navePosizionata() {
        return "Nave posizionata con successo.";
    }
    
    public static String posizionamentoNonValido(String errore) {
        return "Posizionamento non valido: " + errore;
    }
    
    public static String pcPosizionamentoNavi() {
        return "Il PC sta posizionando le proprie navi...";
    }
}
