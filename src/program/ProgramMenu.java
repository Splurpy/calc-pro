package program;

import javax.swing.*;

public class ProgramMenu extends JMenuBar {
    private static final JMenu toolMenu = new JMenu();
    private static final JMenu prefMenu = new JMenu();
    public ProgramMenu () {
        super();
        initMenus();
        // this.add(toolMenu);
        this.setBorder(BorderFactory.createBevelBorder(2));
        this.add(prefMenu);
    }

    private void initMenus() {
        // toolMenu.setText("Tools");
        // toolMenu.setToolTipText("Various Tools/Formulas");
        prefMenu.setText("Preferences");
        prefMenu.setToolTipText("Personal Preferences/Options");
    }
}
