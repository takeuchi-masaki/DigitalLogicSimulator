package logicsim;

import logicsim.gates.ANDGate;
import logicsim.gates.GateType;
import logicsim.gates.ORGate;
import logicsim.gates.XORGate;
import logicsim.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PalettePanel {
    private int width, height;
    final int scale = 50;
    private final List<PaletteComponent> paletteComponents;

    public PalettePanel(int width, int height) {
        this.width = width;
        this.height = height;
        paletteComponents = new ArrayList<>();
        getPaletteComponents();
    }

    public void setDimensions(int w, int h) {
        width = w;
        height = h;
    }

    private void getPaletteComponents() {
        Point palettePosition = new Point(scale / 2, scale / 2);
        PaletteComponent.setDimensions(this.width - scale, scale * 4);

        paletteComponents.add(
                new PaletteComponent(
                        new ANDGate(),
                        new Point(palettePosition)));
        palettePosition.y += (int) (scale * 5.5);

        paletteComponents.add(
                new PaletteComponent(
                        new ORGate(),
                        new Point(palettePosition)));
        palettePosition.y += (int) (scale * 5.5);

        paletteComponents.add(
                new PaletteComponent(
                        new XORGate(),
                        new Point(palettePosition)));
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

    public Pair<GateType, Integer> checkHover() {
        for (PaletteComponent component : paletteComponents) {
            if (component.isHovered()) return new Pair<>(component.getType(), -1);
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
