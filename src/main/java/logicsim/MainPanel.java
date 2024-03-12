package logicsim;

import logicsim.gates.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
    private int width = 1200, height = 900;
    private static PalettePanel palettePanel = PalettePanel.getInstance();
    private static GridPanel gridPanel = GridPanel.getInstance();
    LogicGate selected = null;

    public MainPanel() {
        setPreferredSize(new Dimension(width, height));
        initResizeListener();
        initMouseHandler();
    }

    private void initMouseHandler() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean needRepaint = palettePanel.modifyHover(e.getPoint())
                        || gridPanel.modifyHover(e.getPoint());
                if (needRepaint) {
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                LogicGate hovering = palettePanel.checkHover();
                if (hovering == null) {
                    hovering = gridPanel.checkHover();
                    if (hovering == null)
                        return;
                }
                if (hovering.getID() == -1) {
                    selected = hovering.uniqueCopy();
                } else {
                    selected = hovering.clone();
                }
                selected.setPosition(selected.getTopLeft(e.getPoint()));
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selected == null) {
                    return;
                }
                if (selected.getID() != -1) {
                    gridPanel.removeComponent(selected.getID());
                }
                Point mousePos = e.getPoint();
                if (mousePos.x >= 300) {
                    selected.setPosition(selected.getTopLeft(gridPanel.closestAbsPoint(mousePos)));
                } else {
                    selected.setPosition(selected.getTopLeft(mousePos));
                }
                selected.setHovered(true);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selected == null)
                    return;
                Point curr = e.getPoint();
                if (curr.x > 300) {
                    gridPanel.addComponent(selected, gridPanel.relativePoint(curr));
                }
                selected = null;
                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    gridPanel.zoomIn();
                } else {
                    gridPanel.zoomOut();
                }
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
    }

    private void initResizeListener() {
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                width = getWidth();
                height = getHeight();
                palettePanel.setDimensions(300, height);
                gridPanel.setDimensions(300, width, height);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        gridPanel.draw(g2d);
        palettePanel.draw(g2d);
        if (selected != null) {
            selected.drawScaled(g2d, selected.getCenter());
        }
    }
}
