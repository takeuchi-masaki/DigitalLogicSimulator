package logicsim;

import logicsim.gates.GateType;
import logicsim.gates.LogicGate;
import logicsim.util.Pair;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;

import static java.lang.Math.round;

public class GridPanel {
    public static int gridSize = 50;
    private int startWidth, endWidth, height;
    private final Map<Integer, GridComponent> gridComponentMap;
    private final ImageObserver observer;
//    private List<WireComponent> wireComponentList;

    public GridPanel(int startWidth, int endWidth, int height, ImageObserver observer) {
        gridComponentMap = new HashMap<>();
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.height = height;
        this.observer = observer;
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
            g.drawImage(component.getImage(), drawLocation.x, drawLocation.y, observer);
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

}
