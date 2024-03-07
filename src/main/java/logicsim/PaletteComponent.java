package logicsim;

import logicsim.gates.GateType;
import logicsim.gates.LogicGate;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaletteComponent {
    static final int scale = 50; // scale does not change for the Palette Panel
    private final LogicGate logicGate;
    private final Rectangle bounds;
    private static int width;
    private static int height;
    private boolean hover;

    public PaletteComponent(LogicGate logicGate, Point position) {
        this.logicGate = logicGate;
        this.bounds = new Rectangle(position.x, position.y, width, height);
        this.hover = false;
    }

    public void draw(Graphics g) {
        g.setColor(hover ? new Color(220, 220, 220) : Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height); // Draw the background
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height); // Draw the border
        logicGate.draw(g, new Point(bounds.x + (int) (scale * 1.5), bounds.y + scale / 2));
    }

    public void setHovered(boolean isHovered) {
        this.hover = isHovered;
    }

    public boolean isHovered() {
        return this.hover;
    }

    public boolean contains(Point p) {
        return bounds.contains(p);
    }

    public String getID() {
        return logicGate.toString();
    }

    public static void setDimensions(int width, int height) {
        PaletteComponent.width = width;
        PaletteComponent.height = height;
    }

    public GateType getType() {
        return logicGate.getType();
    }
}
