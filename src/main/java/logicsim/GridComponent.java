package logicsim;

import logicsim.gates.LogicGate;

import java.awt.*;

public class GridComponent {
    LogicGate gate;

    public GridComponent(LogicGate gate, Point relativePos) {
        this.gate = gate.clone();
        this.gate.setPosition(relativePos);
    }

    public int getID() {
        return gate.getID();
    }

    public boolean contains(Point relativePos) {
        Point gatePos = gate.getPos();
        return relativePos.x >= gatePos.x && relativePos.x <= gatePos.x + 3
                && relativePos.y >= gatePos.y && relativePos.y <= gatePos.y + 3;
    }

    public boolean isHovered() { return gate.isHovered(); }

    public void setHovered(boolean val) { gate.setHovered(val); }

    public Image getImage() {
        return gate.getImage();
    }
}
