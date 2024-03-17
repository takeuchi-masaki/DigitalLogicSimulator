package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveMode extends MouseAdapter {
    MainPanel mainPanel;
    public MoveMode(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mainPanel.checkComponentHover(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mainPanel.selectHoveredComponent(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mainPanel.dragSelectedComponent(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mainPanel.releaseSelectedComponent(e.getPoint());
    }
}
