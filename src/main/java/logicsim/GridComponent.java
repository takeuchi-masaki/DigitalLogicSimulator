package logicsim;

import logicsim.gates.LogicGate;

import java.awt.*;

public class GridComponent {
    LogicGate gate;

    public GridComponent(LogicGate gate, Point relativePos) {
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
        Point gatePos = gate.getPos();
        return relativePos.x >= gatePos.x - 2
                && relativePos.x <= gatePos.x + 2
                && relativePos.y >= gatePos.y - 2
                && relativePos.y <= gatePos.y + 2;
    }

    public boolean isHovered() { return gate.isHovered(); }

    public void setHovered(boolean val) { gate.setHovered(val); }

    public void draw(Graphics2D g, Point drawLocation) {
        gate.drawScaled(g, drawLocation);
    }

    public LogicGate getGate() {
        return gate;
    }
}
