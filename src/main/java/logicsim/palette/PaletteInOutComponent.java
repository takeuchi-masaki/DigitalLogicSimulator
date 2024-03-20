package logicsim.palette;

import logicsim.inout.InputOutputComponent;

import java.awt.*;

public class PaletteInOutComponent {
    static final int scale = 50; // scale does not change for the Palette Panel
    private final InputOutputComponent inout;
    private final Rectangle bounds;
    private static int width;
    private static int height;
    private boolean hover;

    public PaletteInOutComponent(InputOutputComponent inout, Point position) {
        this.inout = inout;
        this.bounds = new Rectangle(position.x, position.y, width, height);
        this.hover = false;
    }

    public void draw(Graphics2D g) {
        // Draw the background
        g.setColor(hover ? Color.LIGHT_GRAY : Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the border
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.drawString(inout.toString(), bounds.x + scale / 2, bounds.y + scale);
        inout.draw(g, new Point(bounds.x + scale, bounds.y + 2 * scale), scale);
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
        PaletteInOutComponent.width = width;
        PaletteInOutComponent.height = height;
    }
}
