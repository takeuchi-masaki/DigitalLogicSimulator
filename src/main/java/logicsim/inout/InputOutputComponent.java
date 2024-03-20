package logicsim.inout;

import java.awt.*;

public abstract class InputOutputComponent {
    public Point position; // relative position
    protected boolean enabled;

    public InputOutputComponent(Point position, boolean enabled) {
        this.position = position;
        this.enabled = enabled;
    }

    public void setEnable(boolean enable) {
        this.enabled = enable;
    }

    abstract public void draw(Graphics2D g, Point position, int gridSize);
}
