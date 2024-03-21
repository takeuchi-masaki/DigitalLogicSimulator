package logicsim.gates;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 2x2 NOT Gate
 */
public class NOTGate extends LogicGate{
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;
    private static final String imagePath = "/gates/NotGate.png";

    public NOTGate() {
        super();
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 2);
        }
        // since other gates are 4x4, top left is translated by (1, 1)
        topLeft.x++;
        topLeft.y++;
    }

    public NOTGate(Point position) {
        super(position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 2);
        }
        topLeft.x++;
        topLeft.y++;
    }

    public NOTGate(int id, Point position) {
        super(id, position);
        if (image == null) {
            image = loadImage(imagePath);
            scaledImage = resizeImage(image, 2);
        }
        topLeft.x++;
        topLeft.y++;
    }

    @Override
    public Point input1() {
        return new Point(center.x - 1, center.y);
    }

    @Override
    public Point input2() {
        return null;
    }

    @Override
    public Point output() {
        return new Point(center.x + 1, center.y);
    }

    @Override
    public void setPosition(Point point) {
        center = new Point(point);
        topLeft = new Point(center.x - 1, center.y - 1);
    }

    @Override
    public Point getTopLeft(Point absCenterPoint) {
        return new Point(absCenterPoint.x - gridScale, absCenterPoint.y - gridScale);
    }

    @Override
    public boolean contains(Point relativePos) {
        return relativePos.x >= topLeft.x
                && relativePos.x <= topLeft.x + 2
                && relativePos.y >= topLeft.y
                && relativePos.y <= topLeft.y + 2;
    }

    @Override
    public void resizeImage() {
        scaledImage = resizeImage(image, 2);
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
    public GateType getType() {
        return GateType.NOT;
    }

    @Override
    public String toString() {
        return "NOT Gate";
    }
}
