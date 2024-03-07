package logicsim.gates;

import java.awt.*;

public abstract class LogicGate implements Cloneable {
    private static int id_count = 0;
    private final int id;
    protected boolean input1Not = false, input2Not = false, outputNot = false;
    protected Point topLeft;
    protected short orientation = 0;
    protected boolean hovered = false;
    protected int scale;

    public LogicGate() {
        id = id_count++;
        topLeft = new Point(0, 0);
        scale = 20;
    }

    public LogicGate(Point position) {
        id = id_count++;
        topLeft = position;
        scale = 20;
    }

    public static LogicGate logicGateFactory(GateType type, Point position) {
        LogicGate returnValue = null;
        switch (type) {
            case GateType.AND -> returnValue = new ANDGate(position);
        }
        return returnValue;
    }

    public void setInput1Not(boolean val) { input1Not = val; }

    public void setInput2Not(boolean val) { input2Not = val; }

    public void setOutputNot(boolean val) { outputNot = val; }

    public void setPosition(Point point) { topLeft = point; }

    public void turn() {
        if (++orientation == 4) {
            orientation = 0;
        }
    }

    public short getOrientation() { return orientation; }

    public int getID() { return id; }

    public Point getPos() { return topLeft; }

    public boolean isHovered() { return hovered; }

    public void setHovered(boolean val) { hovered = val; }

    public abstract void resizeImage(int newScale);

    abstract public boolean output(boolean input1, boolean input2);

    abstract public void draw(Graphics g, Point drawPosition);
    abstract public void draw_move(Graphics g, Point drawPosition);

    abstract public GateType getType();

    @Override abstract public LogicGate clone();
}
