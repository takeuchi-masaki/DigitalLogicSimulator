package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ORGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/gates/OrGate.png";

    public ORGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }
    public ORGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }
    public ORGate(int id, Point position) {
        super(id, position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }

    public static boolean output(boolean input1, boolean input2) {
        return input1 | input2;
    }

    @Override
    public void drawPalette(Graphics2D g, Point drawPosition, Color color) {
        draw(g, drawPosition, image, color);
    }

    @Override
    public void draw(Graphics2D g, Point drawPosition, Color color) {
        draw(g, drawPosition, scaledImage, color);
    }

    @Override
    public void resizeImage() {
        scaledImage = resizeImage(image, 4);
    }

    @Override
    public GateType getType() { return GateType.OR; }

    @Override
    public String toString() {
        return "OR Gate";
    }
}
