package org.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import javax.swing.JButton;
import javax.swing.UIManager;
import org.main.myunitracker.Unit;
import org.main.myunitracker.MyUniTracker;

/**
 * The main component of the GUI
 * @author Samuel Heath
 */
public class MyUniTrackerGUI extends JFrame {
    
    final int width,height;
    private JTabbedPane tab;
    private CombinedPanel CP;
    
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
        JPanel p = new JPanel();
        tab = new JTabbedPane();
        
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
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
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
                System.out.println("1");
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
                System.out.println("2");
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
                System.out.println("3");
                unit_name = new JTextField("Unit");
                credit_points = new JTextField();
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