package logicsim.gates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public class ANDGate extends LogicGate {
    private static BufferedImage image = null;

    private void loadImage() {
        String imagePath = "/logicsim/gates/AndGate.png";
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            int resizeX = 150, resizeY = 150;
            image = new BufferedImage(resizeX, resizeY, original.getType());
            Graphics2D g2d = image.createGraphics();
            g2d.drawImage(original, 0, 0, resizeX, resizeY, null);
            g2d.dispose();
        } catch (IOException e) {
            System.err.println("Cannot open " + imagePath);
            exit(1);
        }
    }
    public ANDGate() {
        super();
        if (image == null) {
            loadImage();
        }
    }
    public ANDGate(Point position) {
        super(position);
        if (image == null) {
            loadImage();
        }
    }

    @Override
    public boolean output(boolean input1, boolean input2) {
        return (input1 ^ input1Not) && (input2 ^ input2Not);
    }

    @Override
    public Image getImage() {
        return image;
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
