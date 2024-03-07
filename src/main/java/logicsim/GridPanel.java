package logicsim;

import logicsim.gates.ANDGate;
import logicsim.gates.GateType;
import logicsim.gates.LogicGate;
import logicsim.util.Pair;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;
import java.util.List;

import static java.lang.Math.*;

public class GridPanel {
    private static int gridSize = 20;
    private int startWidth, endWidth, height;
    private final Map<Integer, GridComponent> gridComponentMap;
    private final static List<LogicGate> GATE_TYPES = new ArrayList<>();
//    private List<WireComponent> wireComponentList; // TODO: wires

    public GridPanel(int startWidth, int endWidth, int height) {
        if (GATE_TYPES.isEmpty()) {
            GATE_TYPES.add(new ANDGate());
//            GATE_TYPES.add(); // TODO: add other types
        }
        gridComponentMap = new HashMap<>();
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.height = height;
    }

    public void setDimensions(int startWidth, int endWidth, int height) {
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.height = height;
    }

    public Point relativePoint(Point absPoint) {
        return new Point((int)round((double)(absPoint.x - startWidth) / gridSize), (int)round((double)absPoint.y / gridSize));
    }

    public Point absolutePoint(Point relPoint) {
        return new Point(relPoint.x * gridSize + startWidth, relPoint.y * gridSize);
    }

    public Point closestAbsPoint(Point mousePos) {
        return absolutePoint(relativePoint(mousePos));
    }

    private static void resizeGrid(int newScale) {
        gridSize = newScale;
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
            Point drawLocation = absolutePoint(component.gate.getPos());
            component.draw(g, drawLocation);
        }
    }

    public void addComponent(LogicGate gate, Point relativePos) {
        GridComponent add = new GridComponent(gate, relativePos);
        gridComponentMap.put(add.getID(), add);
    }

    public void removeComponent(int selectedID) {
        gridComponentMap.remove(selectedID);
    }

    public boolean modifyHover(Point point) {
        Point relPoint = relativePoint(point);
        for (GridComponent component : gridComponentMap.values()) {
            if (component.contains(relPoint) != component.isHovered()) {
                component.setHovered(component.contains(relPoint));
                return true;
            }
        }
        return false;
    }

    public Pair<GateType, Integer> checkHover() {
        for (GridComponent component : gridComponentMap.values()) {
            if (component.isHovered()) {
                return new Pair<>(component.gate.getType(), component.getID());
            }
        }
        return null;
    }

    public void zoomIn() {
        resizeGrid((int) min(80, gridSize * 1.2));
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }

    public void zoomOut() {
        resizeGrid((int) max(5, gridSize / 1.2));
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }

    public void resetZoom() {
        resizeGrid(20);
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }
}
