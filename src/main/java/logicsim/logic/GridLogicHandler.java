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

    /**
     * check if Gate inputs or outputs are overlapping on same position
     * or InputOutputComponents overlapping on same position
     */
    private boolean checkInvalidPositioning(GridPanel gridPanel) {
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
        if (checkInvalidPositioning(gridPanel)) {
            System.err.println("bad positioning");
            return;
        }

        // saves the previous position of visited positions
        // if the position is an input or gate output,
        // the previous position is the same as the current position
        Map<Point, Point> previous = new HashMap<>();

        Stack<State> stack = new Stack<>();
        // add all inputs to stack
        for (InputOutputComponent inout : gridPanel.inputOutputComponentMap.values()) {
            if (inout.getType() == InputOutputEnum.IN) {
                previous.put(inout.relativePosition, inout.relativePosition);
                stack.add(new State(inout.relativePosition, inout.relativePosition, inout.enabled));
            }
        }
        // run non-recursive DFS to check the logic
        while(!stack.isEmpty()) {
            State curr = stack.pop();
            for (InputOutputComponent inout: gridPanel.inputOutputComponentMap.values()) {
                Point position = inout.relativePosition;
                if (curr.pos.equals(position)) {
                    if (inout.getType() == InputOutputEnum.IN
                        && !curr.pos.equals(curr.prevPos)) {
                        System.err.println("bad overlapping inputs");
                        inout.setValid(ValidEnum.INVALID);
                        return;
                    }
                    // successfully reached an output component
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
                        boolean nextSignal = calcNextSignal(component, curr.signal);
                        stack.add(new State(output, output, nextSignal));
                    } else if (!prev.equals(output)) {
                        System.err.println("bad gate output");
                        component.valid = ValidEnum.INVALID;
                        return;
                    }
                }
            }
            for (WireComponent wire : gridPanel.wireList) {
                Point wireStart = null;
                Point wireEnd = null;
                // check if one end of the wire is the current position
                if (wire.start.equals(curr.pos)) {
                    wireStart = wire.start;
                    wireEnd = wire.end;
                } else if (wire.end.equals(curr.pos)) {
                    wireStart = wire.end;
                    wireEnd = wire.start;
                }
                // if the wire is connected to the current position and
                // the wire end is not the previous position
                if (wireStart != null
                    && !wireEnd.equals(curr.prevPos)) {
                    Point prev = previous.put(wireEnd, curr.pos);
                    if (prev == null) {
                        stack.add(new State(wireEnd, curr.pos, curr.signal));
                    } else if (!prev.equals(curr.pos)) {
                        // the next position already has a different input
                        System.err.println("bad wire");
                        wire.setValid(ValidEnum.INVALID);
                        return;
                    }
                }
            }
        }
    }

    /**
     *  the gate can output a signal if both inputs are found
     *  or if it is possible to greedily determine the output with a single input
     */
    private static boolean canOutput(GateComponent component) {
        return ((component.input1 != -1) && (component.input2 != -1))
                || switch (component.gate.getType()) {
            case AND -> (component.input1 == 0) || (component.input2 == 0);
            case OR -> (component.input1 == 1) || (component.input2 == 1);
            case XOR -> (component.input1 != -1) && (component.input2 != -1);
            case NOT -> (component.input1 != -1) || (component.input2 != -1);
        };
    }

    private static boolean calcNextSignal(GateComponent component, boolean currentSignal) {
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
                case NOT -> !currentSignal;
            };
        }
        return nextSignal;
    }
}
