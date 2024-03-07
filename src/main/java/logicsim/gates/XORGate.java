package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

public class XORGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/logicsim/gates/XorGate.png";

    public XORGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(this.scale, image);
        }
    }
    public XORGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(this.scale, image);
        }
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) ^ (input2 ^ input2Not);
    }

    @Override
    public void draw(Graphics g, Point drawPosition) {
        draw(g, drawPosition, image);
    }

    @Override
    public void drawScaled(Graphics g, Point drawPosition) {
        draw(g, drawPosition, scaledImage);
    }

    @Override
    public void resizeImage(int newScale) {
        scaledImage = resizeImage(newScale, image);
    }

    @Override
    public GateType getType() { return GateType.XOR; }
}
