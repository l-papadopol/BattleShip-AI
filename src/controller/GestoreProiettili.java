package controller;

import model.Giocatore;
import model.Proiettile;
import model.ProiettileNormale;
import model.ProiettilePotente;
import model.ProiettileSpeciale;
import view.Messaggi;
import view.View;

public class GestoreProiettili {
    private View vista;

    public GestoreProiettili(View vista) {
        this.vista = vista;
    }

    /**
     * Sceglie il proiettile da usare in base al tipo (1=Normale, 2=Potente, 3=Speciale).
     * Decrementa le scorte del giocatore se disponibili e mostra messaggi di errore se non disponibili.
     */
    public Proiettile creaProiettile(int tipo, Giocatore giocatore) {
        switch (tipo) {
            case 2:
                if (giocatore.getProiettiliPotenti() > 0) {
                    giocatore.decProiettiliPotenti();
                    return new ProiettilePotente();
                } else {
                    vista.mostraMessaggio(Messaggi.messaggioProiettilePotenteNonDisponibile());
                    return new ProiettileNormale();
                }
            case 3:
                if (giocatore.getProiettiliSpeciali() > 0) {
                    giocatore.decProiettiliSpeciali();
                    return new ProiettileSpeciale();
                } else {
                    vista.mostraMessaggio(Messaggi.messaggioProiettileSpecialeNonDisponibile());
                    return new ProiettileNormale();
                }
            case 1:
            default:
                // Se tipo non valido o == 1, usa proiettile normale
                return new ProiettileNormale();
        }
    }
}

