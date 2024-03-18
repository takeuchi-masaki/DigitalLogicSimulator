package logicsim.palette;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class PaletteButton {
    protected boolean hover = false;
    protected boolean enabled;
    protected Rectangle bounds;
    protected int width, height;
    protected Point position;

    public PaletteButton(boolean enable, Point position, int maxX, int maxY) {
        this.enabled = enable;
        this.position = position;
        this.width = maxX;
        this.height = maxY;
        bounds = new Rectangle(position.x, position.y, width, height);
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage result = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            int resizeX = 50, resizeY = 50;
            result = new BufferedImage(resizeX, resizeY, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = result.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(original, 0, 0, resizeX, resizeY, null);
            g2d.dispose();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Cannot open " + path);
            exit(1);
        }
        return result;
    }

    void draw(Graphics2D g) {
        g.setColor(hover ? Color.LIGHT_GRAY : Color.WHITE);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    void setHover(boolean hovering) {
        hover = hovering;
    }

    void setEnabled(boolean enable) {
        enabled = enable;
    }

    boolean contains(Point mousePosition) {
        return bounds.contains(mousePosition);
    }
}
