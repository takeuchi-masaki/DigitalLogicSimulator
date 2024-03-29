package logicsim.palette;

import logicsim.mouseAdapters.ModeEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DeleteModeButton extends PaletteButton {
    private final static String imagePath = "/palette/redx.png";

    public DeleteModeButton(boolean enable, Point position, int width, int height) {
        super(enable, position, width, height);
        BufferedImage image = super.loadImage(imagePath, 30, 30);
        ImageIcon icon = new ImageIcon(image);
        this.setIcon(icon);
        this.setText("Delete");
    }

    @Override
    public ModeEnum getMode() {
        return ModeEnum.DELETE_MODE;
    }
}
