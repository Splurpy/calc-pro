package gui;

import calculator.Operator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static calculator.Operator.BADOP;
import static calculator.Operator.strTop;

public class CalcButtonListener implements ActionListener {
    public static final String ERROR = "ERROR";
    private static String operand1, operand2;
    private static Operator op;
    private static boolean displayIsOp = false;

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            innerActionPerformed(event);
        } catch (Throwable thrown) {
            // Handle saving to log files if wanted
            thrown.printStackTrace();
            CalculatorWindow.calcPanel.setDisplayContents(ERROR);
        }
    }
    
    public void innerActionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        CalculatorPanel calcPanel = ((CalculatorPanel) ((JButton) event.getSource()).getParent().getParent());

        if (displayIsOp) {
            calcPanel.setDisplayContents("");
            calcPanel.refreshDisplay();
            displayIsOp = false;
        }

        if (isNumeric(command)) addNum(command, calcPanel);
        if (command == "CLR") clear(calcPanel);
        if (command == "<-") del(calcPanel);
        if (command == "+/-") flipSign(calcPanel);
        if (command == "e") {
            addE(calcPanel);
            return;
        }

        // Command must be an operator
        if (!isNumeric(command) && command != "CLR" && command != "<-" && command != "+/-") {
            if (command != "=") {
                op = strTop(command);
                displayIsOp = true;
            }
            // System.out.println("[DEBUG] OP -> " + op.toString());
            if (op != null && op.isUnary()) {
                System.out.println("[DEBUG] OP is Unary");
                calcPanel.setDisplayContents(op.operate(Double.parseDouble(calcPanel.getDisplayContents()), 0.0));
            } else {
                if (operand1 != null && command == "=") {
                    if (operand2 == null) operand2 = calcPanel.getDisplayContents();
                    String result;
                    try {
                        result = op.operate(Double.parseDouble(operand1), Double.parseDouble(operand2));
                    } catch (NumberFormatException nfe) {
                        result = "ERROR";
                    }
                    if (result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.') result = result.substring(0, result.length() - 2);
                    System.out.println("[DEBUG] " + operand1 + " " + op.toString() + " " + operand2 + " = " + result);
                    calcPanel.setDisplayContents(result);
                    calcPanel.refreshDisplay();
                    operand2 = null;
                } else {
                    operand1 = calcPanel.getDisplayContents();
                    calcPanel.setDisplayContents(op.toString());
                    calcPanel.refreshDisplay();
                }

            }
            if (operand1 == "e") operand1 = String.valueOf(Math.E);
            if (operand2 == "e") operand2 = String.valueOf(Math.E);
        }
    }

    private void addNum(String command, CalculatorPanel calcPanel) {
        if (calcPanel.getDisplayContents().length() == 22) {
            calcPanel.setDisplayContents(ERROR);
        } else {
            calcPanel.setDisplayContents(calcPanel.getDisplayContents() + command);
        }
        calcPanel.refreshDisplay();
    }

    private void clear(CalculatorPanel calcPanel) {
        calcPanel.setDisplayContents("");
        calcPanel.refreshDisplay();
        operand1 = null;
        operand2 = null;
        op = null;
        displayIsOp = false;
    }

    private void del(CalculatorPanel calcPanel) {
        String displayContents = calcPanel.getDisplayContents();
        if (displayContents != null && displayContents != ERROR && displayContents.length() != 0) {
            calcPanel.setDisplayContents(displayContents.substring(0, displayContents.length() - 1));
        }
        calcPanel.refreshDisplay();
    }

    private void flipSign(CalculatorPanel calcPanel) {
        String displayContents = calcPanel.getDisplayContents();
        if (displayContents != null && displayContents != ERROR && displayContents.length() != 0) {
            if (displayContents.charAt(0) == '-') {
                calcPanel.setDisplayContents(displayContents.substring(1));
            } else {
                calcPanel.setDisplayContents("-" + displayContents);
            }
        }
        calcPanel.refreshDisplay();
    }

    private void addE(CalculatorPanel calcPanel) {
        String displayContents = calcPanel.getDisplayContents();
        if (displayContents != null && displayContents != ERROR) {
            if ((displayContents + Math.E).length() >= 22) {
                calcPanel.setDisplayContents(displayContents + "e");
            } else {
                calcPanel.setDisplayContents(displayContents + Math.E);
            }
        }
        calcPanel.refreshDisplay();
    }

    public static boolean isNumeric(String string) {
        if (string == null) return false;
        if (string == ".") return true;
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }
    
    public static final String getOpd1() {
        return operand1;
    }
    
    public static final String getOpd2() {
        return operand2;
    }
    
    public static final Operator getOp() {
        return op;
    }
    
    public static final void setOpd2(String operand) {
        operand2 = operand;
    }
}
