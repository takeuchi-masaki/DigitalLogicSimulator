package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ANDGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/logicsim/gates/AndGate.png";

    public ANDGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }
    public ANDGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }
    public ANDGate(int id, Point position) {
        super(id, position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image);
        }
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) & (input2 ^ input2Not);
    }

    @Override
    public void draw(Graphics2D g, Point drawPosition) {
        draw(g, drawPosition, image);
    }

    @Override
    public void drawScaled(Graphics2D g, Point drawPosition) {
        draw(g, drawPosition, scaledImage);
    }

    @Override
    public void resizeImage(int newScale) {
        scaledImage = resizeImage(image);
    }

    @Override
    public GateType getType() { return GateType.AND; }
}
