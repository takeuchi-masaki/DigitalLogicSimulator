package logicsim;

import logicsim.gates.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class PalettePanel extends JPanel {
    int panelWidth = 300;
    final int scale = 50;
    private final List<PaletteComponent> paletteComponents;

    public PalettePanel() {
        setPreferredSize(new Dimension(panelWidth, getHeight()));
        setBackground(new Color(255, 247, 209)); // light yellow
        paletteComponents = getPaletteComponents();
        setupMouseEvents();
    }

    private void setupMouseEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (PaletteComponent component : paletteComponents) {
                    if (component.contains(e.getPoint())) {
                        // TODO: Perform drag-and-drop logic
                        System.out.println("clicked gate " + component.getID());
                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean repaintNeeded = false;
                for (PaletteComponent comp : paletteComponents) {
                    boolean contains = comp.contains(e.getPoint());
                    if (comp.isHovered() != contains) {
                        comp.setHovered(contains);
                        repaintNeeded = true;
                    }
                }
                if (repaintNeeded) {
                    repaint();
                }
            }
        });
    }

    private List<PaletteComponent> getPaletteComponents() {
        final List<PaletteComponent> paletteComponents;
        Point palettePosition = new Point(scale / 2, scale / 2);
        final int width = panelWidth - scale;
        final int height = scale * 4;
        PaletteComponent.setDimensions(width, height);
        paletteComponents = new ArrayList<>();
        paletteComponents.add(
                new PaletteComponent(
                        new ANDGate(),
                        new Point(palettePosition)));
        palettePosition.y += (int) (scale * 5.5);
        // TODO: change gate types to ORGate, XORGate, NOTGate
        paletteComponents.add(
                new PaletteComponent(
                        new ANDGate(),
                        new Point(palettePosition)));
        palettePosition.y += (int) (scale * 5.5);
        paletteComponents.add(
                new PaletteComponent(
                        new ANDGate(),
                        new Point(palettePosition)));
        return paletteComponents;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (PaletteComponent component : paletteComponents) {
            component.draw(g);
        }
    }
}
