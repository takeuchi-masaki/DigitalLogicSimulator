package logicsim;

import javax.swing.*;

public class App extends JFrame {
    public App() {
        setTitle("Digital Logic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setJMenuBar(new MenuBar());
        add(new MainPanel());
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App logicSimulator = new App();
            logicSimulator.setVisible(true);
        });
    }
}
