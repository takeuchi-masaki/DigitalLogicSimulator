package logicsim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JTextField textField;
    private JButton addButton, subtractButton, multiplyButton, divideButton;

    public App() {
        setTitle("Simple Calculator");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create text field
        textField = new JTextField(10);
        textField.setEditable(true);

        // Create buttons
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("/");

        // Add action listeners to buttons
        addButton.addActionListener(new OperationListener());
        subtractButton.addActionListener(new OperationListener());
        multiplyButton.addActionListener(new OperationListener());
        divideButton.addActionListener(new OperationListener());

        // Create panel and add components
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(addButton);
        panel.add(subtractButton);
        panel.add(multiplyButton);
        panel.add(divideButton);

        // Add panel to content pane
        getContentPane().add(panel);

        setVisible(true);
    }

    private class OperationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String operation = button.getText();
            // Perform operation based on button text
            // (Implementation omitted for brevity)
            System.out.println(operation);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}