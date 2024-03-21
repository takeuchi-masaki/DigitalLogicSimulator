package logicsim.grid;

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
        Point gatePos = gate.getTopLeft();
        return relativePos.x >= gatePos.x
                && relativePos.x <= gatePos.x + 4
                && relativePos.y >= gatePos.y
                && relativePos.y <= gatePos.y + 4;
    }

    public boolean isHovered() { return gate.isHovered(); }

    public void setHover(boolean val) { gate.setHovered(val); }

    public void draw(Graphics2D g, Point drawLocation, Color color) {
        gate.drawScaled(g, drawLocation, color);
    }

    public LogicGate getGate() {
        return gate;
    }
}
