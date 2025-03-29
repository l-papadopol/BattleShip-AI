package view.components;

import javax.swing.*;

import model.entities.Grid;
import model.entities.GridSquare;

import java.awt.*;

/**
 * GridPanel.java disegna una griglia graficamente decente per il gioco Battaglia Navale.
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 *
 * Questa classe estende {@link JPanel} e si occupa di disegnare la griglia di gioco, le navi, i colpi andati a vuoto e 
 * i livelli di danno applicati alle caselle. 
 * Sono disegnati anche i contorni della griglia e le etichette di riferimento per righe e colonne.
 */
public class GridPanel extends JPanel {
    private static final long serialVersionUID = 1L; // Identificativo della classe per la serializzazione
    private Grid grid;
    private final int gridSquareSize = 50; // Dimensione della singola cella in pixel
    private final int margin = 30;         // Margine per le etichette (in alto e a sinistra)

    /**
     * Costruisce un nuovo pannello di griglia con una dimensione di default.
     */
    public GridPanel() {
        setPreferredSize(new Dimension(600, 600));
    }

    /**
     * Imposta la griglia da disegnare e aggiorna la dimensione del pannello in base alla dimensione della griglia.
     *
     * @param grid la griglia da visualizzare
     */
    public void setGrid(Grid grid) {
        this.grid = grid;
        if (grid != null) {
            int dim = grid.getSize();
            // Imposta la dimensione del pannello includendo il margine per le etichette
            setPreferredSize(new Dimension(dim * gridSquareSize + margin, dim * gridSquareSize + margin));
            revalidate();
        }
        repaint();
    }
    
