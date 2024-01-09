package gui;

import javax.swing.*;
import java.awt.*;

public class CalculatorPanel extends JPanel {
    private static final String[] BUTTONS = {
            "CLR", "+/-", "%", "/",
            "<-",   "^",  "e", "+",
            "7",    "8",  "9", "-",
            "4",    "5",  "6", "x",
            "1",    "2",  "3", "÷",
            "root",    "0",  ".", "="};
    private static final JTextField displayField = new JTextField();
    private static String displayContents = "";
    private static final JPanel buttonPad = new JPanel();

    public CalculatorPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.add(displayField, BorderLayout.NORTH);
        this.add(buttonPad, BorderLayout.CENTER);
        initDisplay();
        initButtonPad();
        this.setVisible(true);
    }

    private void initDisplay() {
        displayField.setText(displayContents);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBorder(BorderFactory.createBevelBorder(1));
        displayField.setEditable(false);
        displayField.setFont(new Font(displayField.getFont().getName(), displayField.getFont().getStyle(), 18));
        displayField.setPreferredSize(new Dimension(230, 40));
    }

    private void initButtonPad() {
        buttonPad.setLayout(new GridLayout(6, 4));
        buttonPad.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        addButtons();
    }

    private void addButtons() {
        for (String buttonStr : BUTTONS) {
            JButton tempButton = new JButton(buttonStr);
            tempButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            tempButton.setPreferredSize(new Dimension(40, 38));
            tempButton.addActionListener(new CalcButtonListener());
            buttonPad.add(tempButton);
        }
    }

    public void refreshDisplay() {
        displayField.setText(displayContents);
    }

    public String getDisplayContents() {
        return displayContents;
    }

    public void setDisplayContents(String displayString) {
        displayContents = displayString;
    }
}
