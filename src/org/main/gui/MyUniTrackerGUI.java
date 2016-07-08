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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import org.main.myunitracker.Unit;
import java.awt.event.WindowAdapter;

import java.net.URL;
import java.net.MalformedURLException;

import org.main.myunitracker.MyUniTracker;

/**
 * The main component of the GUI
 * @author Samuel Heath
 */
public class MyUniTrackerGUI extends JFrame {
    
    final int width,height;
    private JTabbedPane tab;
    private CombinedPanel CP;
    public static final Color BACKGROUND_COLOUR = Color.decode("#83C0E6");
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
        try {
            ImageIcon icon = new ImageIcon(new URL("https://raw.githubusercontent.com/Daedalos97/MyUniTracker/master/src/images/splash.gif"));
            window.getContentPane().add(
                new JLabel("", icon, SwingConstants.CENTER));
        } catch (MalformedURLException ME) {
            System.out.println("Error");
        }
        window.setSize(640, 400);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        
        JPanel p = new JPanel();
        tab = new JTabbedPane();
        
        UIManager.put("MenuBar.background", BACKGROUND_COLOUR);
        
        JMenuBar menu_bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem mi0 = new JMenuItem("Change Unit");
        
        mi0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Dialog d = new Dialog(tab.getTabCount()-1);                                                                                                      
            }
        });
        
        menu.add(mi0);
        menu_bar.add(menu);
        
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                MyUniTracker.writeToFile();
            }
        }
        );
        
        init();
        this.setJMenuBar(menu_bar);
        add(tab);
        this.setResizable(true);
        setVisible(true);
    }
    
    private void init() {
        for (int i = 0; i < tab.getTabCount(); i++)
            tab.removeTabAt(i);
        CP = new CombinedPanel(MyUniTracker.calculateGPA(),MyUniTracker.expectedGPA());
        for (int i = 0; i < MyUniTracker.units.size(); i++) {
            org.main.gui.UnitsPanel up = new org.main.gui.UnitsPanel(i,width,height,CP);
            up.setVisible(true);
            tab.addTab(MyUniTracker.units.get(i).getName(),up);
        }
        
        tab.add("Summary",CP);
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
            //System.out.println(LF);
            if (LF.matches("Nimbus")){
                
            }
            //Mac com.sun.java.swing.plaf.mac.MacLookAndFeel
            //Nimbus com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            //Windows com.sun.java.swing.plaf.windows.WindowsLookAndFeel
            } else if(System.getProperty("os.name").startsWith("Mac")) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            String LF = UIManager.getLookAndFeel().getName();
            //System.out.println(LF);
            } else {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            }
        } catch (Exception exc){
            System.out.println(exc.getMessage());
        
        }
    }

    private class Dialog extends JFrame {
        
        private int index = -1;
        
        public Dialog(int tabIndex) {
            super("New Unit");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(280,150);
            setLocationRelativeTo(null);
            this.index = tabIndex;
            initialise();
            setVisible(true);
        }
        
        private void initialise() {
            JPanel pane = new JPanel();
            
            JLabel name = new JLabel("Unit Name:");
            final JTextField unit_name;
            JLabel credit_pts = new JLabel("Number of Credit Points:");
            final JTextField credit_points;
            JButton add_button = new JButton("Add Unit");
            JButton remove_button = new JButton("Remove Unit");
            
            if (index < 0) {
                unit_name = new JTextField("Unit Name");
                credit_points = new JTextField("Credit Points");
                remove_button.setEnabled(false);
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!unit_name.getText().equals("Unit Name") && !credit_points.getText().equals("Credit Points")) {
                            Unit u = new Unit(unit_name.getText(), (int) Integer.parseInt(credit_points.getText()));
                            MyUniTracker.units.add(u);
                            UnitsPanel up = new org.main.gui.UnitsPanel(MyUniTracker.units.size()-1,width,height,CP);
                            up.setVisible(true);
                            tab.insertTab(u.getName(),null,up,null,tab.getTabCount()-1);
                            close();
                        }
                    }
                });
            } else if (index < MyUniTracker.units.size()) {
                unit_name = new JTextField(tab.getTitleAt(tab.getSelectedIndex()));
                credit_points = new JTextField(String.valueOf(MyUniTracker.units.get(tab.getSelectedIndex()).getCreditPoints()));
                remove_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        tab.removeTabAt(tab.getSelectedIndex());
                        MyUniTracker.units.remove(tab.getSelectedIndex());
                        close();
                    }
                });
                
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!unit_name.getText().equals("Unit Name") && !credit_points.getText().equals("Credit Points")) {
                            Unit u = new Unit(unit_name.getText(), (int) Integer.parseInt(credit_points.getText()));
                            MyUniTracker.units.add(u);
                            UnitsPanel up = new org.main.gui.UnitsPanel(MyUniTracker.units.size()-1,width,height,CP);
                            up.setVisible(true);
                            tab.insertTab(u.getName(),null,up,null,tab.getTabCount()-1);
                            close();
                        }
                    }
                });
            } else {
                unit_name = new JTextField("Unit Name");
                credit_points = new JTextField("Credit Points");
                remove_button.setEnabled(false);
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!unit_name.getText().equals("Unit Name") && !credit_points.getText().equals("Credit Points")) {
                            Unit u = new Unit(unit_name.getText(), (int) Integer.parseInt(credit_points.getText()));
                            MyUniTracker.units.add(u);
                            UnitsPanel up = new org.main.gui.UnitsPanel(MyUniTracker.units.size()-1,width,height,CP);
                            up.setVisible(true);
                            tab.insertTab(u.getName(),null,up,null,tab.getTabCount()-1);
                            close();
                        }
                    }
                });
            }
                
            this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                }
            });
            
            pane.add(name);
            pane.add(unit_name);
            pane.add(credit_pts);
            pane.add(credit_points);
            pane.add(add_button);
            pane.add(remove_button);
            add(pane);
        }
        
        private void close() { this.setVisible(false); this.dispose();}
    }
}