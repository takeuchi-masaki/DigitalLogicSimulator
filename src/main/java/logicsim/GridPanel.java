package logicsim;

import logicsim.gates.ANDGate;
import logicsim.gates.GateType;
import logicsim.gates.LogicGate;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static java.lang.Math.min;

public class GridPanel extends JPanel {
    private int gridSize = 50; // Size of the grid cells
//    private List<GridComponent> gridComponentList;
    private LogicGate selected = null;

    public GridPanel() {
        setPreferredSize(new Dimension(getWidth(), getHeight()));
        setupMouseEvents();
    }

    public Point closesetPoint(Point mousePos) {
        int width = getWidth();
        int height = getHeight();
        return new Point(min(width, mousePos.x / gridSize), min(height, mousePos.y / gridSize));
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

    private void setupMouseEvents() {
        TransferHandler handler = new TransferHandler() {
            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                // Check for the type of data and decide whether to accept it
                return support.isDataFlavorSupported(LogicGate.LOGIC_GATE_FLAVOR);
            }

            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                try {
                    selected = (LogicGate) support.getTransferable().getTransferData(LogicGate.LOGIC_GATE_FLAVOR);
                } catch (UnsupportedFlavorException | IOException ex) {
                    return false;
                }
                Point dropLocation = support.getDropLocation().getDropPoint();
                selected.setPosition(dropLocation);
                repaint();
                return true;
            }
        };
        setTransferHandler(handler);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (selected != null) {
                    System.out.println("AAA");
                    selected.setPosition(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("DRAGGING");
                if (selected == null) return;
                selected.setPosition(e.getPoint());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selected == null) return;
                selected = null;
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        if (selected != null) {
            selected.draw_move(g);
        }
    }
}
