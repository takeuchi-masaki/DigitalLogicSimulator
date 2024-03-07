package logicsim.gates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class LogicGate implements Cloneable {
    private static int id_count = 0;
    private final int id;
    protected boolean input1Not = false, input2Not = false, outputNot = false;
    protected Point topLeft;
    protected short orientation = 0;
    protected boolean hovered = false;
    protected int scale;

    public LogicGate() {
        id = -1;
        topLeft = new Point(0, 0);
        scale = 20;
    }
    public LogicGate(Point position) {
        id = ++id_count;
        topLeft = position;
        scale = 20;
    }
    public static LogicGate logicGateFactory(GateType type, Point position) {
        LogicGate returnValue = null;
        switch (type) {
            case GateType.AND -> returnValue = new ANDGate(position);
            case GateType.OR -> returnValue = new ORGate(position);
            case GateType.XOR -> returnValue = new XORGate(position);
        }
        return returnValue;
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage result = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            int resizeX = scale * 5, resizeY = scale * 5;
            result = new BufferedImage(resizeX, resizeY, original.getType());
            Graphics2D g2d = result.createGraphics();
            g2d.drawImage(original, 0, 0, resizeX, resizeY, null);
            g2d.dispose();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Cannot open " + path);
            exit(1);
        }
        return result;
    }

    protected BufferedImage resizeImage(int newScale, BufferedImage original) {
        this.scale = newScale;
        int dim = this.scale * 4;
        BufferedImage resizedImage = new BufferedImage(dim, dim, original.getType());
        Graphics2D g2D = resizedImage.createGraphics();
        g2D.drawImage(original, 0, 0, dim, dim, null);
        g2D.dispose();
        return resizedImage;
    }

    public void setInput1Not(boolean val) { input1Not = val; }

    public void setInput2Not(boolean val) { input2Not = val; }

    public void setOutputNot(boolean val) { outputNot = val; }

    public void setPosition(Point point) { topLeft = point; }

    public void turn() {
        if (++orientation == 4) {
            orientation = 0;
        }
    }

    public short getOrientation() { return orientation; }

    public int getID() { return id; }

    public Point getPos() { return topLeft; }

    public boolean isHovered() { return hovered; }

    public void setHovered(boolean val) { hovered = val; }

    abstract public boolean output(boolean input1, boolean input2);

    protected void draw(Graphics g, Point drawPosition, BufferedImage image) {
        g.drawImage(image, drawPosition.x, drawPosition.y, null);
    }

    abstract public void resizeImage(int newScale);

    abstract public void draw(Graphics g, Point drawPosition);

    abstract public void drawScaled(Graphics g, Point drawPosition);

    abstract public GateType getType();

    @Override
    public LogicGate clone() {
        LogicGate deepCopy = logicGateFactory(this.getType(), this.topLeft);
        deepCopy.input1Not = this.input1Not;
        deepCopy.input2Not = this.input2Not;
        deepCopy.outputNot = this.outputNot;
        while(deepCopy.getOrientation() != this.orientation) {
            deepCopy.turn();
        }
        return deepCopy;
    }
}
