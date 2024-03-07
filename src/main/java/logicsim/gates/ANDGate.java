package logicsim.gates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public class ANDGate extends LogicGate {
    private static BufferedImage image = null;
    private static BufferedImage scaledImage = null;

    public ANDGate() {
        super();
        if (image == null) {
            loadImage();
            resizeImage(this.scale);
        }
    }
    public ANDGate(Point position) {
        super(position);
        if (image == null) {
            loadImage();
            resizeImage(this.scale);
        }
    }

    private void loadImage() {
        String imagePath = "/logicsim/gates/AndGate.png";
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            int resizeX = scale * 5, resizeY = scale * 5;
            image = new BufferedImage(resizeX, resizeY, original.getType());
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(original, 0, 0, resizeX, resizeY, null);
            g2d.dispose();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Cannot open " + imagePath);
            exit(1);
        }
    }

    @Override
    public void resizeImage(int newScale) {
        this.scale = newScale;
        int dim = this.scale * 4;
        scaledImage = new BufferedImage(dim, dim, image.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(image, 0, 0, dim, dim, null);
        g2D.dispose();
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) && (input2 ^ input2Not);
    }

    @Override
    public void draw(Graphics g, Point drawPosition) {
        g.drawImage(image, drawPosition.x, drawPosition.y, null);
    }

    @Override
    public void draw_move(Graphics g, Point drawPosition) {
        g.drawImage(scaledImage, drawPosition.x, drawPosition.y, null);
    }

    @Override
    public GateType getType() { return GateType.AND; }

    @Override
    public LogicGate clone() {
         LogicGate deepCopy = new ANDGate(this.topLeft);
         deepCopy.input1Not = this.input1Not;
         deepCopy.input2Not = this.input2Not;
         deepCopy.outputNot = this.outputNot;
         while(deepCopy.getOrientation() != this.orientation) {
             deepCopy.turn();
         }
         return deepCopy;
    }
}
