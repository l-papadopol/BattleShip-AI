/*
 * 	GridPanel.java disegna una griglia graficamente decente
 *  * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import javax.swing.*;
import java.awt.*;
import model.Grid;
import model.GridSquare;

public class GridPanel extends JPanel {
    private static final long serialVersionUID = 1L; // Non ho capito bene a cosa serve ma se lo metto non mi da warning LOL
    private Grid grid;
    private final int gridSquareSize = 50; // Dimensione della singola cella in pixel
  
    private final int margin = 30; // Margine per le etichette (in alto e a sinistra)

    public GridPanel() {

        setPreferredSize(new Dimension(600, 600));       // Dimensione della finestra di default
    }

    // Questo è il metodo che si occupa di ricreare la griglia. Questo va chiamato per aggiornare il disegno.
    public void setGrid(Grid grid) {
        this.grid = grid;
        if (grid != null) {
            int dim = grid.getSize();
            // Imposto la dimensione del pannello includendo il margine per le etichette
            setPreferredSize(new Dimension(dim * gridSquareSize + margin, dim * gridSquareSize + margin));
            revalidate();
        }
        repaint();
    }
    
    /*
     * Questo metodo sovrascrive il paintComponent del JPanel disegnando la gliglia di gioco
     * Disegna:
     * - Sfondo azzurro
     * - Griglia a scacchetti
     * - Navi piazzate (caselle verdi)
     * - Caselle colpite in gradi di colore evocativi, nella griglia personale
     * - Caselle colpite con indicazione numerica del livello di danno nella griglia di attacco
     * - Colpi andati a vuoto "o" e zone di mare "~"
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D grid2D = (Graphics2D) g;

        if (grid == null)
            return;

        int dim = grid.getSize();
        int gridDimension = dim * gridSquareSize;  // Area della griglia

        // Disegno lo sfondo sull'area della griglia spostata del margine
        grid2D.setColor(new Color(173, 216, 230));
        grid2D.fillRect(margin, margin, gridDimension, gridDimension);

        // Applico antialiasing per migliorare la resa grafica
        grid2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ottengo le caselle e imposto un margine per una migliore resa visiva
        GridSquare[][] gridSquares = grid.getGridSquares();
        int clearance = 4;

        // Ciclo per processare ogni casella della griglia
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                GridSquare gridSquare = gridSquares[x][y];
                // Sposto la griglia in base al margine
                int gridSquareX = margin + x * gridSquareSize;
                int gridSquareY = margin + y * gridSquareSize;

                // Se la casella è occupata, cioè contiene una nave
                // viene colorata in base al livello di danno.
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
                // Se la casella non è occupata ma è stata colpita
                // disegno un simbolo per indicare l'effetto del colpo.
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
                // Caso in cui la casella non è occupata e non è stata colpita
                // disegno il simbolo "~" per rappresentare l'acqua
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
        
        // Disegno le etichette di riferimento
        grid2D.setColor(Color.BLACK);
        Font originalFont = grid2D.getFont();
        Font labelFont = originalFont.deriveFont(Font.PLAIN, 12);
        grid2D.setFont(labelFont);
        FontMetrics fm = grid2D.getFontMetrics(labelFont);
        
        // Etichette orizzontali sul bordo superiore (0, 1, 2, ...)
        for (int x = 0; x < dim; x++) {
            String label = String.valueOf(x);
            int labelWidth = fm.stringWidth(label);
            int posX = margin + x * gridSquareSize + (gridSquareSize - labelWidth) / 2;
            int posY = margin - 5; // posizionato sopra la griglia
            grid2D.drawString(label, posX, posY);
        }
        
        // Etichette verticali sul bordo sinistro (0, 1, 2, ...)
        for (int y = 0; y < dim; y++) {
            String label = String.valueOf(y);
            int labelWidth = fm.stringWidth(label);
            int posX = margin - labelWidth - 5; // posizionato a sinistra della griglia
            int posY = margin + y * gridSquareSize + (gridSquareSize + fm.getAscent()) / 2 - 2;
            grid2D.drawString(label, posX, posY);
        }
        
        // Ripristino il font originale
        grid2D.setFont(originalFont);
    }
    
    // Metodo che scrive centrandolo, un testo di un font qualsiasi e di dimensione qualsiasi nella cella
    private void writeCenteredText(Graphics2D g, String text, int gridSquareX, int gridSquareY, int gridSquareSize, int stileFont) {
        // Imposta esplicitamente il colore a nero, così da non ereditare quello precedente sennò vengono cose strane
        g.setColor(Color.BLACK);
        Font originalFont = g.getFont();
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


