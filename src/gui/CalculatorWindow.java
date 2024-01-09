package gui;

import javax.swing.*;

public class CalculatorWindow extends JPanel {
    private static final JPanel calcPanel = new CalculatorPanel();


    public CalculatorWindow() {
        this.add(calcPanel);
        this.setVisible(true);
    }
}
