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

import java.awt.Color;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.net.URL;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.main.myunitracker.MyUniTracker;

/**
 * The main component of the GUI
 * @author Samuel Heath
 */
public class MyUniTrackerGUI extends JFrame {
    
    final int width,height;
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
        
        //Data set up
        this.width = width;
        this.height = height;
        
        JWindow window = new JWindow();
            File f = new File("resources/splash.gif");
            ImageIcon icon = new ImageIcon(f.getAbsolutePath());
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
            org.main.gui.UnitsPanel up = new org.main.gui.UnitsPanel(MyUniTracker.units.get(i),width,height,CP);
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
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            String LF = UIManager.getLookAndFeel().getName();
            System.out.println(LF);
            if (LF.matches("Nimbus")){
                
            }
            //Mac com.sun.java.swing.plaf.mac.MacLookAndFeel
            //Nimbus com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            //Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            } else if(System.getProperty("os.name").startsWith("Mac")) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            String LF = UIManager.getLookAndFeel().getName();
            System.out.println(LF);
            } else {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            }
        } catch (Exception exc){
            System.out.println(exc.getMessage());
        
        }
    }
}