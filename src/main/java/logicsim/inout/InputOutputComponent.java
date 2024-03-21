package logicsim.inout;

import logicsim.logic.ValidEnum;

import java.awt.*;

public abstract class InputOutputComponent {
    private static int id_count = 0;
    private final int id;
    public Point relativePosition;
    public boolean enabled;
    protected boolean hover = false;
    public ValidEnum valid = ValidEnum.NULL;

    public InputOutputComponent(Point relativePosition, boolean enabled) {
        id = ++id_count;
        this.relativePosition = relativePosition;
        this.enabled = enabled;
    }

    InputOutputComponent(Point relativePosition, boolean enabled, int id) {
        this.id = id;
        this.relativePosition = relativePosition;
        this.enabled = enabled;
    }

    public static InputOutputComponent inputOutputFactory(InputOutputEnum inputOutputEnum, Point position, boolean enabled) {
        return switch(inputOutputEnum) {
            case IN -> new InputComponent(position, enabled);
            case OUT -> new OutputComponent(position, enabled);
        };
    }

    public static InputOutputComponent inputOutputFactory(InputOutputEnum inputOutputEnum, Point position, boolean enabled, int id) {
        return switch(inputOutputEnum) {
            case IN -> new InputComponent(position, enabled, id);
            case OUT -> new OutputComponent(position, enabled, id);
        };
    }

    public void setValid(ValidEnum valid) { this.valid = valid; }

    public static int getId_count() { return id_count; }

    public static void setId_count(int cnt) { id_count = cnt; }

    public void setEnable(boolean enable) { this.enabled = enable; }

    public void setRelativePosition(Point point) { relativePosition = point; }

    public int getId() { return id; }

    public boolean isHovered() { return hover; }

    public void setHover(boolean isHovered) { this.hover = isHovered; }

    abstract public void draw(Graphics2D g, Point position, int gridSize);

    abstract public InputOutputEnum getType();

    public InputOutputComponent uniqueCopy() {
        return inputOutputFactory(this.getType(), this.relativePosition, this.enabled);
    }

    @Override
    public InputOutputComponent clone() {
        return inputOutputFactory(this.getType(), this.relativePosition, this.enabled, this.id);
    }
}
