package logicsim.gates;

import java.awt.*;

public class ANDGate extends LogicGate {
    public ANDGate() { super(); }
    public ANDGate(Point position) { super(position); }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) && (input2 ^ input2Not);
    }

    @Override
    public void draw(Graphics g, int gridScale, Point zeroPosition) {
        // Calculate the top-left corner of the AND gate in pixel coordinates
        int x = topLeft.x * gridScale + zeroPosition.x;
        int y = topLeft.y * gridScale + zeroPosition.y;

        g.setColor(hovered ? Color.darkGray : Color.GRAY);
        g.fillRect(x, y, gridScale, 3 * gridScale); // Rectangle takes 1 grid unit width and 3 grid units height
        g.fillArc((int) (x + gridScale / 4.0), y, (int)(gridScale * 1.5), 3 * gridScale, 90, -180);
    }

    @Override
    public void draw_move(Graphics g) {
        int gridScale = 50;
        Point move = topLeft;
        g.setColor(Color.GREEN);
        g.fillRect(move.x, move.y, gridScale, (int)(3 * gridScale));
        g.fillArc((int) (move.x + gridScale / 4.0), move.y, (int) (gridScale * 1.5), 3 * gridScale, 90, -180);
    }

    @Override
    public GateType getType() { return GateType.AND; }

    @Override
    public LogicGate clone() {
         LogicGate deepCopy = new ANDGate(this.topLeft);
         deepCopy.input1Not = this.input1Not;
         deepCopy.input2Not = this.input2Not;
         deepCopy.outputNot = this.outputNot;
         while(deepCopy.getOrientation() != this.orientation) {
             deepCopy.turn();
         }
         return deepCopy;
    }
}
