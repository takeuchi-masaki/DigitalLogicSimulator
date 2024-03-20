package logicsim.palette;

import logicsim.gates.*;
import logicsim.inout.InputComponent;
import logicsim.inout.OutputComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PalettePanel {
    private static PalettePanel INSTANCE = null;
    private int width = 300, height = 900;
    final int scale = 50;
    private final List<PaletteGateComponent> gateComponents;
    private final List<PaletteInOutComponent> inOutComponents;
    public final PaletteButton moveButton;
    public final PaletteButton wireModeButton;
    public final PaletteButton deleteModeButton;

    private PalettePanel() {
        moveButton = new MoveButton(true, new Point(5, 50), 90, 100);
        wireModeButton = new WireModeButton(false, new Point(105, 50), 90, 100);
        deleteModeButton = new DeleteModeButton(false, new Point(205, 50), 90, 100);
        gateComponents = new ArrayList<>();
        inOutComponents = new ArrayList<>();
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
        PaletteGateComponent.setDimensions(4 * scale + 10, 3 * scale);
        PaletteInOutComponent.setDimensions(2 * scale, 3 * scale);

        gateComponents.add(
                new PaletteGateComponent(
                        new ANDGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
        palettePosition.y += scale * 3 + 10;

        gateComponents.add(
                new PaletteGateComponent(
                        new ORGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
        palettePosition.y += scale * 3 + 10;

        gateComponents.add(
                new PaletteGateComponent(
                        new XORGate(-1, new Point(0, 0)),
                        new Point(palettePosition)));
        palettePosition.y += scale * 3 + 10;

        inOutComponents.add(new PaletteInOutComponent(
                new InputComponent(new Point(palettePosition), true),
                new Point(palettePosition)
        ));
        palettePosition.x += scale * 2 + 10;

        inOutComponents.add(new PaletteInOutComponent(
                new OutputComponent(new Point(palettePosition), false),
                new Point(palettePosition)
        ));
    }

    public void clearHover() {
        for (PaletteGateComponent component : gateComponents) {
            component.setHovered(false);
        }
    }

    public boolean modifyHover(Point mouseLocation) {
        boolean modified = false;
        for (PaletteGateComponent component : gateComponents) {
            if (component.contains(mouseLocation) != component.isHovered()) {
                component.setHovered(!component.isHovered());
                modified = true;
            }
        }
        return modified;
    }

    public LogicGate checkGateHover() {
        for (PaletteGateComponent component : gateComponents) {
            if (component.isHovered()) {
                return component.getGate();
            }
        }
        return null;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);
        for (PaletteGateComponent component : gateComponents) {
            component.draw(g);
        }
        for (PaletteInOutComponent component : inOutComponents) {
            component.draw(g);
        }
    }
}
