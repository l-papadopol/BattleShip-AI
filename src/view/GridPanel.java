/*
 * 	GridPanel.java è l'interfaccia utente grafica
 *  * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package view;

import javax.swing.*;
import java.awt.*;
import model.Griglia;
import model.Casella;

public class GridPanel extends JPanel {
    private static final long serialVersionUID = 1L; // Non ho capito bene a cosa serve ma se lo metto non mi da warning LOL
    private Griglia griglia;
    private final int dimensioneCella = 50; // Dimensione della singola cella in pixel

    public GridPanel() {
        setPreferredSize(new Dimension(600, 600)); // Dimensione della finestra, di default
    }

    // Questo è il metodo che si occupa di ricreare la griglia. Questo va chiamato per aggiornare il disegno.
    public void setGriglia(Griglia griglia) {
        this.griglia = griglia;
        if (griglia != null) {
            int dim = griglia.getDimensione();
            setPreferredSize(new Dimension(dim * dimensioneCella, dim * dimensioneCella));
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
        Graphics2D griglia2D = (Graphics2D) g;

        if (griglia == null)
            return;

        int dim = griglia.getDimensione();
        int gridDimension = dim * dimensioneCella;  // Area della griglia

        // Disegna lo sfondo sull'area della griglia
        griglia2D.setColor(new Color(173, 216, 230));
        griglia2D.fillRect(0, 0, gridDimension, gridDimension);

        // Applico antialiasing per migliorare la resa grafica
        griglia2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Ottengo le caselle e imposto un margine per una migliore resa visiva
        Casella[][] caselle = griglia.getCaselle();
        int margine = 4;

        // Ciclo per processare ogni casella della griglia
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                Casella casella = caselle[x][y];
                int xCella = x * dimensioneCella;
                int yCella = y * dimensioneCella;

                // Se la casella è occupata, cioè contiene una nave
                // viene colorata in base al livello di danno.
                if (casella.getOccupata()) {
                    int livelloDanno = casella.getLivelloDanno();
                    Color coloreCasella;
                    if (livelloDanno == 0) {
                        coloreCasella = Color.GREEN;
                    } else if (livelloDanno == 1) {
                        coloreCasella = Color.YELLOW;
                    } else if (livelloDanno == 2) {
                        coloreCasella = Color.ORANGE;
                    } else if (livelloDanno == 3) {
                        coloreCasella = Color.RED;
                    } else { // danno >= 4: nave affondata
                        coloreCasella = Color.GRAY;
                    }
                    griglia2D.setColor(coloreCasella);
                    griglia2D.fillRect(xCella + margine, yCella + margine, dimensioneCella - 2 * margine, dimensioneCella - 2 * margine);
                }
                // Se la casella non è occupata ma è stata colpita
                // disegno un simbolo per indicare l'effetto del colpo.
                else if (casella.getColpita()) {
                    String simbolo;
                    if (casella.getLivelloDanno() > 0) {
                        int danno = casella.getLivelloDanno();
                        switch (danno) {
                            case 1: simbolo = "¼"; break;
                            case 2: simbolo = "½"; break;
                            case 3: simbolo = "¾"; break;
                            case 4: simbolo = "X"; break;
                            default: simbolo = "?"; break;
                        }
                    } else {
                        simbolo = "o";
                    }
                    disegnaTestoCentrato(griglia2D, simbolo, xCella, yCella, dimensioneCella, Font.BOLD);
                }
                // Caso in cui la casella non è occupata e non è stata colpita
                // disegno il simbolo "~" per rappresentare l'acqua
                else {
                    disegnaTestoCentrato(griglia2D, "~", xCella, yCella, dimensioneCella, Font.PLAIN);
                }
            }
        }

        // Disegna le linee della griglia
        griglia2D.setColor(new Color(0, 0, 139));
        griglia2D.setStroke(new BasicStroke(2));
        for (int i = 0; i <= dim; i++) {
            int x = i * dimensioneCella;
            griglia2D.drawLine(x, 0, x, gridDimension);
        }
        for (int i = 0; i <= dim; i++) {
            int y = i * dimensioneCella;
            griglia2D.drawLine(0, y, gridDimension, y);
        }
    }
    
    // Metodo che centra un testo di un font qualsiasi e di dimensione qualsiasi nella cella
    private void disegnaTestoCentrato(Graphics2D g, String text, int xCella, int yCella, int dimensioneCella, int stileFont) {
        // Imposta esplicitamente il colore a nero, così da non ereditare quello precedente
        g.setColor(Color.BLACK);
        Font fontOriginale = g.getFont();
        Font font = fontOriginale.deriveFont(stileFont, dimensioneCella * 0.6f);
        g.setFont(font);
        FontMetrics dimensioni = g.getFontMetrics(font);
        int textWidth = dimensioni.stringWidth(text);
        int textHeight = dimensioni.getHeight();
        int tx = xCella + (dimensioneCella - textWidth) / 2;
        int ty = yCella + ((dimensioneCella - textHeight) / 2) + dimensioni.getAscent();
        g.drawString(text, tx, ty);
        g.setFont(fontOriginale);
    }



}

