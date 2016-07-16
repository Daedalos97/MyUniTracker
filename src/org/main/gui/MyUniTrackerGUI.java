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

package org.main.gui;

import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import java.net.URLEncoder;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;

import org.main.myunitracker.MyUniTracker;

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
    //D9B679, White, 83C0E6, E6A983
    
    /**
     * @param title The name of the window that opens.
     * @param width Width of the canvas.
     * @param height Height of the canvas.
     */
    public MyUniTrackerGUI(String title, int width, int height) {
        //Window Setup
        super(title);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLookFeel();
        setLocationRelativeTo(null);
        WIDTH = width;
        HEIGHT = height;
        
        //Data set up
        
        JWindow window = new JWindow();
            ImageIcon icon = new ImageIcon(MyUniTrackerGUI.class.getResource("images/splash.gif"));
            window.getContentPane().add(
                new JLabel("", icon, SwingConstants.CENTER));
        window.setSize(640, 400);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        this.setIconImage(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MyUniTracker.png")).getImage());
        System.out.println("Worked?");
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
        setVisible(true);
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
        CP = new CombinedPanel();
        for (int i = 0; i < MyUniTracker.units.size(); i++) {
            org.main.gui.UnitsPanel up = new org.main.gui.UnitsPanel(MyUniTracker.units.get(i));
            up.setVisible(true);
            tab.addTab(MyUniTracker.units.get(i).getUnitName(),up);
        }
        tab.add("Overview",CP);
        CP.updateGraph();
        add(tab);
    }
    
    /**
     * Changes the look and feel of the GUI, based on the Operating System.
     */
    private void setLookFeel(){
        try {
            if (System.getProperty("os.name").startsWith("Windows")){
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getLookAndFeel().getName());
            //Mac com.sun.java.swing.plaf.mac.MacLookAndFeel
            //Nimbus com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            //Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            } else if(System.getProperty("os.name").startsWith("Mac")) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "MyUniTracker");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                System.out.println(UIManager.getLookAndFeel().getName());
            } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}