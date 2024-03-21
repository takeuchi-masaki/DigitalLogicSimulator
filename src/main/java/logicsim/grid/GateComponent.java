package logicsim.grid;

import logicsim.gates.LogicGate;
import logicsim.logic.ValidEnum;

import java.awt.*;

public class GateComponent {
    public LogicGate gate;
    public ValidEnum valid = ValidEnum.NULL;
    public int input1 = -1, input2 = -1;

    public GateComponent(LogicGate gate, Point relativePos) {
        if (gate.getID() == -1) {
            this.gate = gate.uniqueCopy();
        } else {
            this.gate = gate.clone();
        }
        this.gate.setPosition(relativePos);
    }

    public int getID() {
        return gate.getID();
    }

    public boolean contains(Point relativePos) {
        return gate.contains(relativePos);
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

    public boolean isHovered() { return gate.isHovered(); }

    public void setHover(boolean val) { gate.setHovered(val); }

    public void draw(Graphics2D g, Point drawLocation, Color color) {
        if (valid == ValidEnum.INVALID) {
            color = Color.RED;
        }
        gate.draw(g, drawLocation, color);
    }

    public LogicGate getGate() {
        return gate;
    }
}
