package logicsim.palette;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WireModeButton extends PaletteButton {
    private final static String imagePath = "/palette/plus.png";

    public WireModeButton(boolean enable, Point position, int width, int height) {
        super(enable, position, width, height);
        this.setBounds(position.x, position.y, width, height);
        BufferedImage image = super.loadImage(imagePath, 30, 50);
        ImageIcon icon = new ImageIcon(image);
        this.setIcon(icon);
        this.setText("Wire");
    }
}