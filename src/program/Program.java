package program;

import gui.CalculatorWindow;

import javax.swing.*;

public class Program {
    private static final CalculatorWindow calcWindow = new CalculatorWindow();
    private static final JMenuBar menuBar = new ProgramMenu();
    private static final JFrame programFrame = new JFrame();


    public static void main(String[] args) {
        programFrame.add(calcWindow);
        programFrame.setTitle("CalcPro");
        programFrame.setSize(250, 350);
        programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        programFrame.setResizable(false);
        programFrame.setJMenuBar(menuBar);
        programFrame.setVisible(true);
    }
}
