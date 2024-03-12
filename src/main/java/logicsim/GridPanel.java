package logicsim;

import logicsim.gates.*;

import java.awt.*;
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
            GATE_TYPES.add(new ORGate());
            GATE_TYPES.add(new XORGate());
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
        return new Point(
                (int)round((absPoint.x - startWidth) / (double)gridSize),
                (int)round(absPoint.y / (double)gridSize)
        );
    }

    public Point absolutePoint(Point relPoint) {
        return new Point(relPoint.x * gridSize + startWidth, relPoint.y * gridSize);
    }

    public Point closestAbsPoint(Point mousePos) {
        return absolutePoint(relativePoint(mousePos));
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

    public LogicGate checkHover() {
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
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }

    public void zoomOut() {
        gridSize = (int)max(5, gridSize / 1.2);
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }

    public void resetZoom() {
        gridSize = 20;
        LogicGate.resizeScale(gridSize);
        for (LogicGate gate : GATE_TYPES) {
            gate.resizeImage(gridSize);
        }
    }
}
