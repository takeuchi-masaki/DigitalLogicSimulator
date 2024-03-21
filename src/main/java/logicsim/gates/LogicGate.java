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
    protected Point topLeft;
    protected Point center;
    protected boolean hovered = false;
    protected static final int defaultScale = 20;
    protected static int gridScale = 20;
    private static List<LogicGate> GATE_TYPES;

    public LogicGate() {
        id = ++id_count;
        setPosition(new Point(0, 0));
    }

    public LogicGate(Point position) {
        id = ++id_count;
        setPosition(position);
    }

    /**
     *  Used to create a deep copy without incrementing the id_count
     */
    LogicGate(int id, Point position) {
        this.id = id;
        setPosition(position);
    }

    private static void initGateTypes() {
        GATE_TYPES = new ArrayList<>();
        for (GateType type : GateType.values()) {
            GATE_TYPES.add(logicGateFactory(type, new Point(0, 0), -1));
        }
    }

    /**
     * Allows for interating over each type of gate
     */
    public static List<LogicGate> getTypes() {
        if (GATE_TYPES == null){
            LogicGate.initGateTypes();
        }
        return GATE_TYPES;
    }

    public static LogicGate logicGateFactory(GateType type, Point position, int id) {
        return switch (type) {
            case GateType.AND -> new ANDGate(id, position);
            case GateType.OR -> new ORGate(id, position);
            case GateType.XOR -> new XORGate(id, position);
            case GateType.NOT -> new NOTGate(id, position);
        };
    }

    public static LogicGate logicGateFactory(GateType type, Point position) {
        return switch (type) {
            case GateType.AND -> new ANDGate(position);
            case GateType.OR -> new ORGate(position);
            case GateType.XOR -> new XORGate(position);
            case GateType.NOT -> new NOTGate(position);
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

    protected BufferedImage resizeImage(BufferedImage original, int gateSize) {
        int dim = gridScale * gateSize;
        BufferedImage resizedImage = new BufferedImage(dim, dim, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(original, 0, 0, dim, dim, null);
        g2d.dispose();
        return resizedImage;
    }

    protected void draw(Graphics2D g, Point drawPosition, BufferedImage image, Color color) {
        g.drawImage(image, drawPosition.x, drawPosition.y, color, null);
    }

    public void setPosition(Point point) {
        center = new Point(point);
        // topleft shifted -2, -2 since LogicGate is 4x4
        topLeft = new Point(center.x - 2, center.y - 2);
    }

    public static void resizeScale(int newScale) {
        gridScale = newScale;
    }

    public int getID() { return id; }

    public Point getTopLeft(Point absCenterPoint) {
        return new Point(absCenterPoint.x - 2 * gridScale, absCenterPoint.y - 2 * gridScale);
    }

    public boolean contains(Point relativePos) {
        return relativePos.x >= topLeft.x
            && relativePos.x <= topLeft.x + 4
            && relativePos.y >= topLeft.y
            && relativePos.y <= topLeft.y + 4;
    }

    public Point input1() {
        return new Point(topLeft.x, topLeft.y + 1);
    }

    public Point input2() {
        return new Point(topLeft.x, topLeft.y + 3);
    }

    public Point output() {
        return new Point(center.x + 2, center.y);
    }

    public static int getId_count() { return id_count; }

    public static void setId_count(int cnt) { id_count = cnt; }

    public Point getTopLeft() { return topLeft; }

    public Point getCenter() { return center; }

    public boolean isHovered() { return hovered; }

    public void setHovered(boolean val) { hovered = val; }

    abstract public void resizeImage();

    abstract public void drawPalette(Graphics2D g, Point drawPosition, Color color);

    abstract public void draw(Graphics2D g, Point drawPosition, Color color);

    abstract public GateType getType();

    public LogicGate uniqueCopy() {
        return logicGateFactory(this.getType(), this.center);
    }

    @Override
    public LogicGate clone() {
        return logicGateFactory(this.getType(), this.center, this.id);
    }

    @Override
    public String toString() {
        return null;
    }
}
