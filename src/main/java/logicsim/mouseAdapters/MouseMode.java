package logicsim.mouseAdapters;

import logicsim.MainPanel;

import java.awt.event.MouseAdapter;

public abstract class MouseMode extends MouseAdapter {
    protected MainPanel mainPanel;

    public MouseMode(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public abstract ModeEnum getMode();
}
