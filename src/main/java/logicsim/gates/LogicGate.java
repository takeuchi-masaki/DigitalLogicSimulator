package logicsim.gates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class LogicGate implements Cloneable {
    private static int id_count = 0;
    private final int id;
    protected boolean input1Not = false, input2Not = false, outputNot = false;
    protected Point topLeft;
    protected Point center;
    protected short orientation = 1;
    protected boolean hovered = false;
    protected static final int defaultScale = 20;
    protected static int gridScale = 20;
    private static List<LogicGate> GATE_TYPES;

    public LogicGate() {
        id = ++id_count;
        center = new Point(0, 0);
        topLeft = new Point(center.x - 2, center.y - 2);
    }
    public LogicGate(Point position) {
        id = ++id_count;
        center = position;
        topLeft = new Point(center.x - 2, center.y - 2);
    }
    public LogicGate(int id, Point position) {
        this.id = id;
        center = position;
        topLeft = new Point(center.x - 2, center.y - 2);
    }

    private static void initGateTypes() {
        if (GATE_TYPES == null) {
            GATE_TYPES = new ArrayList<>();
            GATE_TYPES.add(new ANDGate());
            GATE_TYPES.add(new ORGate());
            GATE_TYPES.add(new XORGate());
        }
    }

    protected static LogicGate logicGateFactory(GateType type, Point position, int id) {
        return switch (type) {
            case GateType.AND -> new ANDGate(id, position);
            case GateType.OR -> new ORGate(id, position);
            case GateType.XOR -> new XORGate(id, position);
        };
    }
    protected static LogicGate logicGateFactory(GateType type, Point position) {
        return switch (type) {
            case GateType.AND -> new ANDGate(position);
            case GateType.OR -> new ORGate(position);
            case GateType.XOR -> new XORGate(position);
        };
    }

    public static List<LogicGate> getTypes() {
        LogicGate.initGateTypes();
        return GATE_TYPES;
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage result = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            int resizeX = defaultScale * 5, resizeY = defaultScale * 5;
            result = new BufferedImage(resizeX, resizeY, original.getType());
            Graphics2D g2d = result.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(original, 0, 0, resizeX, resizeY, null);
            g2d.dispose();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Cannot open " + path);
            exit(1);
        }
        return result;
    }

    protected BufferedImage resizeImage(BufferedImage original) {
        int dim = LogicGate.gridScale * 4;
        BufferedImage resizedImage = new BufferedImage(dim, dim, original.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(original, 0, 0, dim, dim, null);
        g2d.dispose();
        return resizedImage;
    }

    protected void draw(Graphics2D g, Point drawPosition, BufferedImage image) {
        if (isHovered()) {
            g.drawImage(image, drawPosition.x, drawPosition.y, Color.LIGHT_GRAY, null);
        } else {
            g.drawImage(image, drawPosition.x, drawPosition.y, null);
        }
    }

    public void setInput1Not(boolean val) { input1Not = val; }

    public void setInput2Not(boolean val) { input2Not = val; }

    public void setOutputNot(boolean val) { outputNot = val; }

    public void setPosition(Point point) {
        center = point;
        topLeft.x = center.x - 2;
        topLeft.y = center.y - 2;
    }

    public void turn() {
        if (++orientation == 4) {
            orientation = 0;
        }
    }

    public static void resizeScale(int newScale) {
        gridScale = newScale;
    }

    public short getOrientation() { return orientation; }

    public int getID() { return id; }

    public Point getTopLeft(Point absCenterPoint) {
        return new Point(absCenterPoint.x - 2 * gridScale, absCenterPoint.y - 2 * gridScale);
    }

    public Point getTopLeft() { return topLeft; }

    public Point getCenter() { return center; }

    public boolean isHovered() { return hovered; }

    public void setHovered(boolean val) { hovered = val; }

    abstract public boolean output(boolean input1, boolean input2);

    abstract public void resizeImage(int newScale);

    abstract public void draw(Graphics2D g, Point drawPosition);

    abstract public void drawScaled(Graphics2D g, Point drawPosition);

    abstract public GateType getType();

    public LogicGate uniqueCopy() {
        LogicGate copy = logicGateFactory(this.getType(), this.center);
        copy.input1Not = this.input1Not;
        copy.input2Not = this.input2Not;
        copy.outputNot = this.outputNot;
        while(copy.getOrientation() != this.orientation) {
            copy.turn();
        }
        return copy;
    }

    @Override
    public LogicGate clone() {
        LogicGate deepCopy = logicGateFactory(this.getType(), this.center, this.id);
        deepCopy.input1Not = this.input1Not;
        deepCopy.input2Not = this.input2Not;
        deepCopy.outputNot = this.outputNot;
        while(deepCopy.getOrientation() != this.orientation) {
            deepCopy.turn();
        }
        return deepCopy;
    }
}
