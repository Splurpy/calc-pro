package program;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import gui.CalculatorPanel;
import gui.CalculatorWindow;

public class ProgramMenu extends JMenuBar {
    private static final JMenu optMenu = new JMenu();
    public ProgramMenu () {
        super();
        initMenus();
        this.add(optMenu);
        this.setBorder(BorderFactory.createBevelBorder(2));
    }

    private void initMenus() {
        optMenu.setText("Program");
        optMenu.setToolTipText("Program Shortcuts & Documentation");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        
        copyItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                CalculatorWindow.calcPanel.copyDisplay();
            }
        });
        
        pasteItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                CalculatorWindow.calcPanel.pasteDisplay();
            }
        });
        
        optMenu.add(copyItem);
        optMenu.add(pasteItem);
    }
}
