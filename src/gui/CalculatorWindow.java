package gui;

import javax.swing.*;

public class CalculatorWindow extends JPanel {
    public static final CalculatorPanel calcPanel = new CalculatorPanel();


    public CalculatorWindow() {
        this.add(calcPanel);
        this.setVisible(true);
    }
    
    public CalculatorPanel getMainPanel() {
        return calcPanel;
    }
}
