package logicsim.logic;

import logicsim.GridPanel;
import logicsim.gates.ANDGate;
import logicsim.gates.ORGate;
import logicsim.gates.XORGate;
import logicsim.grid.GateComponent;
import logicsim.grid.WireComponent;
import logicsim.inout.InputOutputComponent;
import logicsim.inout.InputOutputEnum;

import java.awt.*;
import java.util.*;

public class GridLogicHandler {
    private static GridLogicHandler INSTANCE = null;

    private GridLogicHandler() {}

    public static GridLogicHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GridLogicHandler();
        }
        return INSTANCE;
    }

    public class State {
        Point pos;
        Point prevPos;
        boolean signal;

        public State(Point pos, Point prevPos, boolean signal) {
            this.pos = pos;
            this.prevPos = prevPos;
            this.signal = signal;
        }
    }

    /*
        check if gate inputs are overlapping on same position
        or inputs/outputs overlapping on same position
     */
    private boolean invalidPositioning(GridPanel gridPanel) {
        Set<Point> positions = new HashSet<>();
        for (InputOutputComponent inout : gridPanel.inputOutputComponentMap.values()) {
            if (!positions.add(inout.relativePosition)) {
                inout.setValid(ValidEnum.INVALID);
            }
        }

        positions.clear();
        for (GateComponent gate : gridPanel.gateComponentMap.values()) {
            Point input1 = gate.getGate().input1();
            Point input2 = gate.getGate().input2();
            if (!positions.add(input1)
            || (input2 != null && !positions.add(input2))) {
                gate.setValid(ValidEnum.INVALID);
            }
        }

        positions.clear();
        for (GateComponent gate : gridPanel.gateComponentMap.values()) {
            Point output = gate.getGate().output();
            if (!positions.add(output)) {
                gate.setValid(ValidEnum.INVALID);
            }
        }
        return gridPanel.hasInvalidComponent();
    }

    public void checkLogic(GridPanel gridPanel) {
        gridPanel.resetValid();
        if (invalidPositioning(gridPanel)) {
            System.err.println("bad positioning");
            return;
        }

        Map<Point, Point> previous = new HashMap<>();

        Queue<State> queue = new ArrayDeque<>();
        for (InputOutputComponent inout : gridPanel.inputOutputComponentMap.values()) {
            if (inout.relativePosition.x < 0 || inout.relativePosition.x >= 150
                || inout.relativePosition.y < 0 || inout.relativePosition.y >= 150) {
                inout.setValid(ValidEnum.INVALID);
                continue;
            }
            if (inout.getType() == InputOutputEnum.OUT) continue;
            previous.put(inout.relativePosition, inout.relativePosition);
            queue.add(new State(inout.relativePosition, inout.relativePosition, inout.enabled));
        }
        while(!queue.isEmpty()) {
            State curr = queue.poll();
            for (InputOutputComponent inout: gridPanel.inputOutputComponentMap.values()) {
                Point position = inout.relativePosition;
                if (curr.pos.equals(position)) {
                    if (inout.getType() == InputOutputEnum.IN
                        && !curr.pos.equals(curr.prevPos)) {
                        System.err.println("bad overlapping inputs");
                        inout.setValid(ValidEnum.INVALID);
                        return;
                    }
                    inout.setEnable(curr.signal);
                    break;
                }
            }
            for (GateComponent component : gridPanel.gateComponentMap.values()) {
                boolean updated = false;
                Point input1 = component.gate.input1();
                if (input1.equals(curr.pos)) {
                    if (component.input1 != -1) {
                        System.err.println("bad gate input1");
                        component.setValid(ValidEnum.INVALID);
                        return;
                    }
                    component.input1 = curr.signal ? 1 : 0;
                    updated = true;
                }
                Point input2 = component.gate.input2();
                if (input2 != null
                    && input2.equals(curr.pos)) {
                    if (component.input2 != -1) {
                        System.err.println("bad gate input2");
                        component.setValid(ValidEnum.INVALID);
                        return;
                    }
                    component.input2 = curr.signal ? 1 : 0;
                    updated = true;
                }
                if (updated) {
                    if (!canOutput(component)) continue;
                    Point output = component.gate.output();
                    Point prev = previous.put(output, output);
                    if (prev == null) {
                        boolean nextSignal = calcNextSignal(component, curr);
                        queue.add(new State(output, output, nextSignal));
                    } else if (!prev.equals(output)) {
                        System.err.println("bad gate output");
                        component.valid = ValidEnum.INVALID;
                        return;
                    }
                }
            }
            for (WireComponent wire : gridPanel.wireList) {
                Point wireStart = null;
                if (wire.start.equals(curr.pos)
                        && !wire.end.equals(curr.prevPos)) {
                    wireStart = wire.start;
                } else if (wire.end.equals(curr.pos)
                        && !wire.start.equals(curr.prevPos)) {
                    wireStart = wire.end;
                }
                if (wireStart != null) {
                    Point prev = previous.put(wireStart, curr.pos);
                    if (prev == null) {
                        queue.add(new State(wireStart, curr.pos, curr.signal));
                    } else if (!prev.equals(curr.pos)) {
                        System.err.println("bad wire");
                        wire.setValid(ValidEnum.INVALID);
                        return;
                    }
                }
            }
        }
    }

    private static boolean canOutput(GateComponent component) {
        return ((component.input1 != -1) && (component.input2 != -1))
                || switch (component.gate.getType()) {
            case AND -> (component.input1 == 0) || (component.input2 == 0);
            case OR -> (component.input1 == 1) || (component.input2 == 1);
            // (component.input1 != -1) && (component.input2 != -1) is trivially false if first condition fails
            case XOR -> false;
            case NOT -> true; // input1 != -1 || input2 != -1 is trivially true
        };
    }

    private static boolean calcNextSignal(GateComponent component, State curr) {
        boolean nextSignal;
        if ((component.input1 != -1) && (component.input2 != -1)) {
            // both inputs found
            nextSignal = switch(component.gate.getType()) {
                case AND -> ANDGate.output(component.input1, component.input2);
                case OR -> ORGate.output(component.input1, component.input2);
                case XOR -> XORGate.output(component.input1, component.input2);
                case NOT -> false; // should not be called
            };
        } else {
            // only single input found, but next signal can be outputted
            nextSignal = switch(component.gate.getType()) {
                case AND -> false;  // must be single False
                case OR -> true;    // must be single True
                case XOR -> false;  // should not be called
                case NOT -> !curr.signal;
            };
        }
        return nextSignal;
    }
}
