package logicsim;

import logicsim.gates.GateType;
import logicsim.gates.LogicGate;
import logicsim.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
    private int width = 1200, height = 900;
    PalettePanel palettePanel;
    GridPanel gridPanel;
    LogicGate selected = null;
    int selectedID = -1;

    public MainPanel() {
        setPreferredSize(new Dimension(width, height));
        palettePanel = new PalettePanel(300, height);
        gridPanel = new GridPanel(300, width, height);
        initResizeListener();
        initMouseHandler();
    }

    private void initMouseHandler() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean needRepaint =
                        palettePanel.modifyHover(e.getPoint())
                        || gridPanel.modifyHover(e.getPoint());
                if (!needRepaint) { return; }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Pair<GateType, Integer> hovering = palettePanel.checkHover();
                if (hovering == null) {
                    hovering = gridPanel.checkHover();
                }
                if (hovering == null) return;
                selected = LogicGate.logicGateFactory(hovering.first, e.getPoint());
                selectedID = hovering.second;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selected == null) return;
                if (selectedID != -1) {
                    gridPanel.removeComponent(selectedID);
                }
                Point currPos = e.getPoint();
                if (currPos.x > 300) {
                    selected.setPosition(gridPanel.closestAbsPoint(currPos));
                } else {
                    selected.setPosition(currPos);
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selected == null) return;
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

        gridPanel.draw(g2d);
        palettePanel.draw(g2d);
        if (selected != null) {
            selected.draw_move(g, selected.getPos());
        }
    }
}
