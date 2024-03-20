package logicsim.inout;

import java.awt.*;

// right click in move mode to enable/disable
public class OutputComponent extends InputOutputComponent {
    public OutputComponent(Point position, boolean enabled) {
        super(position, enabled);
    }

    @Override
    public void draw(Graphics2D g, Point absolutePosition, int gridSize) {
        Color color = enabled ? Color.GREEN : Color.BLACK;
        g.setColor(color);
        g.fillOval(absolutePosition.x - gridSize / 2, absolutePosition.y - gridSize / 2, gridSize, gridSize);
        g.setColor(Color.BLACK);
        g.drawOval(absolutePosition.x - gridSize / 2, absolutePosition.y - gridSize / 2, gridSize, gridSize);
        
        String str = enabled ? "1" : "0";
        Color textcolor = enabled ? Color.BLACK : Color.WHITE;
        g.setColor(textcolor);
        g.drawString(str, absolutePosition.x - gridSize / 5, absolutePosition.y + gridSize / 5);
    }

    @Override
    public String toString() {
        return "Output";
    }
}
