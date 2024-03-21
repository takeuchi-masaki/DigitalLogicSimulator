package logicsim.grid;

import logicsim.gates.*;
import logicsim.inout.*;
import logicsim.mouseAdapters.ModeEnum;

import java.awt.*;
import java.util.*;

import static java.lang.Math.*;

public class GridPanel {
    private static GridPanel INSTANCE = null;
    private final int defaultGridSize = 20;
    public static int gridSize;
    private int startWidth = 300, endWidth = 1200, height = 900;
    private final Map<Integer, GridComponent> gateComponentMap;
    private final Map<Integer, InputOutputComponent> inputOutputComponentMap;
    private final ArrayList<WireComponent> wires;
    public ModeEnum currentMode;

    private GridPanel() {
        gateComponentMap = new HashMap<>();
        inputOutputComponentMap = new HashMap<>();
        wires = new ArrayList<>();
        resetZoom();
    }

    public static GridPanel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GridPanel();
        }
        return INSTANCE;
    }

    public void setDimensions(int startWidth, int endWidth, int height) {
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.height = height;
    }

    public Point relativePoint(Point absPoint) {
        return new Point(
            (int)round((absPoint.x - startWidth) / (double)gridSize),
            (int)round(absPoint.y / (double)gridSize)
        );
    }

    public Point absolutePoint(Point relPoint) {
        return new Point(
        relPoint.x * gridSize + startWidth,
        relPoint.y * gridSize
        );
    }

    public Point closestAbsPoint(Point mousePos) {
        return absolutePoint(relativePoint(mousePos));
    }

    public WireComponent closestWire(Point mousePosition) {
        final int[] dx = { -1, 1, 0, 0 };
        final int[] dy = { 0, 0, -1, 1 };
        Point start = relativePoint(mousePosition);
        Point absStart = absolutePoint(start);
        int best = Integer.MAX_VALUE;
        Point bestEnd = new Point();
        for (int i = 0; i < 4; i++) {
            Point end = new Point(start.x + dx[i], start.y + dy[i]);
            if ((start.x <= 0 && end.x <= 0)
                || (start.y <= 0 && end.y <= 0)) {
                continue;
            }
            Point absEnd = absolutePoint(end);
            int dist = abs(mousePosition.x - (absStart.x + absEnd.x) / 2)
                    + abs(mousePosition.y - (absStart.y + absEnd.y) / 2);
            if (dist < best) {
                best = dist;
                bestEnd = end;
            }
        }
        if (best == Integer.MAX_VALUE) return null;
        return new WireComponent(start, bestEnd);
    }

    public void addWire(WireComponent wire) {
        if (!wires.contains(wire)) {
            wires.add(wire);
        }
    }

    public void addGateComponent(LogicGate gate, Point relativePos) {
        GridComponent add = new GridComponent(gate, relativePos);
        gateComponentMap.put(add.getID(), add);
    }

    public void removeGateComponent(int selectedID) {
        gateComponentMap.remove(selectedID);
    }

    public void addInOutComponent(InputOutputComponent inout, Point position) {
        InputOutputComponent add =
            InputOutputComponent.inputOutputFactory(
                    inout.getType(), position, inout.enabled, inout.getId()
            );
        inputOutputComponentMap.put(add.getId(), add);
    }

    public void removeInOutComponent(int selectedID) {
        inputOutputComponentMap.remove(selectedID);
    }

    public void clearHover() {
        for (GridComponent component : gateComponentMap.values()) {
            component.setHover(false);
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            component.setHover(false);
        }
    }

    public boolean modifyHover(Point point) {
        boolean modified = false;
        Point relPoint = relativePoint(point);
        for (GridComponent component : gateComponentMap.values()) {
            if (component.contains(relPoint) != component.isHovered()) {
                component.setHover(component.contains(relPoint));
                modified = true;
            }
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            if (component.position.equals(relPoint) != component.isHovered()) {
                component.setHover(component.position.equals(relPoint));
                modified = true;
            }
        }
        return modified;
    }

    public LogicGate checkGateHover() {
        for (GridComponent component : gateComponentMap.values()) {
            if (component.isHovered()) {
                return component.getGate();
            }
        }
        return null;
    }

    public InputOutputComponent checkInOutHover() {
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            if (component.isHovered()) {
                return component;
            }
        }
        return null;
    }

    public boolean containsWire(WireComponent hoveredWire) {
        for (WireComponent wire : wires) {
            if (wire.start.equals(hoveredWire.start)
                && wire.end.equals(hoveredWire.end)) {
                return true;
            }
        }
        return false;
    }

    public void removeWire(WireComponent hoveredWire) {
        for (int i = 0; i < wires.size(); i++) {
            if (wires.get(i).start.equals(hoveredWire.start)
                    && wires.get(i).end.equals(hoveredWire.end)) {
                wires.remove(i);
                return;
            }
        }
    }

    public void zoomIn() {
        gridSize = (int)min(80, gridSize * 1.2);
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage();
        }
    }

    public void zoomOut() {
        gridSize = (int)max(5, gridSize / 1.2);
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage();
        }
    }

    public void resetZoom() {
        gridSize = defaultGridSize;
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage();
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = startWidth; x < endWidth; x += gridSize) {
            for (int y = 0; y < height; y += gridSize) {
                g.drawLine(x, 0, x, height);
                g.drawLine(x, y, endWidth, y);
            }
        }
        for (GridComponent component : gateComponentMap.values()) {
            Point drawLocation = absolutePoint(component.gate.getTopLeft());
            Color color = null;
            if (component.isHovered()) {
                if (currentMode == ModeEnum.DELETE_MODE) {
                    color = Color.RED;
                } else {
                    color = Color.LIGHT_GRAY;
                }
            }
            component.draw(g, drawLocation, color);
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            Point drawLocation = absolutePoint(component.position);
            component.draw(g, drawLocation, gridSize);
        }
        for (WireComponent wire : wires) {
            wire.draw(g,
                absolutePoint(wire.start),
                absolutePoint(wire.end),
                Color.BLACK,
                gridSize * 0.2f);
        }
    }
}
