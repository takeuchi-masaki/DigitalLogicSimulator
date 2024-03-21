package logicsim.importexport;

import logicsim.gates.GateType;

import java.io.Serializable;

public class GateComponentWrapper implements Serializable {
    public GateType gateType;
    public int id, centerX, centerY;

    public GateComponentWrapper() {
    }

    public GateComponentWrapper(GateType type, int id, int centerX, int centerY) {
        this.gateType = type;
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
    }
}
