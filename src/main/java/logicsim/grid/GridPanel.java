package logicsim.grid;

import logicsim.gates.*;

import java.awt.*;
import java.util.*;

import static java.lang.Math.*;

public class GridPanel {
    private static GridPanel INSTANCE = null;
    private static int gridSize = 20;
    private int startWidth = 300, endWidth = 1200, height = 900;
    private final Map<Integer, GridComponent> gridComponentMap;
    private final ArrayList<WireComponent> wires;

    private GridPanel() {
        gridComponentMap = new HashMap<>();
        wires = new ArrayList<>();
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

    public void addComponent(LogicGate gate, Point relativePos) {
        GridComponent add = new GridComponent(gate, relativePos);
        gridComponentMap.put(add.getID(), add);
    }

    public void removeComponent(int selectedID) {
        gridComponentMap.remove(selectedID);
    }

    public void clearComponentHover() {
        for (GridComponent component : gridComponentMap.values()) {
            component.setHovered(false);
        }
    }

    public boolean modifyComponentHover(Point point) {
        Point relPoint = relativePoint(point);
        for (GridComponent component : gridComponentMap.values()) {
            if (component.contains(relPoint) != component.isHovered()) {
                component.setHovered(component.contains(relPoint));
                return true;
            }
        }
        return false;
    }

    public LogicGate checkComponentHover() {
        for (GridComponent component : gridComponentMap.values()) {
            if (component.isHovered()) {
                return component.getGate();
            }
        }
        return null;
    }

    public void zoomIn() {
        gridSize = (int)min(80, gridSize * 1.2);
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage(gridSize);
        }
    }

    public void zoomOut() {
        gridSize = (int)max(5, gridSize / 1.2);
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage(gridSize);
        }
    }

    public void resetZoom() {
        gridSize = 20;
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : LogicGate.getTypes()) {
            gate.resizeImage(gridSize);
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
        for (GridComponent component : gridComponentMap.values()) {
            Point drawLocation = absolutePoint(component.gate.getTopLeft());
            component.draw(g, drawLocation);
        }
        for (WireComponent wire : wires) {
            wire.draw(g, absolutePoint(wire.start), absolutePoint(wire.end));
        }
    }
}
