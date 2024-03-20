package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class XORGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/gates/XorGate.png";

    public XORGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }
    public XORGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }
    public XORGate(int id, Point position) {
        super(id, position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) ^ (input2 ^ input2Not);
    }

    @Override
    public void drawPalette(Graphics2D g, Point drawPosition, Color color) {
        drawScaled(g, drawPosition, image, color);
    }

    @Override
    public void drawScaled(Graphics2D g, Point drawPosition, Color color) {
        drawScaled(g, drawPosition, scaledImage, color);
    }

    @Override
    public void resizeImage() {
        scaledImage = resizeImage(image);
    }

    @Override
    public GateType getType() { return GateType.XOR; }

    @Override
    public String toString() {
        return "XOR Gate";
    }
}
