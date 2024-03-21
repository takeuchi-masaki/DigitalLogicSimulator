package logicsim;

import logicsim.gates.LogicGate;
import logicsim.grid.WireComponent;
import logicsim.inout.InputOutputComponent;
import logicsim.inout.InputOutputEnum;
import logicsim.logic.GridLogicHandler;
import logicsim.mouseAdapters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JPanel {
    private int width = 1200, height = 900;
    private static final PalettePanel palettePanel = PalettePanel.getInstance();
    private static final GridPanel gridPanel = GridPanel.getInstance();
    private static final GridLogicHandler gridlogic = GridLogicHandler.getInstance();
    private LogicGate selectedGateComponent = null;
    private InputOutputComponent selectedInOutComponent = null;
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
        gridPanel.currentMode = mode.getMode();
        palettePanel.clearHover();
        gridPanel.clearHover();
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

    public void checkHover(Point mousePosition) {
        boolean needRepaint = palettePanel.modifyHover(mousePosition);
        needRepaint |= gridPanel.modifyHover(mousePosition);
        if (needRepaint) {
            repaint();
        }
    }

    public void selectHoveredGateComponent() {
        LogicGate hovering = palettePanel.checkGateHover();
        if (hovering == null) {
            hovering = gridPanel.checkGateHover();
        }
        if (hovering == null) return;
        if (hovering.getID() == -1) {
            selectedGateComponent = hovering.uniqueCopy();
        } else {
            selectedGateComponent = hovering.clone();
        }
    }

    public void selectHoveredInOutComponent() {
        InputOutputComponent hovering = palettePanel.checkInOutHover();
        if (hovering == null) {
            hovering = gridPanel.checkInOutHover();
        }
        if (hovering == null) return;
        if (hovering.getId() == -1) {
            selectedInOutComponent = hovering.uniqueCopy();
        } else {
            selectedInOutComponent = hovering.clone();
        }
    }

    public void selectHovered() {
        selectHoveredInOutComponent();
        if (selectedInOutComponent == null) {
            selectHoveredGateComponent();
        }
    }

    private void dragSelectedGate(Point mousePosition) {
        if (selectedGateComponent == null) return;
        if (selectedGateComponent.getID() != -1) {
            gridPanel.removeGateComponent(selectedGateComponent.getID());
        }
        if (mousePosition.x >= 300) {
            selectedGateComponent.setPosition(
                selectedGateComponent.getTopLeft(gridPanel.closestAbsPoint(mousePosition))
            );
        } else {
            selectedGateComponent.setPosition(selectedGateComponent.getTopLeft(mousePosition));
        }
        selectedGateComponent.setHovered(true);
        repaint();
    }

    private boolean dragSelectedInOut(Point mousePosition) {
        if (selectedInOutComponent == null) return false;
        if (selectedInOutComponent.getId() != -1) {
            gridPanel.removeInOutComponent(selectedInOutComponent.getId());
        }
        if (mousePosition.x >= 300) {
            selectedInOutComponent.setRelativePosition(gridPanel.closestAbsPoint(mousePosition));
        } else {
            selectedInOutComponent.setRelativePosition(mousePosition);
        }
        selectedInOutComponent.setHover(true);
        repaint();
        return true;
    }

    public void dragSelectedComponent(Point mousePosition) {
        if (!dragSelectedInOut(mousePosition)) {
            dragSelectedGate(mousePosition);
        }
    }

    private void releaseSelectedGate(Point mousePosition) {
        if (mousePosition.x > 300) {
            gridPanel.addGateComponent(selectedGateComponent, gridPanel.relativePoint(mousePosition));
        }
        selectedGateComponent = null;
        repaint();
    }

    private void releaseSelectedInOutComponent(Point mousePosition) {
        if (mousePosition.x > 300) {
            gridPanel.addInOutComponent(selectedInOutComponent, gridPanel.relativePoint(mousePosition));
        }
        selectedInOutComponent = null;
        repaint();
    }

    public void releaseSelectedComponent(Point mousePosition) {
        if (selectedGateComponent != null) {
            releaseSelectedGate(mousePosition);
        }
        if (selectedInOutComponent != null) {
            releaseSelectedInOutComponent(mousePosition);
        }
    }

    public void removeComponent(Point mousePosition) {
        checkHover(mousePosition);
        selectHovered();
        if (selectedGateComponent != null) {
            gridPanel.removeGateComponent(selectedGateComponent.getID());
            selectedGateComponent = null;
        }
        if (selectedInOutComponent != null) {
            gridPanel.removeInOutComponent(selectedInOutComponent.getId());
            selectedInOutComponent = null;
        }
        repaint();
    }

    public void toggleSelectedInput() {
        if (selectedInOutComponent == null
        || selectedInOutComponent.getType() == InputOutputEnum.OUT) {
            return;
        }
        selectedInOutComponent.setEnable(!selectedInOutComponent.enabled);
        gridlogic.checkLogic(gridPanel);
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
        if (selectedGateComponent != null) {
            Color color = (currentMode.getMode() == ModeEnum.DELETE_MODE)
                ? Color.RED
                : Color.LIGHT_GRAY;
            selectedGateComponent.draw(g2d, selectedGateComponent.getCenter(), color);
        }
        if (selectedInOutComponent != null) {
            selectedInOutComponent.draw(g2d, selectedInOutComponent.relativePosition, GridPanel.gridSize);
        }
        if (hoveredWire != null) {
            Color color = (currentMode.getMode() == ModeEnum.DELETE_MODE)
                ? Color.RED
                : Color.BLUE;
            hoveredWire.draw(g2d,
                gridPanel.absolutePoint(hoveredWire.start),
                gridPanel.absolutePoint(hoveredWire.end),
                color,
                GridPanel.gridSize * 0.2f
            );
        }
    }
}
