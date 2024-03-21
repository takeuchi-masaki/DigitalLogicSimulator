package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ANDGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/gates/AndGate.png";

    public ANDGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }

    public ANDGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }

    public ANDGate(int id, Point position) {
        super(id, position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 4);
        }
    }

    public static boolean output(int input1, int input2) {
        assert(input1 == 0 || input1 == 1);
        assert(input2 == 0 || input2 == 1);
        return (input1 & input2) == 1;
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
    public GateType getType() { return GateType.AND; }

    @Override
    public String toString() {
        return "AND Gate";
    }
}
