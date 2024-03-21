package logicsim;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tester {
    @Test
    void hashsetFunctionalityTest() {
        Set<Point> pointsSet = new HashSet<>();

        Point p1 = new Point(1, 2);
        pointsSet.add(p1);

        Point p2 = new Point(1, 2);

        assertTrue(pointsSet.contains(p2));
    }
}
