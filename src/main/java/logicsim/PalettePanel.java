package logicsim;

import logicsim.gates.*;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PalettePanel extends JPanel {
    int panelWidth = 300;
    final int scale = 50;
    private final List<PaletteComponent> paletteComponents;
    private LogicGate selected;

    public PalettePanel() {
        setPreferredSize(new Dimension(panelWidth, getHeight()));
        setBackground(new Color(255, 247, 209)); // light yellow
        paletteComponents = getPaletteComponents();
        setupMouseEvents();
    }

    private void setupMouseEvents() {
        TransferHandler handler = new TransferHandler() {
            @Override
            protected Transferable createTransferable(JComponent c) {
                return new TransferableLogicGate(selected);
            }
            @Override
            public int getSourceActions(JComponent c) {
                return COPY_OR_MOVE;
            }
        };
        setTransferHandler(handler);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean repaintNeeded = false;
                for (PaletteComponent comp : paletteComponents) {
                    boolean contains = comp.contains(e.getPoint());
                    if (comp.isHovered() != contains) {
                        comp.setHovered(contains);
                        repaintNeeded = true;
                        e.getComponent().setCursor(
                                contains ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
                    }
                }
                if (repaintNeeded) {
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                for (PaletteComponent comp : paletteComponents) {
                    if (comp.contains(e.getPoint())) {
                        switch(comp.getType()) {
                            case GateType.AND -> selected = new ANDGate();
                        }
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selected == null) return;
                selected.setPosition(e.getPoint());
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selected == null) return;

                selected = null;
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
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
        if (selected != null) {
            selected.draw_move(g);
        }
    }
}
