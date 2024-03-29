package logicsim.palette;

import logicsim.gates.LogicGate;

import java.awt.*;

public class PaletteGateComponent {
    static final int scale = 50; // scale does not change for the Palette Panel
    private final LogicGate logicGate;
    private final Rectangle bounds;
    private static int width, height;
    private boolean hover;

    public PaletteGateComponent(LogicGate logicGate, Point position) {
        this.logicGate = logicGate;
        this.bounds = new Rectangle(position.x, position.y, width, height);
        this.hover = false;
    }

    public void draw(Graphics2D g) {
        // Draw the background of the PaletteComponent
        g.setColor(hover ? Color.LIGHT_GRAY : Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the border of the PaletteComponent
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.drawString(logicGate.toString(), bounds.x + 10, bounds.y + 50);

        // Draw the gate preview
        Color color = logicGate.isHovered()
                ? Color.LIGHT_GRAY
                : null;
        logicGate.drawPalette(g, new Point(bounds.x + scale * 2, bounds.y + 10), color);
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

    public static void setDimensions(int width, int height) {
        PaletteGateComponent.width = width;
        PaletteGateComponent.height = height;
    }

    public LogicGate getGate() {
        return logicGate;
    }
}
