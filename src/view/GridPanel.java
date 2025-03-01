package view;

import javax.swing.*;
import java.awt.*;
import model.Griglia;
import model.Casella;

public class GridPanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private Griglia griglia;
    private final int cellSize = 100; // ogni cella è 100x100 pixel

    public GridPanel() {
        // Dimensione di default
        setPreferredSize(new Dimension(400, 400));
    }

    public void setGriglia(Griglia griglia) {
        this.griglia = griglia;
        if (griglia != null) {
            int dim = griglia.getDimensione();
            setPreferredSize(new Dimension(dim * cellSize, dim * cellSize));
            revalidate();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Impostazioni per un rendering migliore
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Sfondo: azzurro chiaro
        g2d.setColor(new Color(173, 216, 230));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (griglia == null)
            return;

        int dim = griglia.getDimensione();
        Casella[][] caselle = griglia.getCaselle();

        // Disegna le celle in cui sono presenti le navi
        for (int y = 0; y < dim; y++) {
            for (int x = 0; x < dim; x++) {
                Casella casella = caselle[x][y];
                if (casella.getOccupata()) {
                    int damage = casella.getLivelloDanno();
                    Color shipColor;
                    if (damage == 0) {
                        shipColor = Color.GREEN;
                    } else if (damage == 1) {
                        shipColor = Color.YELLOW;
                    } else if (damage == 2) {
                        shipColor = Color.ORANGE;
                    } else if (damage == 3) {
                        shipColor = Color.RED;
                    } else { // damage >= 4: affondato
                        shipColor = Color.GRAY;
                    }
                    g2d.setColor(shipColor);
                    int cellX = x * cellSize;
                    int cellY = y * cellSize;
                    int margin = 4; // per lasciare spazio alle linee della griglia
                    g2d.fillRect(cellX + margin, cellY + margin, cellSize - 2 * margin, cellSize - 2 * margin);
                }
            }
        }

        // Disegna le linee della griglia (scacchiera)
        g2d.setColor(new Color(0, 0, 139)); // blu scuro
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i <= dim; i++) {
            int x = i * cellSize;
            g2d.drawLine(x, 0, x, dim * cellSize);
        }
        for (int i = 0; i <= dim; i++) {
            int y = i * cellSize;
            g2d.drawLine(0, y, dim * cellSize, y);
        }
    }
}
