package logicsim.grid;

import logicsim.logic.ValidEnum;

import java.awt.*;

public class WireComponent {
    public Point start, end;
    public ValidEnum valid = ValidEnum.NULL;

    public WireComponent(Point start, Point end) {
        // sort the wire ends by lowest x, then lowest y
        boolean startLower;
        if (start.x == end.x) {
            startLower = start.y < end.y;
        } else {
            startLower = start.x < end.x;
        }
        if (startLower) {
            this.start = start;
            this.end = end;
        } else {
            this.start = end;
            this.end = start;
        }
    }

    public void setValid(ValidEnum valid) {
        this.valid = valid;
    }

    /**
        Draw to absolute points
     */
    public void draw(Graphics2D g, Point startStroke, Point endStroke, Color color, float strokeWidth) {
        if (valid == ValidEnum.INVALID) {
            color = Color.RED;
        }
        Stroke originalStroke = g.getStroke();
        Color originalColor = g.getColor();

        g.setStroke(new BasicStroke(strokeWidth));
        g.setColor(color);
        g.drawLine(startStroke.x, startStroke.y, endStroke.x, endStroke.y);

        g.setStroke(originalStroke);
        g.setColor(originalColor);
    }

    @Override
    public String toString() {
        return "{ [" + start.x + ", " + start.y + "], [" + end.x + ", " + end.y + "] }";
    }
}
