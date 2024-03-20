package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DeleteMode extends MouseMode {
    public DeleteMode(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point mousePosition = e.getPoint();
        if (mousePosition.x < 300) {
            mainPanel.setMode(mainPanel.moveMode);
            return;
        }
        if (!mainPanel.removeWire()) {
            mainPanel.removeComponent(e.getPoint());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePosition = e.getPoint();
        if (!mainPanel.hoverExistingWire(mousePosition)){
            mainPanel.checkComponentHover(mousePosition);
        }
    }

    @Override
    public ModeEnum getMode() {
        return ModeEnum.DELETE_MODE;
    }
}
