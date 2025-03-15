/**
 * Implementazione della view grafica per Battaglia Navale.
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

import model.Grid;

public class Gui implements ViewInterface {
    private JFrame frame;
    private GridPanel personalGrid;
    private GridPanel attackGrid;
    private JTextArea outputArea;
    private JTextField inputArea;
    
    // Flag per capire quale griglia aggiornare in gridDrawing()
    private boolean personalGridFlag = false;
    private boolean attackGridFlag = false;
    

    /*
     * Disegno la GUI
     */
    public Gui(String name) {
        try {
            SwingUtilities.invokeAndWait(() -> {
            	
            	/*
            	 * Divido la finestra
            	 */
                frame = new JFrame("Battaglia Navale");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout(10, 10));

                /*
                 * Popolo il pannello superiore
                 */
                JPanel upperPanel = new JPanel(new GridLayout(1, 2, 10, 10));
                upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Contenitore per la griglia personale
                JPanel personalGridContainer = new JPanel(new BorderLayout());
                personalGridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                // Contenitore per la griglia dei colpi sparati
                JPanel attackGridContainer = new JPanel(new BorderLayout());
                attackGridContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                 // Metto le due griglie, personale e di attacco nei rispettivi contenitori con le relative etichette
                JLabel personalGridCaption = new JLabel(name, SwingConstants.CENTER);
                personalGridContainer.add(personalGridCaption, BorderLayout.NORTH);
                personalGrid = new GridPanel();
                personalGridContainer.add(personalGrid, BorderLayout.CENTER);

                JLabel attackGridCaption = new JLabel("Colpi sparati", SwingConstants.CENTER);
                attackGridContainer.add(attackGridCaption, BorderLayout.NORTH);
                attackGrid = new GridPanel();
                attackGridContainer.add(attackGrid, BorderLayout.CENTER);

                upperPanel.add(personalGridContainer);
                upperPanel.add(attackGridContainer);
                frame.add(upperPanel, BorderLayout.NORTH);

                /*
                 *  Area centrale: output dei messaggi
                 */
                outputArea = new JTextArea(10, 40);
                outputArea.setEditable(false);
                outputArea.setBackground(Color.BLACK);
                outputArea.setForeground(Color.GREEN);
                outputArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                JScrollPane outputMsgPanel = new JScrollPane(outputArea);
                outputMsgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                frame.add(outputMsgPanel, BorderLayout.CENTER);

                /*
                 * Livello inferiore della finestra
                 * Campo di input con aggiunto un bordo visibile e padding interno
                 */
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

    /*
     * Implemento i metodi dell'interfaccia
     */
    @Override
    public void showMsg(String message) {
        SwingUtilities.invokeLater(() -> {
            // Imposta il flag in base al messaggio inviato
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

    @Override
    public String prompt(String message) {
        // Mostra il messaggio per indicare all'utente cosa inserire
        showMsg(message);
        
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

    /*
     *  Gestisce ed aggiorna le griglie personale e di attacco in base a che messaggi arrivano dal controller
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
                // Se non è stato impostato alcun flag, aggiorna quello personale
                personalGrid.setGrid(grid);
            }
        });
    }

    /*
     *  Chiude tutto
     */
    @Override
    public void close() {
        SwingUtilities.invokeLater(() -> frame.dispose());
    }

}
