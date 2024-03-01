package logicsim;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar {
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;

    public MenuBar() {
        initFileMenu();
        initMenuActions();
    }

    private void initFileMenu() {
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        add(fileMenu);
    }

    private void initMenuActions() {
        // TODO: add listener to menu
    }
}
