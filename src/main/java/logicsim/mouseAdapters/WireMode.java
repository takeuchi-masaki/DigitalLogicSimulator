package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class WireMode extends MouseMode {
    public WireMode(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mainPanel.hoverWire(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point mousePosition = e.getPoint();
        if (mousePosition.x < 300) {
            mainPanel.setMode(mainPanel.moveMode);
        } else {
            mainPanel.addWire();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }

    @Override
    public ModeEnum getMode() {
        return ModeEnum.WIRE_MODE;
    }
}
