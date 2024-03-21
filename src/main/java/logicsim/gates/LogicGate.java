package logicsim.gates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public abstract class LogicGate implements Cloneable {
    private static int id_count = 0;
    private int id;
    protected boolean input1Not = false, input2Not = false, outputNot = false;
    protected Point topLeft;
    protected Point center;
    protected boolean hovered = false;
    protected static final int defaultScale = 25;
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

    LogicGate(int id, Point position) {
        this.id = id;
        center = position;
        topLeft = new Point(center.x - 2, center.y - 2);
    }

    private static void initGateTypes() {
        if (GATE_TYPES == null) {
            GATE_TYPES = new ArrayList<>();
            for (GateType type : GateType.values()) {
                GATE_TYPES.add(logicGateFactory(type, new Point(0, 0), -1));
            }
        }
    }

    public static List<LogicGate> getTypes() {
        LogicGate.initGateTypes();
        return GATE_TYPES;
    }

    public static LogicGate logicGateFactory(GateType type, Point position, int id) {
        return switch (type) {
            case GateType.AND -> new ANDGate(id, position);
            case GateType.OR -> new ORGate(id, position);
            case GateType.XOR -> new XORGate(id, position);
        };
    }

    public static LogicGate logicGateFactory(GateType type, Point position) {
        return switch (type) {
            case GateType.AND -> new ANDGate(position);
            case GateType.OR -> new ORGate(position);
            case GateType.XOR -> new XORGate(position);
        };
    }

    protected BufferedImage loadImage(String path) {
        BufferedImage result = null;
        try {
            BufferedImage original = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            int resizeX = defaultScale * 4, resizeY = defaultScale * 4;
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
        int dim = gridScale * 4;
        BufferedImage resizedImage = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(original, 0, 0, dim, dim, null);
        g2d.dispose();
        return resizedImage;
    }

    protected void drawScaled(Graphics2D g, Point drawPosition, BufferedImage image, Color color) {
        g.drawImage(image, drawPosition.x, drawPosition.y, color, null);
    }

    public void setInput1Not(boolean val) { input1Not = val; }

    public void setInput2Not(boolean val) { input2Not = val; }

    public void setOutputNot(boolean val) { outputNot = val; }

    public void setPosition(Point point) {
        center = point;
        topLeft.x = center.x - 2;
        topLeft.y = center.y - 2;
    }

    public static void resizeScale(int newScale) {
        gridScale = newScale;
    }

    public int getID() { return id; }

    public Point getTopLeft(Point absCenterPoint) {
        return new Point(absCenterPoint.x - 2 * gridScale, absCenterPoint.y - 2 * gridScale);
    }

    public static int getId_count() {
        return id_count;
    }

    public static void setId_count(int cnt) {
        id_count = cnt;
    }

    public Point getTopLeft() { return topLeft; }

    public Point getCenter() { return center; }

    public boolean isHovered() { return hovered; }

    public void setHovered(boolean val) { hovered = val; }

    abstract public boolean output(boolean input1, boolean input2);

    abstract public void resizeImage();

    abstract public void drawPalette(Graphics2D g, Point drawPosition, Color color);

    abstract public void drawScaled(Graphics2D g, Point drawPosition, Color color);

    abstract public GateType getType();

    public LogicGate uniqueCopy() {
        LogicGate copy = logicGateFactory(this.getType(), this.center);
        copy.input1Not = this.input1Not;
        copy.input2Not = this.input2Not;
        copy.outputNot = this.outputNot;
        return copy;
    }

    @Override
    public LogicGate clone() {
        LogicGate deepCopy = logicGateFactory(this.getType(), this.center, this.id);
        deepCopy.input1Not = this.input1Not;
        deepCopy.input2Not = this.input2Not;
        deepCopy.outputNot = this.outputNot;
        return deepCopy;
    }

    @Override
    public String toString() {
        return null;
    }
}
