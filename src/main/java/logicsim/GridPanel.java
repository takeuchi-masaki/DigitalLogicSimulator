package logicsim;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private int gridSize = 50; // Size of the grid cells

    public GridPanel() {
        setPreferredSize(new Dimension(getWidth(), getHeight())); // Example size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        // Optional: draw components if you maintain a list of them
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        g2d.setColor(Color.LIGHT_GRAY);

        // Adjust grid drawing based on the current zoom level
        for (int x = 0; x < width; x += gridSize) {
            for (int y = 0; y < height; y += gridSize) {
                g2d.drawLine(x, 0, x, height);
                g2d.drawLine(0, y, width, y);
            }
        }
    }
}
