package view;

import javax.swing.*;

import model.entities.Grid;
import view.components.GridPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

/**
 * Implementazione della view grafica per Battaglia Navale.
 * <p>
 * La classe utilizza due pannelli per le griglie:
 * <ul>
 *   <li><strong>personalGridPanel</strong>: visualizza la griglia personale (a sinistra)</li>
 *   <li><strong>attackGridPanel</strong>: visualizza la griglia d'attacco (a destra)</li>
 * </ul>
 * I flag "personalGridFlag" e "attackGridFlag" vengono impostati in base ai messaggi inviati dal controller,
 * determinando quale griglia aggiornare nella chiamata al metodo {@code gridDrawing()}.
 * </p>
 * 
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
public class Gui implements ViewInterface {
    private JFrame frame;
    private GridPanel personalGrid;
    private GridPanel attackGrid;
    private JTextArea outputArea;
    private JTextField inputArea;
    
    // Flag per capire quale griglia aggiornare in gridDrawing()
    private boolean personalGridFlag = false;
    private boolean attackGridFlag = false;
    
    /**
     * Costruisce e mostra la GUI per il gioco Battaglia Navale.
     * Viene inizializzata la finestra principale, i pannelli per le griglie, l'area di output dei messaggi e il campo di input per l'utente.
     *
     * @param name il nome del giocatore, usato per etichettare la griglia personale
     */
    public Gui(String name) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Configurazione della finestra principale
                frame = new JFrame("Battaglia Navale");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout(10, 10));

                // Pannello superiore che contiene le due griglie
                JPanel upperPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Contenitore per la griglia personale
                JPanel personalGridContainer = new JPanel(new BorderLayout());
                personalGridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                // Contenitore per la griglia d'attacco
                JPanel attackGridContainer = new JPanel(new BorderLayout());
                attackGridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Etichetta e griglia personale
                JLabel personalGridCaption = new JLabel(name, SwingConstants.CENTER);
                personalGridContainer.add(personalGridCaption, BorderLayout.NORTH);
                personalGrid = new GridPanel();
                personalGridContainer.add(personalGrid, BorderLayout.CENTER);

                // Etichetta e griglia d'attacco
                JLabel attackGridCaption = new JLabel("Colpi sparati", SwingConstants.CENTER);
                attackGridContainer.add(attackGridCaption, BorderLayout.NORTH);
                attackGrid = new GridPanel();
                attackGridContainer.add(attackGrid, BorderLayout.CENTER);

                upperPanel.add(personalGridContainer);
                upperPanel.add(attackGridContainer);
                frame.add(upperPanel, BorderLayout.NORTH);

                // Area centrale per l'output dei messaggi
                outputArea = new JTextArea(10, 40);
                outputArea.setEditable(false);
                outputArea.setBackground(Color.BLACK);
                outputArea.setForeground(Color.GREEN);
                outputArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                JScrollPane outputMsgPanel = new JScrollPane(outputArea);
                outputMsgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                frame.add(outputMsgPanel, BorderLayout.CENTER);

                // Campo di input per l'utente, posizionato in basso
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
                
                // Imposta il focus sulla casella di input per facilitare l'inserimento
                inputArea.requestFocusInWindow();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Visualizza un messaggio nell'area di output.
     * Se il messaggio corrisponde a "GRIGLIA PERSONALE" o "GRIGLIA DI ATTACCO", imposta il flag corrispondente,
     * in modo da aggiornare la griglia appropriata nella successiva chiamata a {@code gridDrawing()}.
     *
     * @param message il messaggio da mostrare
     */
    @Override
    public void showMsg(String message) {
        SwingUtilities.invokeLater(() -> {
            // Imposta il flag in base al contenuto del messaggio
            String stringTrim = message.trim();
            if (stringTrim.equals("GRIGLIA PERSONALE")) {
                personalGridFlag = true;
            } else if (stringTrim.equals("GRIGLIA DI ATTACCO")) {
                attackGridFlag = true;
            }
            
            outputArea.append(message + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        });
    }

    /**
     * Mostra un prompt all'utente e attende il suo input tramite il campo di testo.
     * Il metodo utilizza un {@link CountDownLatch} per sospendere l'esecuzione fino all'invio dell'input.
     *
     * @param message il messaggio di prompt da mostrare all'utente
     * @return la stringa inserita dall'utente
     */
    @Override
    public String prompt(String message) {
        // Mostra il messaggio per indicare cosa inserire
        showMsg(message);
        
        final CountDownLatch latch = new CountDownLatch(1);
        final String[] userInput = new String[1];
        
        // ActionListener per catturare l'evento di invio dell'input
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput[0] = inputArea.getText();
                inputArea.setText("");
                latch.countDown();
            }
        };
        
        // Associa il listener al campo di input
        inputArea.addActionListener(listener);
        
        try {
            // Attende che l'utente prema invio
            latch.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        // Rimuove il listener per evitare problemi in seguito
        inputArea.removeActionListener(listener);
        
        return userInput[0];
    }

    /**
     * Aggiorna la visualizzazione della griglia in base al flag impostato.
     * Se il flag {@code personalGridFlag} è attivo, aggiorna la griglia personale;
     * se il flag {@code attackGridFlag} è attivo, aggiorna la griglia d'attacco.
     * Se nessun flag è attivo, aggiorna di default la griglia personale.
     *
     * @param grid la griglia da disegnare
     */
    @Override
    public void gridDrawing(Grid grid) {
        SwingUtilities.invokeLater(() -> {
            // Aggiorna il pannello appropriato in base al flag impostato
            if (personalGridFlag) {
                personalGrid.setGrid(grid);
                personalGridFlag = false;
            } else if (attackGridFlag) {
                attackGrid.setGrid(grid);
                attackGridFlag = false;
            } else {
                // Se nessun flag è stato impostato, aggiorna la griglia personale per default
                personalGrid.setGrid(grid);
            }
        });
    }

    /**
     * Chiude la finestra della GUI e libera le risorse associate.
     */
    @Override
    public void close() {
        SwingUtilities.invokeLater(() -> frame.dispose());
    }
}
