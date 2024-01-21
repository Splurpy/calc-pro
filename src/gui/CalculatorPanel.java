package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;

public class CalculatorPanel extends JPanel {
    private static final String[] BUTTONS = {
            "CLR", "+/-", "%", "/",
            "<-", "^", "e", "+",
            "7", "8", "9", "-",
            "4", "5", "6", "x",
            "1", "2", "3", "÷",
            "root", "0", ".", "="};
    private static final JTextField displayField = new JTextField();
    private static String displayContents = "";
    private static final JPanel buttonPad = new JPanel();

    public CalculatorPanel() {
        super();
        this.setLayout(new BorderLayout());
        displayField.setFocusable(false);
        this.add(displayField, BorderLayout.NORTH);
        this.add(buttonPad, BorderLayout.CENTER);
        initDisplay();
        initButtonPad();
        this.setVisible(true);
    }

    private void setHotKey(JButton button) {
        String buttonStr = button.getText();
        KeyStroke keyStroke;

        switch (buttonStr) {
            case "CLR":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, 0);
                break;
            case "+/-":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_QUOTE, 0);
                break;
            case "%":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_5, InputEvent.SHIFT_DOWN_MASK);
                break;
            case "/":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, InputEvent.SHIFT_DOWN_MASK);
                break;
            case "<-":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
                break;
            case "^":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_6, InputEvent.SHIFT_DOWN_MASK);
                break;
            case "e":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_E, 0);
                break;
            case "+":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK);
                break;
            case "-":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0);
                break;
            case "x":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, 0);
                break;
            case "÷":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0);
                break;
            case "=":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0);
                KeyStroke keyStroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

                button.registerKeyboardAction(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        button.doClick();
                    }
                }, keyStroke2, JComponent.WHEN_IN_FOCUSED_WINDOW);
                break;
            case "root":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, 0);
                break;
            case ".":
                keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0);
                break;
            default:
                keyStroke = KeyStroke.getKeyStroke(buttonStr);
                break;

        }
        button.registerKeyboardAction(new ActionListener() {
                                          @Override
                                          public void actionPerformed(ActionEvent event) {
                                              button.doClick();
                                          }
                                      },
                keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
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
            // CALL SETHOKEY() HERE
            setHotKey(tempButton);
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
