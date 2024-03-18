package logicsim.palette;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class PaletteButton extends JToggleButton {
    int width, height;

    public PaletteButton(boolean enable, Point position, int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setSelected(enable);
        setBorderPainted(false);
        setOpaque(true);
        this.setBounds(position.x, position.y, width, height);
    }

    protected BufferedImage loadImage(String path, int resizeX, int resizeY) {
        BufferedImage result = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
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
}