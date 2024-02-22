package logicsim.gates;

import java.awt.*;

public class ANDGate extends LogicGate {
    public ANDGate() {
        super();
    }

    public ANDGate(Point position) {
        super(position);
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) && (input2 ^ input2Not);
    }

    @Override
    public void draw(Graphics g, int gridScale, Point zeroPosition) {
        // Calculate the top-left corner of the AND gate in pixel coordinates
        int x = topLeft.x * gridScale + zeroPosition.x;
        int y = topLeft.y * gridScale + zeroPosition.y;

        g.setColor(Color.GRAY);
        g.fillRect(x, y, gridScale, (int) (3 * gridScale)); // Rectangle takes 1 grid unit width and 3 grid units height
        g.fillArc((int) (x + gridScale / 4.0), y, (int) (gridScale * 1.5), 3 * gridScale, 90, -180);
    }
}
