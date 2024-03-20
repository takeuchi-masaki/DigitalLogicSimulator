package logicsim;

import logicsim.gates.*;
import logicsim.grid.GridPanel;
import logicsim.grid.WireComponent;
import logicsim.mouseAdapters.*;
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
    public final MouseMode moveMode = new MoveMode(this);
    public final MouseMode wireMode = new WireMode(this);
    public final MouseMode deleteMode = new DeleteMode(this);

    public MainPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        initButtons();
        initResizeListener();
        initKeyListener();
        setMode(moveMode);
    }

    public void setMode(MouseMode mode) {
        palettePanel.moveButton.setSelected(mode.getMode() == palettePanel.moveButton.getMode());
        palettePanel.wireModeButton.setSelected(mode.getMode() == palettePanel.wireModeButton.getMode());
        palettePanel.deleteModeButton.setSelected(mode.getMode() == palettePanel.deleteModeButton.getMode());
        if (currentMode == mode) return;
        palettePanel.clearHover();
        gridPanel.clearComponentHover();
        removeMouseListener(currentMode);
        removeMouseMotionListener(currentMode);
        currentMode = mode;
        addMouseListener(mode);
        addMouseMotionListener(mode);
        repaint();
    }

    private void initButtons() {
        palettePanel.moveButton.addActionListener(e -> setMode(moveMode));
        palettePanel.wireModeButton.addActionListener(e -> setMode(wireMode));
        palettePanel.deleteModeButton.addActionListener(e -> setMode(deleteMode));
        add(palettePanel.moveButton);
        add(palettePanel.wireModeButton);
        add(palettePanel.deleteModeButton);
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
                    setMode(moveMode);
                } else if (keycode == KeyEvent.VK_W) {
                    if (currentMode.getMode() == ModeEnum.WIRE_MODE) {
                        setMode(moveMode);
                    } else {
                        setMode(wireMode);
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

    public void selectHoveredComponent() {
        LogicGate hovering = palettePanel.checkHover();
        if (hovering == null) {
            hovering = gridPanel.checkComponentHover();
        }
        if (hovering == null) return;
        if (hovering.getID() == -1) {
            selectedComponent = hovering.uniqueCopy();
        } else {
            selectedComponent = hovering.clone();
        }
    }

    public void dragSelectedComponent(Point mousePosition) {
        if (selectedComponent == null) return;
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
        if (selectedComponent == null) return;
        if (mousePosition.x > 300) {
            gridPanel.addComponent(selectedComponent, gridPanel.relativePoint(mousePosition));
        }
        selectedComponent = null;
        repaint();
    }

    public void removeComponent(Point mousePosition) {
        checkComponentHover(mousePosition);
        selectHoveredComponent();
        if (selectedComponent == null) return;
        gridPanel.removeComponent(selectedComponent.getID());
        selectedComponent = null;
        repaint();
    }

    public void hoverWire(Point mousePosition) {
        hoveredWire = gridPanel.closestWire(mousePosition);
        repaint();
    }

    public boolean hoverExistingWire(Point mousePosition) {
        hoveredWire = gridPanel.closestWire(mousePosition);
        if (hoveredWire != null
            && !gridPanel.containsWire(hoveredWire)) {
            hoveredWire = null;
        }
        repaint();
        return hoveredWire != null;
    }

    public void addWire() {
        if (hoveredWire == null) return;
        gridPanel.addWire(hoveredWire);
        hoveredWire = null;
        repaint();
    }

    public boolean removeWire() {
        if (hoveredWire == null) return false;
        gridPanel.removeWire(hoveredWire);
        hoveredWire = null;
        repaint();
        return true;
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
            Color color = (currentMode.getMode() == ModeEnum.DELETE_MODE
                ? Color.RED
                : Color.BLUE);
            hoveredWire.draw(g2d,
                gridPanel.absolutePoint(hoveredWire.start),
                gridPanel.absolutePoint(hoveredWire.end),
                color
            );
        }
    }
}
