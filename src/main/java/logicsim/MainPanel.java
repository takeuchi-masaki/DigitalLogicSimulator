package logicsim;

import logicsim.gates.*;
import logicsim.grid.GridPanel;
import logicsim.grid.WireComponent;
import logicsim.mouseAdapters.MouseMode;
import logicsim.mouseAdapters.MoveMode;
import logicsim.mouseAdapters.WireMode;
import logicsim.palette.PalettePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
    private int width = 1200, height = 900;
    private static final PalettePanel palettePanel = PalettePanel.getInstance();
    private static final GridPanel gridPanel = GridPanel.getInstance();
    private LogicGate selectedComponent = null;
    private WireComponent hoveredWire = null;
    private MouseMode currentMode;
    private final MouseAdapter moveMode = new MoveMode(this);
    private final MouseAdapter wireMode = new WireMode(this);

    public MainPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        initButtons();
        initResizeListener();
        initKeyListener();
        setMouseMoveMode();
    }

    public void setMouseMoveMode() {
        palettePanel.moveButton.setSelected(true);
        palettePanel.wireModeButton.setSelected(false);
        if (currentMode == MouseMode.MOVE_MODE) return;
        hoveredWire = null;
        currentMode = MouseMode.MOVE_MODE;
        removeMouseListener(wireMode);
        removeMouseMotionListener(wireMode);
        addMouseListener(moveMode);
        addMouseMotionListener(moveMode);
        repaint();
    }

    public void setWireMode(){
        palettePanel.wireModeButton.setSelected(true);
        palettePanel.moveButton.setSelected(false);
        if (currentMode == MouseMode.WIRE_MODE) return;
        currentMode = MouseMode.WIRE_MODE;
        removeMouseListener(moveMode);
        removeMouseMotionListener(moveMode);
        addMouseListener(wireMode);
        addMouseMotionListener(wireMode);
        palettePanel.clearHover();
        gridPanel.clearComponentHover();
        repaint();
    }

    private void initButtons() {
        palettePanel.moveButton.addActionListener(e -> setMouseMoveMode());
        palettePanel.wireModeButton.addActionListener(e -> setWireMode());
        add(palettePanel.moveButton);
        add(palettePanel.wireModeButton);
    }

    private void initResizeListener() {
        MouseWheelListener mouseWheelListener = new MouseAdapter() {
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
        addMouseWheelListener(mouseWheelListener);
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

    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                if (keycode == KeyEvent.VK_ESCAPE) {
                    setMouseMoveMode();
                } else if (keycode == KeyEvent.VK_W) {
                    if (currentMode == MouseMode.WIRE_MODE) {
                        setMouseMoveMode();
                    } else {
                        setWireMode();
                    }
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow(); // Request focus to receive key events
    }

    public void checkComponentHover(Point mousePosition) {
        boolean needRepaint = palettePanel.modifyHover(mousePosition)
                || gridPanel.modifyComponentHover(mousePosition);
        if (needRepaint) {
            repaint();
        }
    }

    public void selectHoveredComponent(Point mousePosition) {
        LogicGate hovering = palettePanel.checkHover();
        if (hovering == null) {
            hovering = gridPanel.checkComponentHover();
            if (hovering == null)
                return;
        }
        if (hovering.getID() == -1) {
            selectedComponent = hovering.uniqueCopy();
        } else {
            selectedComponent = hovering.clone();
        }
        selectedComponent.setPosition(selectedComponent.getTopLeft(mousePosition));
        repaint();
    }

    public void dragSelectedComponent(Point mousePosition) {
        if (selectedComponent == null) {
            return;
        }
        if (selectedComponent.getID() != -1) {
            gridPanel.removeComponent(selectedComponent.getID());
        }
        if (mousePosition.x >= 300) {
            selectedComponent.setPosition(selectedComponent.getTopLeft(gridPanel.closestAbsPoint(mousePosition)));
        } else {
            selectedComponent.setPosition(selectedComponent.getTopLeft(mousePosition));
        }
        selectedComponent.setHovered(true);
        repaint();
    }

    public void releaseSelectedComponent(Point mousePosition) {
        if (selectedComponent == null) {
            return;
        }
        if (mousePosition.x > 300) {
            gridPanel.addComponent(selectedComponent, gridPanel.relativePoint(mousePosition));
        }
        selectedComponent = null;
        repaint();
    }

    public void hoverWire(Point mousePosition) {
        hoveredWire = gridPanel.closestWire(mousePosition);
        repaint();
    }

    public void addWire() {
        if (hoveredWire == null) return;
        gridPanel.addWire(hoveredWire);
        hoveredWire = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        gridPanel.draw(g2d);
        palettePanel.draw(g2d);
        if (selectedComponent != null) {
            selectedComponent.drawScaled(g2d, selectedComponent.getCenter());
        }
        if (hoveredWire != null) {
            hoveredWire.drawHovered(g2d,
                    gridPanel.absolutePoint(hoveredWire.start),
                    gridPanel.absolutePoint(hoveredWire.end)
            );
        }
    }
}
