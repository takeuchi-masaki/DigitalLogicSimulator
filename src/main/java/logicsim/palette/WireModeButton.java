package logicsim.palette;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WireModeButton extends PaletteButton {
    private static BufferedImage image = null;
    private final static String imagePath = "/palette/plus.png";

    public WireModeButton(boolean enable, Point position, int width, int height) {
        super(enable, position, width, height);
        if (image == null) {
            loadImage();
        }
    }

    private void loadImage() {
        image = super.loadImage(imagePath);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawImage(image, position.x + 20, position.y + 20, null);
    }
}
