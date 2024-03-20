package logicsim.palette;

import logicsim.gates.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PalettePanel {
    private static PalettePanel INSTANCE = null;
    private int width = 300, height = 900;
    final int scale = 50;
    private final List<PaletteComponent> paletteComponents;
    public final PaletteButton moveButton;
    public final PaletteButton wireModeButton;
    public final PaletteButton deleteModeButton;

    private PalettePanel() {
        moveButton = new MoveButton(true, new Point(5, 50), 90, 100);
        wireModeButton = new WireModeButton(false, new Point(105, 50), 90, 100);
        deleteModeButton = new DeleteModeButton(false, new Point(205, 50), 90, 100);
        paletteComponents = new ArrayList<>();
        getPaletteComponents();
    }

    public static PalettePanel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PalettePanel();
        }
        return INSTANCE;
    }

    public void setDimensions(int w, int h) {
        width = w;
        height = h;
    }

    private void getPaletteComponents() {
        Point palettePosition = new Point(scale / 2 + 20, scale / 2 + scale * 3);
        PaletteComponent.setDimensions(this.width - 2 * scale, scale * 3);

        paletteComponents.add(
                new PaletteComponent(
                        new ANDGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
        palettePosition.y += scale * 3 + 10;

        paletteComponents.add(
                new PaletteComponent(
                        new ORGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
        palettePosition.y += scale * 3 + 10;

        paletteComponents.add(
                new PaletteComponent(
                        new XORGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
    }

    public void clearHover() {
        for (PaletteComponent component : paletteComponents) {
            component.setHovered(false);
        }
    }

    public boolean modifyHover(Point mouseLocation) {
        boolean modified = false;
        for (PaletteComponent component : paletteComponents) {
            if (component.contains(mouseLocation) != component.isHovered()) {
                component.setHovered(!component.isHovered());
                modified = true;
            }
        }
        return modified;
    }

    public LogicGate checkHover() {
        for (PaletteComponent component : paletteComponents) {
            if (component.isHovered()) {
                return component.getGate();
            }
        }
        return null;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);
        for (PaletteComponent component : paletteComponents) {
            component.draw(g);
        }
    }
}
