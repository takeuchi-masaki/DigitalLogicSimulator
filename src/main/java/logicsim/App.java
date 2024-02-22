package logicsim;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    PalettePanel palettePanel;
    GridPanel gridPanel;

    public App() {
        setTitle("Digital Logic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 900));

        palettePanel = new PalettePanel();
        gridPanel = new GridPanel();

        setLayout(new BorderLayout());
        add(palettePanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App logicSimulator = new App();
            logicSimulator.setVisible(true);
        });
    }
}
