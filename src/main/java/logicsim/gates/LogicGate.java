package logicsim.gates;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

public abstract class LogicGate {
    public static final DataFlavor LOGIC_GATE_FLAVOR = new DataFlavor(LogicGate.class, "Logic Gate");
    static int id_count = 0;
    int id;
    boolean input1Not = false, input2Not = false, outputNot = false;
    Point topLeft; // relative grid position
    int orientation = 0;

    public LogicGate() {
        id = id_count++;
        topLeft = new Point(0, 0);
    }

    public LogicGate(Point position) {
        id = id_count++;
        topLeft = position;
    }

    public void setInput1Not(boolean val) {
        input1Not = val;
    }

    public void setInput2Not(boolean val) {
        input2Not = val;
    }

    public void setOutputNot(boolean val) {
        outputNot = val;
    }

    public void setPosition(Point point) {
        topLeft = point;
    }

    public void turn() {
        orientation = (orientation + 1) % 4;
    }

    abstract public boolean output(boolean input1, boolean input2);

    abstract public void draw(Graphics g, int gridScale, Point zeroPosition);

    abstract public void draw_move(Graphics g);

    abstract public GateType getType();

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
