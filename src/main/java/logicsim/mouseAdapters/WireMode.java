package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WireMode extends MouseAdapter {
    MainPanel mainPanel;

    public WireMode(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mainPanel.hoverWire(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point mousePosition = e.getPoint();
        if (mousePosition.x < 300) {
            mainPanel.setMouseMoveMode();
        } else {
            mainPanel.addWire();
        }
    }
}
