package logicsim.grid;

import logicsim.gates.LogicGate;

import java.awt.*;

public class GateComponent {
    public LogicGate gate;

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

    public boolean isHovered() { return gate.isHovered(); }

    public void setHover(boolean val) { gate.setHovered(val); }

    public void draw(Graphics2D g, Point drawLocation, Color color) {
        gate.draw(g, drawLocation, color);
    }

    public LogicGate getGate() {
        return gate;
    }
}
