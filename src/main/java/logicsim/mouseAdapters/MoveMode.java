package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.event.MouseEvent;

public class MoveMode extends MouseMode {
    public MoveMode(MainPanel mainPanel) {
        super(mainPanel);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mainPanel.checkHover(e.getPoint());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mainPanel.selectHovered();
        if (e.getButton() == MouseEvent.BUTTON1) {
            // left click
            mainPanel.dragSelectedComponent(e.getPoint());
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            // right click, toggle input
            mainPanel.toggleSelectedInput();
            // mainPanel.checkHover(e.getPoint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mainPanel.dragSelectedComponent(e.getPoint());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mainPanel.releaseSelectedComponent(e.getPoint());
    }

    @Override
    public ModeEnum getMode() {
        return ModeEnum.MOVE_MODE;
    }
}
