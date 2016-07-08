/*
 * The MIT License
 *
 * Copyright 2016 Samuel James Serwan Heath.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.main.gui;

import javax.swing.BoxLayout;
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
        this.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR);
        
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