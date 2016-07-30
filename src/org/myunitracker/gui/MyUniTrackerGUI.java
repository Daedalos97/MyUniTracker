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
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;

import org.myunitracker.main.MyUniTracker;

/**
 * The main component of the GUI
 * @author Samuel Heath
 */
public class MyUniTrackerGUI extends JFrame {
    
    private static int WIDTH,HEIGHT;
    private static JTabbedPane tab;
    private static CombinedPanel CP;
    public static final Color BACKGROUND_COLOUR = Color.decode("#83C0E6");
    public static final Color BACKGROUND_COLOUR01 = Color.decode("#eeeeee");
    public static final Font fontTitle = new Font("Arial", Font.BOLD, 14), fontSubTitle = new Font("Arial", Font.BOLD,13), fontText = new Font("Arial", Font.PLAIN,13);
    //D9B679, White, 83C0E6, E6A983
    
    /**
     * @param title The name of the window that opens.
     * @param width Width of the canvas.
     * @param height Height of the canvas.
     */
    public MyUniTrackerGUI(String title, int width, int height) {
        //Window Setup
        super(title);
        
        WIDTH = width;
        HEIGHT = height;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLookFeel();
        if (System.getProperty("os.name").startsWith("Mac")) {
            this.setSize(WIDTH+17,HEIGHT);
        } else {
            this.setSize(WIDTH, HEIGHT);
        }
        this.setLocationRelativeTo(null);
        
        //Data set up
        List<Image> icons = new ArrayList();
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT512.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT256.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT128.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT64.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT32.png")).getImage());
        this.setIconImages(icons);
        
        tab = new JTabbedPane();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                MyUniTracker.writeToFile();
            }
        }
        );
        
        init();
        
        this.add(tab);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    public static JTabbedPane getTabbedPane() {
        return tab;
    }
    
    public static CombinedPanel getCombinedPanel() {
        return CP;
    }
    
    private void init() {
        for (int i = 0; i < tab.getTabCount(); i++)
            tab.removeTabAt(i);
        for (int i = 0; i < MyUniTracker.units.size(); i++) {
            UnitsPanel up = new UnitsPanel(MyUniTracker.units.get(i));
            tab.addTab(MyUniTracker.units.get(i).getUnitName(),up);
        }
        CP = new CombinedPanel();
        tab.add("Overview",CP);
        
    }
    
    /**
     * Changes the look and feel of the GUI, based on the Operating System.
     */
    private void setLookFeel() {
        try {
            if (System.getProperty("os.name").startsWith("Windows")){
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal");
            System.out.println(UIManager.getLookAndFeel().getName());
            //Mac com.sun.java.swing.plaf.mac.MacLookAndFeel
            //Nimbus com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            //Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            } else if(System.getProperty("os.name").startsWith("Mac")) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MyUniTracker");
                UIManager.setLookAndFeel(UIManager.getLookAndFeel().getName());
                System.out.println(UIManager.getLookAndFeel().getName());
            } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
        } catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}