package logicsim;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class MenuBar extends JMenuBar {
    GridImporter importer = GridImporter.getInstance();
    GridExporter exporter = GridExporter.getInstance();
    JMenu fileMenu;
    JMenu helpMenu;

    public MenuBar() {
        initFileMenu();
        initHelpMenu();
    }

    private void initFileMenu() {
        fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openItem.addActionListener(e -> openFile());

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveItem.addActionListener(e -> saveFile());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        add(fileMenu);
    }

    private void initHelpMenu() {
        helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        JMenuItem docuItem = new JMenuItem("Documentation");
        docuItem.addActionListener(e -> showDocumentation());

        helpMenu.add(aboutItem);
        helpMenu.add(docuItem);
        add(helpMenu);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open XML File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File xmlFile = fileChooser.getSelectedFile();
        importer.importFile(xmlFile);
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save XML File");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File xmlFile = fileChooser.getSelectedFile();
        if (!xmlFile.getName().toLowerCase().endsWith(".xml")) {
            xmlFile = new File(xmlFile.getParentFile(), xmlFile.getName() + ".xml");
        }
        exporter.exportFile(xmlFile);
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(null,
                """
                        Digital Logic Simulator
                        Version 0.1
                        Created by Masaki Takeuchi and ChatGPT""",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showDocumentation() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html");
        editorPane.setText("<html><body>Click the <a href='https://www.example.com'>https://www.example.com</a> to visit the webpage.</body></html>");
        editorPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    System.err.println("[ERROR] MenuBar::showDocumentation(): Cannot open URL");
                    ex.printStackTrace();
                }
            }
        });
        editorPane.setEditable(false);
        editorPane.setBackground(null);

        JOptionPane.showMessageDialog(null, editorPane);
    }
}