    /**
     * Sovrascrive il metodo paintComponent per disegnare la griglia di gioco.
     * Vengono disegnati:
     * <ul>
     *   <li>Lo sfondo della griglia (azzurro)</li>
     *   <li>Le celle colorate in base allo stato: navi, danni, acqua o colpi a vuoto</li>
     *   <li>Le linee della griglia</li>
     *   <li>Le etichette di riferimento per righe e colonne</li>
     * </ul>
     *
     * @param g il contesto grafico utilizzato per disegnare
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D grid2D = (Graphics2D) g;

        if (grid == null)
            return;

        int dim = grid.getSize();
        int gridDimension = dim * gridSquareSize;  // Area della griglia

        // Disegno lo sfondo azzurro per l'area della griglia, spostato del margine
        grid2D.setColor(new Color(173, 216, 230));
        grid2D.fillRect(margin, margin, gridDimension, gridDimension);

        // Abilito l'antialiasing per una migliore resa grafica
        grid2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GridSquare[][] gridSquares = grid.getGridSquares();
        int clearance = 4; // Spazio di clearance all'interno di ogni cella

        // Ciclo per processare ogni cella della griglia
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                GridSquare gridSquare = gridSquares[x][y];
                // Calcolo la posizione della cella considerando il margine
                int gridSquareX = margin + x * gridSquareSize;
                int gridSquareY = margin + y * gridSquareSize;

                // Se la cella è occupata da una nave, viene colorata in base al livello di danno
                if (gridSquare.getIsOccupied()) {
                    int damageLevel = gridSquare.getDamageLevel();
                    Color gridSquareColour;
                    if (damageLevel == 0) {
                        gridSquareColour = new Color(7, 184, 10);
                    } else if (damageLevel == 1) {
                        gridSquareColour = Color.YELLOW;
                    } else if (damageLevel == 2) {
                        gridSquareColour = Color.ORANGE;
                    } else if (damageLevel == 3) {
                        gridSquareColour = Color.RED;
                    } else { 
                        gridSquareColour = Color.GRAY;
                    }
                    grid2D.setColor(gridSquareColour);
                    grid2D.fillRect(gridSquareX + clearance, gridSquareY + clearance, gridSquareSize - 2 * clearance, gridSquareSize - 2 * clearance);
                }
                // Se la cella non è occupata ma è stata colpita, disegno un simbolo al centro
                else if (gridSquare.getIsHit()) {
                    String damageSymbol;
                    if (gridSquare.getDamageLevel() > 0) {
                        int damageLevel = gridSquare.getDamageLevel();
                        switch (damageLevel) {
                            case 1: damageSymbol = "¼"; break;
                            case 2: damageSymbol = "½"; break;
                            case 3: damageSymbol = "¾"; break;
                            case 4: damageSymbol = "X"; break;
                            default: damageSymbol = "?"; break;
                        }
                    } else {
                        damageSymbol = "o";
                    }
                    writeCenteredText(grid2D, damageSymbol, gridSquareX, gridSquareY, gridSquareSize, Font.BOLD);
                }
                // Se la cella non è occupata e non è stata colpita, rappresenta l'acqua con "~"
                else {
                    writeCenteredText(grid2D, "~", gridSquareX, gridSquareY, gridSquareSize, Font.PLAIN);
                }
            }
        }

        // Disegna le linee della griglia tenendo conto del margine
        grid2D.setColor(new Color(0, 0, 139));
        grid2D.setStroke(new BasicStroke(2));
        for (int i = 0; i <= dim; i++) {
            int x = margin + i * gridSquareSize;
            grid2D.drawLine(x, margin, x, margin + gridDimension);
        }
        for (int i = 0; i <= dim; i++) {
            int y = margin + i * gridSquareSize;
            grid2D.drawLine(margin, y, margin + gridDimension, y);
        }
        
        // Disegno le etichette di riferimento per le colonne (in alto)
        grid2D.setColor(Color.BLACK);
        Font originalFont = grid2D.getFont();
        Font labelFont = originalFont.deriveFont(Font.PLAIN, 12);
        grid2D.setFont(labelFont);
        FontMetrics fm = grid2D.getFontMetrics(labelFont);
        
        for (int x = 0; x < dim; x++) {
            String label = String.valueOf(x);
            int labelWidth = fm.stringWidth(label);
            int posX = margin + x * gridSquareSize + (gridSquareSize - labelWidth) / 2;
            int posY = margin - 5; // Posizionato sopra la griglia
            grid2D.drawString(label, posX, posY);
        }
        
        // Disegno le etichette di riferimento per le righe (a sinistra)
        for (int y = 0; y < dim; y++) {
            String label = String.valueOf(y);
            int labelWidth = fm.stringWidth(label);
            int posX = margin - labelWidth - 5; // Posizionato a sinistra della griglia
            int posY = margin + y * gridSquareSize + (gridSquareSize + fm.getAscent()) / 2 - 2;
            grid2D.drawString(label, posX, posY);
        }
        
        // Ripristino il font originale
        grid2D.setFont(originalFont);
    }
    
    /**
     * Scrive un testo centrato all'interno di una cella della griglia.
     *
     * @param g il contesto grafico utilizzato per disegnare
     * @param text il testo da scrivere
     * @param gridSquareX la coordinata X della cella
     * @param gridSquareY la coordinata Y della cella
     * @param gridSquareSize la dimensione della cella
     * @param stileFont lo stile del font da utilizzare (es. {@link Font#BOLD} o {@link Font#PLAIN})
     */
    private void writeCenteredText(Graphics2D g, String text, int gridSquareX, int gridSquareY, int gridSquareSize, int stileFont) {
        // Imposta il colore a nero per il testo
        g.setColor(Color.BLACK);
        Font originalFont = g.getFont();
        // Imposta il font con la dimensione relativa alla cella
        Font font = originalFont.deriveFont(stileFont, gridSquareSize * 0.6f);
        g.setFont(font);
        FontMetrics dimensions = g.getFontMetrics(font);
        int textWidth = dimensions.stringWidth(text);
        int textHeight = dimensions.getHeight();
        int tx = gridSquareX + (gridSquareSize - textWidth) / 2;
        int ty = gridSquareY + ((gridSquareSize - textHeight) / 2) + dimensions.getAscent();
        g.drawString(text, tx, ty);
        g.setFont(originalFont);
    }
}


