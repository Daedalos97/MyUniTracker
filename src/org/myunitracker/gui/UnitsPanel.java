/*
 * Copyright (c) 2016, Samuel James Serwan Heath
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.myunitracker.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.myunitracker.main.Unit;

/**
 * @author Samuel Heath
 */
public class UnitsPanel extends JPanel {
    
    public UnitsPanel(Unit unit) {
        this.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR);
        
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        p.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        
        GraphPanel gp = new GraphPanel(unit);
        gp.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbc.insets = new Insets(5,0,5,5);
        gbc.gridheight = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        p.add(gp,gbc);
        
        FormPanel fp = new FormPanel(unit,gp);
        fp.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0,5,10,0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p.add(fp,gbc);
        
        add(p);
        this.setVisible(true);
    }
}