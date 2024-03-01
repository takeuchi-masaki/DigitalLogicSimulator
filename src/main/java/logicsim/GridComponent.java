package logicsim;

import logicsim.gates.LogicGate;

import java.awt.*;

public class GridComponent {
    LogicGate gate;

    public GridComponent(LogicGate gate, Point relativePos) {
        this.gate = gate.clone();
        this.gate.setPosition(relativePos);
    }

    public void draw(Graphics2D g, int scale, Point zeroPos) {
        gate.draw(g, scale, zeroPos);
    }

    public int getID() {
        return gate.getID();
    }

    public boolean contains(Point relativePos) {
        Point gatePos = gate.getPos();
        return relativePos.x >= gatePos.x && relativePos.x <= gatePos.x + 2
                && relativePos.y >= gatePos.y && relativePos.y <= gatePos.y + 3;
    }

    public boolean isHovered() { return gate.isHovered(); }

    public void setHovered(boolean val) { gate.setHovered(val); }
}
