package logicsim.importexport;

import logicsim.gates.LogicGate;
import logicsim.grid.GateComponent;
import logicsim.grid.WireComponent;
import logicsim.inout.InputOutputComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Wrapper class for the grid, used for saving and loading xml file
 */
public class GridWrapper implements Serializable {
    public int gateIDCount, inputOutputCount;
    public ArrayList<GateComponentWrapper> gates;
    public ArrayList<InputOutputWrapper> inputOutputs;
    public ArrayList<WireWrapper> wires;

    public GridWrapper(){}

    public GridWrapper(Map<Integer, GateComponent> gateComponentMap,
                       Map<Integer, InputOutputComponent> inputOutputComponentMap,
                       ArrayList<WireComponent> wireList
    ) {
        gateIDCount = LogicGate.getId_count();
        inputOutputCount = InputOutputComponent.getId_count();

        gates = new ArrayList<>();
        for (GateComponent gate : gateComponentMap.values()) {
            gates.add(new GateComponentWrapper(
                    gate.gate.getType(),
                    gate.getID(),
                    gate.gate.getCenter().x, gate.gate.getCenter().y)
            );
        }

        inputOutputs = new ArrayList<>();
        for (InputOutputComponent inout : inputOutputComponentMap.values()) {
            inputOutputs.add(new InputOutputWrapper(
                    inout.getType(),
                    inout.getId(),
                    inout.relativePosition.x, inout.relativePosition.y,
                    inout.enabled)
            );
        }

        this.wires = new ArrayList<>();
        for (WireComponent wire : wireList) {
            this.wires.add(new WireWrapper(
                    wire.start.x, wire.start.y,
                    wire.end.x, wire.end.y)
            );
        }
    }
}
