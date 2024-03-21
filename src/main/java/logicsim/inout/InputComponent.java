package logicsim.inout;

import logicsim.logic.ValidEnum;

import java.awt.*;

public class InputComponent extends InputOutputComponent {
    public InputComponent(Point position, boolean enabled) {
        super(position, enabled);
    }

    public InputComponent(Point position, boolean enabled, int id) {
        super(position, enabled, id);
    }

    @Override
    public InputOutputEnum getType() {
        return InputOutputEnum.IN;
    }

    @Override
    public void draw(Graphics2D g, Point absolutePosition, int gridSize) {
        Color color;
        if (valid == ValidEnum.INVALID) {
            color = Color.RED;
        } else {
            if (hover) {
                color = enabled ? new Color(48,104,68) : Color.DARK_GRAY;
            } else {
                color = enabled ? Color.GREEN : Color.BLACK;
            }
        }
        g.setColor(color);
        g.fillRect(absolutePosition.x - gridSize / 2, absolutePosition.y - gridSize / 2, gridSize, gridSize);
        g.setColor(Color.BLACK);
        g.drawRect(absolutePosition.x - gridSize / 2, absolutePosition.y - gridSize / 2, gridSize, gridSize);

        String str = enabled ? "1" : "0";
        Color textcolor = enabled ? Color.BLACK : Color.WHITE;
        g.setColor(textcolor);
        g.drawString(str, absolutePosition.x - gridSize / 5, absolutePosition.y + gridSize / 5);
    }

    @Override
    public String toString() {
        return "Input";
    }
}
