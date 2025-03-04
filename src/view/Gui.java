/**
 * Gui.java: Implementazione della view grafica per Battaglia Navale.
 * La classe utilizza due pannelli per le griglie:
 *  - personalGridPanel: visualizza la griglia personale (a sinistra)
 *  - attackGridPanel: visualizza la griglia d'attacco (a destra)
 * I flag "flagGrigliaPersonale" e "flagGrigliaAttacco" vengono impostati in base ai messaggi inviati dal controller.
 * 
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import model.Griglia;

public class Gui implements View {
    private JFrame frame;
    private GridPanel grigliaPersonale;
    private GridPanel grigliaAttacco;
    private JTextArea outputArea;
    private JTextField inputArea;
    
    // Flag per capire quale griglia aggiornare in stampa()
    private boolean flagGrigliaPersonale = false;
    private boolean flagGrigliaAttacco = false;

    public Gui() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("Battaglia Navale");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout(10, 10));

                // Pannello superiore: contiene le due griglie
                JPanel pannelloSuperiore = new JPanel(new GridLayout(1, 2, 10, 10));
                pannelloSuperiore.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Contenitore per la griglia personale
                JPanel contenitoreGrigliaPersonale = new JPanel(new BorderLayout());
                contenitoreGrigliaPersonale.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                JLabel etichettaGrigliaPersonale = new JLabel("Giocatore", SwingConstants.CENTER);
                contenitoreGrigliaPersonale.add(etichettaGrigliaPersonale, BorderLayout.NORTH);
                grigliaPersonale = new GridPanel();
                contenitoreGrigliaPersonale.add(grigliaPersonale, BorderLayout.CENTER);

                // Contenitore per la griglia dei colpi sparati
                JPanel contenitoreGrigliaAttacco = new JPanel(new BorderLayout());
                contenitoreGrigliaAttacco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                JLabel etichettaGrigliaAttacco = new JLabel("Colpi sparati", SwingConstants.CENTER);
                contenitoreGrigliaAttacco.add(etichettaGrigliaAttacco, BorderLayout.NORTH);
                grigliaAttacco = new GridPanel();
                contenitoreGrigliaAttacco.add(grigliaAttacco, BorderLayout.CENTER);

                pannelloSuperiore.add(contenitoreGrigliaPersonale);
                pannelloSuperiore.add(contenitoreGrigliaAttacco);
                frame.add(pannelloSuperiore, BorderLayout.NORTH);

                // Area centrale: output dei messaggi
                outputArea = new JTextArea(10, 40);
                outputArea.setEditable(false);
                outputArea.setBackground(Color.BLACK);
                outputArea.setForeground(Color.GREEN);
                outputArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                JScrollPane pannelloMessaggiOutput = new JScrollPane(outputArea);
                pannelloMessaggiOutput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                frame.add(pannelloMessaggiOutput, BorderLayout.CENTER);

                // Campo di input: aggiunge un bordo visibile e padding interno
                inputArea = new JTextField();
                inputArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.BLUE, 2),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                inputArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                frame.add(inputArea, BorderLayout.SOUTH);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                // Imposta il focus fisso sulla casella di input testuale così non devo cliccare ogni volta per scrivere
                inputArea.requestFocusInWindow();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void mostraMessaggio(String message) {
        SwingUtilities.invokeLater(() -> {
            // Imposta il flag in base al messaggio inviato
            String ritaglioDiTesto = message.trim();
            if (ritaglioDiTesto.equals("GRIGLIA PERSONALE")) {
                flagGrigliaPersonale = true;
            } else if (ritaglioDiTesto.equals("GRIGLIA DI ATTACCO")) {
                flagGrigliaAttacco = true;
            }
            outputArea.append(message + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    @Override
    public String prompt(String message) {
        // Mostra il messaggio per indicare all'utente cosa inserire
        mostraMessaggio(message);
        
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] userInput = new String[1];
        
        // ActionListener che cattura l'invio dell'input
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput[0] = inputArea.getText();
                inputArea.setText("");
                latch.countDown();
            }
        };
        
        // Associo il listener al JTextField
        inputArea.addActionListener(listener);
        
        try {
            // Aspettiamo che l'utente prema invio
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        // Rimuoviamo il listener per evitare bug strani
        inputArea.removeActionListener(listener);
        
        return userInput[0];
    }

    @Override
    public void stampa(Griglia griglia) {
        SwingUtilities.invokeLater(() -> {
            // Aggiorna il pannello appropriato in base al flag impostato
            if (flagGrigliaPersonale) {
                grigliaPersonale.setGriglia(griglia);
                flagGrigliaPersonale = false;
            } else if (flagGrigliaAttacco) {
                grigliaAttacco.setGriglia(griglia);
                flagGrigliaAttacco = false;
            } else {
                // Se non è stato impostato alcun flag, aggiorna quello personale
                grigliaPersonale.setGriglia(griglia);
            }
        });
    }

    // Metodo che gestisce l'input delle coordinate (x,y) incluso la validazione
    @Override
    public Point leggiCoordinate(String message, int dim) {
        while (true) {
            String input = prompt(message);
            
            // In caso di annullamento, restituisce una coordinata di default
            if (input == null) {
                return new Point(0, 0);
            }
            
            String[] spezzattinoDiStringa = input.trim().split("\\s+");
            if (spezzattinoDiStringa.length != 2) {
                mostraMessaggio("Formato non valido. Inserisci due numeri separati da uno spazio.");
                continue;
            }
            try {
                int x = Integer.parseInt(spezzattinoDiStringa[0]);
                int y = Integer.parseInt(spezzattinoDiStringa[1]);
                if (x < 0 || x >= dim || y < 0 || y >= dim) {
                    mostraMessaggio("Le coordinate devono essere comprese tra 0 e " + (dim - 1));
                    continue;
                }
                return new Point(x, y);
            } catch (NumberFormatException e) {
                mostraMessaggio("Inserisci numeri validi.");
            }
        }
    }

    // Metodo che gestisce l'input dell'orientamento della nave, incluso la validazione
    @Override
    public boolean leggiOrientamento(String message) {
        while (true) {
            String input = prompt(message);
            if (input == null) {
                return true;
            }
            
            input = input.trim();
            if (input.equalsIgnoreCase("H")) {
                return true;
            } else if (input.equalsIgnoreCase("V")) {
                return false;
            } else {
                mostraMessaggio("Inserisci un orientamento valido: H per orizzontale o V per verticale.");
            }
        }
    }

    // Chiudi, incarta e porta a casa
    @Override
    public void chiudi() {
        SwingUtilities.invokeLater(() -> frame.dispose());
    }
}
