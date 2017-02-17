/*
 * Copyright 2017 Samuel James Serwan Heath.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myunitracker.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

//MyUniTracker Import
import org.myunitracker.main.Unit;


/**
 * @author Samuel Heath
 */
public class UnitsPanel extends JPanel {
    
    public UnitsPanel(Unit unit) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(MyUniTrackerGUI.BACKGROUND_COLOUR, 5));
        
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        GraphPanel gp = new GraphPanel(unit);
        gp.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbc.insets = new Insets(5,0,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.9;
        p.add(gp,gbc);
        
        FormPanel fp = new FormPanel(unit,gp);
        fp.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,5,10,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        p.add(fp,gbc);
        
        add(p);
        this.setVisible(true);
    }
}