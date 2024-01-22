package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class CalculatorPanel extends JPanel {
  private static final String[] BUTTONS =
      {"CLR", "+/-", "%", "/", "<-", "^", "e", "+", "7", "8", "9", "-", "4", "5", "6", "x", "1",
          "2", "3", "÷", "root", "0", ".", "="};
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
        KeyStroke keyStroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_8, InputEvent.SHIFT_DOWN_MASK);
        
        button.registerKeyboardAction(new ActionListener() {
          @Override public void actionPerformed(ActionEvent event) {
            button.doClick();
          }
        }, keyStroke2, JComponent.WHEN_IN_FOCUSED_WINDOW);
        break;
      case "÷":
        keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0);
        break;
      case "=":
        keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0);
        keyStroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        
        button.registerKeyboardAction(new ActionListener() {
          @Override public void actionPerformed(ActionEvent event) {
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
      @Override public void actionPerformed(ActionEvent event) {
        button.doClick();
      }
    }, keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
  }
  
  private void initDisplay() {
    displayField.setText(displayContents);
    displayField.setHorizontalAlignment(JTextField.RIGHT);
    displayField.setBorder(BorderFactory.createBevelBorder(1));
    displayField.setEditable(false);
    displayField.setFont(
        new Font(displayField.getFont().getName(), displayField.getFont().getStyle(), 18));
    displayField.setPreferredSize(new Dimension(230, 40));
    // ActionListening beyond this point
    displayField.registerKeyboardAction(new ActionListener() { // Copying from display
                                          @Override public void actionPerformed(ActionEvent event) {
                                            Toolkit.getDefaultToolkit().getSystemClipboard()
                                                .setContents(new StringSelection(displayContents), null);
                                          }
                                        }, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
    
    // Pasting into display
    displayField.registerKeyboardAction(new ActionListener() {
                                          @Override public void actionPerformed(ActionEvent event) {
                                            try {
                                              String clipboard = (String) (Toolkit.getDefaultToolkit().getSystemClipboard()
                                                  .getContents(this)).getTransferData(DataFlavor.stringFlavor);
                                              if (CalcButtonListener.isNumeric(clipboard)) {
                                                displayContents = clipboard;
                                              } else if (clipboard.equals("e")) {
                                                displayContents = String.valueOf(Math.E);
                                              } else {
                                                displayContents = CalcButtonListener.ERROR;
                                              }
          
                                              // When operand2 is null, operator is not null, and operand 1 is not null
                                              if ((CalcButtonListener.getOpd2() == null) && (CalcButtonListener.getOp() != null) && (
                                                  CalcButtonListener.getOpd1() != null)) {
                                                displayContents = clipboard;
                                                CalcButtonListener.setOpd2(clipboard);
                                                System.out.println("[DEBUG] Opd1: " + CalcButtonListener.getOpd1());
                                                System.out.println("[DEBUG] Op: " + CalcButtonListener.getOp());
                                                System.out.println("[DEBUG] Opd2: " + CalcButtonListener.getOpd2());
                                              }
                                            } catch (UnsupportedFlavorException e) {
                                              throw new RuntimeException(e);
                                            } catch (IOException e) {
                                              throw new RuntimeException(e);
                                            }
                                            refreshDisplay();
                                          }
                                        }, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
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
      // Set the hotkeys for each button
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
