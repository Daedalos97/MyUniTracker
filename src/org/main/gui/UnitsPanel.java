package org.main.gui;

import java.awt.Color;
import javax.swing.BoxLayout;
import org.main.myunitracker.Unit;
import javax.swing.JPanel;

/**
 * @author Samuel Heath
 */
public class UnitsPanel extends JPanel {
    
    private GraphPanel gp;
    private CombinedPanel cp;
    
    /*
    Make it so that the Form Panel has a table of the assessments, as well as
    giving statistics such as your current mark and grade.
    Allow users to add assessments and to edit them.
    */
    public UnitsPanel(int index, int WIDTH, int HEIGHT, CombinedPanel combinedPanel) {
        this.setSize(WIDTH, HEIGHT);
        //setLayout(new GridBagLayout());
        gp = new GraphPanel(index);
        this.cp = combinedPanel;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        FormPanel fp = new FormPanel(index,gp,cp);
        p.add(gp);
        p.add(fp);
        add(p);
        this.setVisible(true);
    }
}