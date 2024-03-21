package logicsim;

import logicsim.gates.*;
import logicsim.grid.GateComponent;
import logicsim.grid.WireComponent;
import logicsim.importexport.GateComponentWrapper;
import logicsim.importexport.GridWrapper;
import logicsim.importexport.InputOutputWrapper;
import logicsim.importexport.WireWrapper;
import logicsim.inout.*;
import logicsim.logic.GridLogicHandler;
import logicsim.logic.ValidEnum;
import logicsim.mouseAdapters.ModeEnum;

import java.awt.*;
import java.util.*;

import static java.lang.Math.*;

public class GridPanel {
    private static GridPanel INSTANCE = null;
    private static GridLogicHandler gridLogicHandler = GridLogicHandler.getInstance();
    private final int defaultGridSize = 30;
    public static int gridSize;
    private int startWidth = 300, endWidth = 1200, height = 900;
    public final Map<Integer, GateComponent> gateComponentMap;
    public final Map<Integer, InputOutputComponent> inputOutputComponentMap;
    public final ArrayList<WireComponent> wireList;
    public ModeEnum currentMode;

    private GridPanel() {
        gateComponentMap = new HashMap<>();
        inputOutputComponentMap = new HashMap<>();
        wireList = new ArrayList<>();
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
        if (!wireList.contains(wire)) {
            wireList.add(wire);
            gridLogicHandler.checkLogic(this);
        }
    }

    public void addGateComponent(LogicGate gate, Point relativePos) {
        GateComponent add = new GateComponent(gate, relativePos);
        gateComponentMap.put(add.getID(), add);
        gridLogicHandler.checkLogic(this);
    }

    public void removeGateComponent(int selectedID) {
        gateComponentMap.remove(selectedID);
        gridLogicHandler.checkLogic(this);
    }

    public void addInOutComponent(InputOutputComponent inout, Point position) {
        InputOutputComponent add =
            InputOutputComponent.inputOutputFactory(
                    inout.getType(), position, inout.enabled, inout.getId()
            );
        inputOutputComponentMap.put(add.getId(), add);
        gridLogicHandler.checkLogic(this);
    }

    public void removeInOutComponent(int selectedID) {
        inputOutputComponentMap.remove(selectedID);
        gridLogicHandler.checkLogic(this);
    }

    public void clearHover() {
        for (GateComponent component : gateComponentMap.values()) {
            component.setHover(false);
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            component.setHover(false);
        }
    }

    public boolean modifyHover(Point point) {
        boolean modified = false;
        Point relPoint = relativePoint(point);
        for (GateComponent component : gateComponentMap.values()) {
            if (component.contains(relPoint) != component.isHovered()) {
                component.setHover(component.contains(relPoint));
                modified = true;
            }
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            if (component.relativePosition.equals(relPoint) != component.isHovered()) {
                component.setHover(component.relativePosition.equals(relPoint));
                modified = true;
            }
        }
        return modified;
    }

    public LogicGate checkGateHover() {
        for (GateComponent component : gateComponentMap.values()) {
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
        for (WireComponent wire : wireList) {
            if (wire.start.equals(hoveredWire.start)
                && wire.end.equals(hoveredWire.end)) {
                return true;
            }
        }
        return false;
    }

    public void removeWire(WireComponent hoveredWire) {
        for (int i = 0; i < wireList.size(); i++) {
            if (wireList.get(i).start.equals(hoveredWire.start)
                    && wireList.get(i).end.equals(hoveredWire.end)) {
                wireList.remove(i);
                break;
            }
        }
        gridLogicHandler.checkLogic(this);
    }

    public void resetValid() {
        for (GateComponent gate : gateComponentMap.values()) {
            gate.setValid(ValidEnum.NULL);
            gate.input1 = -1;
            gate.input2 = -1;
        }
        for (InputOutputComponent inout : inputOutputComponentMap.values()) {
            inout.setValid(ValidEnum.NULL);
        }
        for (WireComponent wire : wireList) {
            wire.setValid(ValidEnum.NULL);
        }
    }

    public boolean hasInvalidComponent() {
        for (GateComponent gate : gateComponentMap.values()) {
            if (gate.valid == ValidEnum.INVALID) {
                return true;
            }
        }
        for (InputOutputComponent inout : inputOutputComponentMap.values()) {
            if (inout.valid == ValidEnum.INVALID) {
                return true;
            }
        }
        for (WireComponent wire : wireList) {
            if (wire.valid == ValidEnum.INVALID) {
                return true;
            }
        }
        return false;
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

    public GridWrapper exportWrapper() {
        return new GridWrapper(gateComponentMap, inputOutputComponentMap, wireList);
    }

    public void importWrapper(GridWrapper wrapper) {
        LogicGate.setId_count(wrapper.gateIDCount);
        gateComponentMap.clear();
        for (GateComponentWrapper gate : wrapper.gates) {
            gateComponentMap.put(gate.id,
                    new GateComponent(
                            LogicGate.logicGateFactory(gate.gateType,
                                    new Point(gate.centerX, gate.centerY),
                                    gate.id),
                            new Point(gate.centerX, gate.centerY)
                    )
            );
        }

        InputOutputComponent.setId_count(wrapper.inputOutputCount);
        inputOutputComponentMap.clear();
        for (InputOutputWrapper inout : wrapper.inputOutputs) {
            inputOutputComponentMap.put(inout.id,
                    InputOutputComponent.inputOutputFactory(inout.type,
                            new Point(inout.positionX, inout.positionY),
                            inout.enabled, inout.id)
            );
        }

        wireList.clear();
        for (WireWrapper wire : wrapper.wires) {
            wireList.add(new WireComponent(
                    new Point(wire.startX, wire.startY),
                    new Point(wire.endX, wire.endY))
            );
        }
        System.out.println("Completed importing from file");
        gridLogicHandler.checkLogic(this);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = startWidth; x < endWidth; x += gridSize) {
            for (int y = 0; y < height; y += gridSize) {
                g.drawLine(x, 0, x, height);
                g.drawLine(x, y, endWidth, y);
            }
        }
        for (WireComponent wire : wireList) {
            wire.draw(g,
                absolutePoint(wire.start),
                absolutePoint(wire.end),
                Color.BLACK,
                gridSize * 0.2f);
        }
        for (GateComponent component : gateComponentMap.values()) {
            Point drawLocation = absolutePoint(component.gate.getTopLeft());
            Color color = null;
            if (component.isHovered()) {
                color = currentMode == ModeEnum.DELETE_MODE
                        ? Color.RED
                        : Color.LIGHT_GRAY;
            }
            component.draw(g, drawLocation, color);
        }
        for (InputOutputComponent component : inputOutputComponentMap.values()) {
            Point drawLocation = absolutePoint(component.relativePosition);
            Color color = null;
            if (component.isHovered()) {
                color = currentMode == ModeEnum.DELETE_MODE
                        ? Color.RED
                        : Color.LIGHT_GRAY;
            }
            component.draw(g, drawLocation, gridSize);
        }
    }
}
