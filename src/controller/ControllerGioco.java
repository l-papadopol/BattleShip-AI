/*
 * ControllerGioco.java: Implementazione del controller del gioco che si interfaccia fra model e view
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller;

import model.Giocatore;
import model.Model;
import view.Messaggi;
import view.View;

public class ControllerGioco implements Controller {
	private Model gioco;
	private View vista;

	// Classi helper
	private GestorePosizionamento gestorePosizionamento;
	private GestoreProiettili gestoreProiettili;
	private GestoreTurnoPC gestoreTurnoPC;
	private GestoreTurnoGiocatore gestoreTurnoGiocatore;

	public ControllerGioco(Model model, View vista, int difficolta) {
		this.gioco = model;
		this.vista = vista;

		// Inizializziamo i gestori (classi helper)
		this.gestorePosizionamento = new GestorePosizionamento(vista);
		this.gestoreProiettili = new GestoreProiettili(vista);
		this.gestoreTurnoPC = new GestoreTurnoPC(gioco, vista, difficolta, gestoreProiettili);
		this.gestoreTurnoGiocatore = new GestoreTurnoGiocatore(gioco, vista, gestoreProiettili);
	}

	@Override
	public void gioca() {
		vista.mostraMessaggio(Messaggi.inizioPartita());
		posizionaNavi();

		while (!gioco.isGameOver()) {
			Giocatore giocatoreCorrente = gioco.getGiocatoreCorrente();
			vista.mostraMessaggio(Messaggi.turnoGiocatore(giocatoreCorrente.getNome()));

			// Se è il giocatore umano
			if (giocatoreCorrente.equals(gioco.getGiocatore1())) {
				gestoreTurnoGiocatore.gestisciTurnoGiocatore(giocatoreCorrente);
			} else {
				// Altrimenti è il PC
				gestoreTurnoPC.gestisciTurnoPC(giocatoreCorrente);
			}
		}

		vista.mostraMessaggio(Messaggi.partitaTerminata(gioco.getVincitore().getNome()));
		vista.chiudi();
	}

	@Override
	public void posizionaNavi() {
		Giocatore g1 = gioco.getGiocatore1();
		Giocatore g2 = gioco.getGiocatore2();

		int[] lunghezzaNavi = {5, 4, 4, 3, 3, 3, 2, 2, 1};

		// Posizionamento navi per il giocatore umano
		gestorePosizionamento.posizionaNaviGiocatore(g1, lunghezzaNavi);

		// Posizionamento navi per il giocatore PC
		gestorePosizionamento.posizionaNaviPC(g2, lunghezzaNavi);
	}
	
}

