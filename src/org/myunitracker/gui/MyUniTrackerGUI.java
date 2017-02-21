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
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

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
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 14), fontSubTitle = new Font("Arial", Font.BOLD,13), fontText = new Font("Arial", Font.PLAIN,13);
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
        setSize(WIDTH, HEIGHT-100);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        
        //Add Icons
        initialiseIcons();
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                MyUniTracker.writeToFile();
            }
        }
        );
        
        tab = new JTabbedPane();
        this.setLayout(new BorderLayout());
        this.add(tab, BorderLayout.CENTER);
        
        //Sets up the tabs on the tabbed pane.
        initialiseTabs();
        
        this.setResizable(true);
        this.setVisible(true);
    }
    
    /**
     * Initialises the icons the application may use on different systems.
     */
    private void initialiseIcons() {
        List<Image> icons = new ArrayList();
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT512.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT256.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT128.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT64.png")).getImage());
        icons.add(new ImageIcon(MyUniTrackerGUI.class.getResource("images/MUT32.png")).getImage());
        this.setIconImages(icons);
    }
    
    public static JTabbedPane getTabbedPane() {
        return tab;
    }
    
    public static CombinedPanel getCombinedPanel() {
        return CP;
    }
    
    private void initialiseTabs() {
        for (int i = 0; i < tab.getTabCount(); i++)
            tab.removeTabAt(i);
        for (int i = 0; i < MyUniTracker.units.size(); i++) {
            UnitsPanel up = new UnitsPanel(MyUniTracker.units.get(i));
            tab.addTab("<html><p style='padding:4px;'>"+MyUniTracker.units.get(i).getUnitName()+"</p></html>",up);
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