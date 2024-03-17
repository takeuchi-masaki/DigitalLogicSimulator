//package logicsim;
//
//import java.awt.*;
//
//public class WireComponent {
//    Point start, end;
//
//    public WireComponent(Point start, Point end) {
//        this.start = start;
//        this.end = end;
//    }
//
//    void draw(Graphics2D g) {
//        // Save the original state
//        Stroke originalStroke = g.getStroke();
//        Color originalColor = g.getColor();
//
//        // Set custom properties
//        g.setStroke(new BasicStroke(2f)); // Set a custom stroke
//        g.setColor(Color.RED); // Set a custom color
//
//        // Perform custom drawing
//        g.drawLine(start.x, start.y, end.x, end.y);
//
//        // Restore original state
//        g.setStroke(originalStroke);
//        g.setColor(originalColor);
//    }
//}
