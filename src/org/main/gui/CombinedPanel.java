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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import javafx.scene.chart.CategoryAxis;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.main.myunitracker.Unit;
import javafx.scene.chart.XYChart;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.main.myunitracker.MyUniTracker;
import java.awt.event.WindowAdapter;

/*
int gridx,int gridy,int gridwidth,int gridheight,double weightx, double weighty,
int anchor,int fill,Insets insets,int ipadx,int ipady
*/

/**
 * @author Sam
 */
public class CombinedPanel extends JPanel {
    
    private LineChart<String,Number> lineChart;
    private ArrayList<XYChart.Series> data;
    private JLabel cur_wam,expect_wam,cur_gpa,expect_gpa;
    private JTabbedPane tab;
    
    public CombinedPanel() {
        this.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR);
        
        tab = MyUniTrackerGUI.getTabbedPane();
        
        JPanel combinedGraphPanel = new JPanel();
        combinedGraphPanel.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        combinedGraphPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,0,0);
        
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBackground(java.awt.Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        combinedGraphPanel.add(statsPanel,gbc);
        
        JPanel unitPanel = new JPanel(new GridBagLayout());
        unitPanel.setBackground(java.awt.Color.WHITE);
        unitPanel.setBorder(BorderFactory.createTitledBorder("Add Units and Results"));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        combinedGraphPanel.add(unitPanel,gbc);
        
        final JFXPanel fxPanel = new JFXPanel();
        JPanel graphPanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridheight = 4;
        gbc.gridx = 1;
        gbc.gridy = 0;
        fxPanel.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        graphPanel.add(fxPanel);
        combinedGraphPanel.add(graphPanel,gbc);
        
        GridBagConstraints gbcStats = new GridBagConstraints();
        gbcStats.insets = new Insets(3,3,3,3);
        
        /*
         * Sub Panel of Stats For WAM
         */
        JPanel wamPanel = new JPanel(new GridBagLayout());
        wamPanel.setBackground(java.awt.Color.WHITE);
        wamPanel.setBorder(BorderFactory.createTitledBorder("Weighted Average Mark"));
        gbcStats.gridx = 0;
        gbcStats.gridy = 0;
        statsPanel.add(wamPanel,gbcStats);
        
        GridBagConstraints gbcWAM = new GridBagConstraints();
        gbcWAM.insets = new Insets(3,3,3,3);
        
        JLabel wamLabel = new JLabel("Current WAM: ");
        gbcWAM.gridx = 0;
        gbcWAM.gridy = 0;
        wamPanel.add(wamLabel,gbcWAM);
        cur_wam = new JLabel(String.valueOf(MyUniTracker.calculateWAM()));
        gbcWAM.gridx = 1;
        gbcWAM.gridy = 0;
        wamPanel.add(cur_wam,gbcWAM);
        JLabel expect_wamLabel = new JLabel("Expected WAM (using current units):");
        gbcWAM.gridx = 0;
        gbcWAM.gridy = 1;
        wamPanel.add(expect_wamLabel,gbcWAM);
        expect_wam = new JLabel(String.valueOf(MyUniTracker.expectedWAM()));
        gbcWAM.gridx = 1;
        gbcWAM.gridy = 1;
        wamPanel.add(expect_wam,gbcWAM);
        
        /*
         * Sub Panel of Stats For GPA
         */
        
        JPanel gpaPanel = new JPanel(new GridBagLayout());
        gpaPanel.setBackground(java.awt.Color.WHITE);
        gpaPanel.setBorder(BorderFactory.createTitledBorder("Grade Point Average"));
        gbcStats.fill = GridBagConstraints.HORIZONTAL;
        gbcStats.gridx = 0;
        gbcStats.gridy = 1;
        statsPanel.add(gpaPanel,gbcStats);
        
        GridBagConstraints gbcGPA = new GridBagConstraints();
        gbcGPA.insets = new Insets(3,3,3,3);
        
        JLabel gpaLabel = new JLabel("Current GPA: ");
        gbcGPA.gridx = 0;
        gbcGPA.gridy = 0;
        gpaPanel.add(gpaLabel,gbcGPA);
        cur_gpa = new JLabel(String.valueOf(MyUniTracker.calculateGPA()));
        gbcGPA.gridx = 1;
        gbcGPA.gridy = 0;
        gpaPanel.add(cur_gpa,gbcGPA);
        JLabel expect_gpaLabel = new JLabel("Expected GPA (using current units):");
        gbcGPA.gridx = 0;
        gbcGPA.gridy = 1;
        gpaPanel.add(expect_gpaLabel,gbcGPA);
        expect_gpa = new JLabel(String.valueOf(MyUniTracker.expectedGPA()));
        gbcGPA.gridx = 1;
        gbcGPA.gridy = 1;
        gpaPanel.add(expect_gpa,gbcGPA);
        
        /*
         * Layout for unit and results panel
         */
        
        GridBagConstraints gbcUnit = new GridBagConstraints();
        gbcUnit.insets = new Insets(3,3,3,3);
        
        JLabel name = new JLabel("Unit Name: ");
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 0;
        gbcUnit.anchor = GridBagConstraints.EAST;
        unitPanel.add(name,gbcUnit);
        
        final JTextField unit_name = new JTextField("Unit Name");
        unit_name.setBackground(Color.decode("#e0e0e0"));
        gbcUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 0;
        unitPanel.add(unit_name,gbcUnit);
        gbcUnit.fill = GridBagConstraints.NONE;

        JLabel credit_pts = new JLabel("Number of Credit Points: ");
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 1;
        unitPanel.add(credit_pts,gbcUnit);
        
        final JTextField credit_points = new JTextField("Credit Points");
        credit_points.setBackground(Color.decode("#e0e0e0"));
        gbcUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 1;
        unitPanel.add(credit_points,gbcUnit);
        gbcUnit.fill = GridBagConstraints.NONE;
        
        JButton add_button = new JButton("Add Unit");
        gbcUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 2;
        unitPanel.add(add_button,gbcUnit);
        
        JLabel past_resultLabel = new JLabel("Add Past Results: ");
        gbcUnit.anchor = GridBagConstraints.EAST;
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 3;
        unitPanel.add(past_resultLabel,gbcUnit);
        gbcUnit.anchor = GridBagConstraints.CENTER;
        
        JButton addPastResult = new JButton("Add Results");
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 3;
        unitPanel.add(addPastResult,gbcUnit);
       
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (!unit_name.getText().equals("Unit Name") && !credit_points.getText().equals("Credit Points")) {
                    Unit u = new Unit(unit_name.getText(), (int) Integer.parseInt(credit_points.getText()));
                    MyUniTracker.units.add(u);
                    UnitsPanel up = new org.main.gui.UnitsPanel(u,100,200,MyUniTrackerGUI.getCombinedPanel());
                    up.setVisible(true);
                    tab.insertTab(u.getName(),null,up,null,tab.getTabCount()-1);
                }
            }
        });
        
        addPastResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) { 
                Dialog addPast = new Dialog();
            }
        });
        
        //statsPanel.add(addPastResult);
        
        this.add(combinedGraphPanel);
        this.setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }
    
    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    } 
    
    private Scene createScene() {
        data = new ArrayList(1);
        Stage s = new Stage();
        s.setTitle("Unit Progress");
        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Assessments");
        yAxis.setLabel("Marks (%)");
        yAxis.setUpperBound(100.0);
        //creating the chart
        lineChart = new LineChart(xAxis,yAxis);
        lineChart.setTitle("All Units");
       
        //Defining a series
        for (Unit m : MyUniTracker.units) {
            XYChart.Series series = new XYChart.Series();
            series.setName(m.getName());
            for (int i = 0; i < m.getAssessments().size(); i++) {
                if (!m.getAssessments().get(i).getAssessmentName().equals("Final Exam")) 
                series.getData().add(new XYChart.Data(m.getAssessments().get(i).getAssessmentName(),(m.getAssessments().get(i).getPercentage())));  
            }
            data.add(series);
            lineChart.getData().add(series);
        }
        Scene scene = new Scene(lineChart,816,600);
        Platform.setImplicitExit(false);
       
        return scene;
    }
    
    public void updatePanel() {
        cur_wam.setText(String.valueOf(MyUniTracker.calculateWAM()));
        expect_wam.setText(String.valueOf(MyUniTracker.expectedWAM()));
        cur_gpa.setText(String.valueOf(MyUniTracker.calculateGPA()));
        expect_gpa.setText(String.valueOf(MyUniTracker.expectedGPA()));
        this.repaint();
    } 
    
    public void updateGraph() {
        this.repaint();
        Platform.runLater(new Runnable() {
            @Override public void run() {
                for (XYChart.Series s: data) {
                    s.getData().clear();
                    
                }
                lineChart.getData().clear();
                for (Unit m : MyUniTracker.units) {
                    XYChart.Series series = new XYChart.Series();
                    series.setName(m.getName());
                    for (int i = 0; i < m.getAssessments().size(); i++) {
                        if (!m.getAssessments().get(i).getAssessmentName().equals("Final Exam")) 
                        series.getData().add(new XYChart.Data(m.getAssessments().get(i).getAssessmentName(),(m.getAssessments().get(i).getPercentage())));  
                    }
                    data.add(series);
                    lineChart.getData().add(series);
                }
            }
        });
    }
    
    private class Dialog extends JFrame {
        
        public Dialog() {
            super("Add Past Results");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(280,150);
            setLocationRelativeTo(null);
            initialise();
            setVisible(true);
        }
        
        private void initialise() {
            JPanel pane = new JPanel();
            
            JLabel name = new JLabel("Unit Name:");
            final JTextField unit_name = new JTextField("Unit Name");
            JLabel grade = new JLabel("Grade:");
            String[] grades = new String[] {"HD", "D", "CR","P","N"};
            final JComboBox gradeComboBox = new JComboBox(grades);
            gradeComboBox.setSelectedIndex(0);
            JLabel mark = new JLabel("Mark:");
            final JTextField final_mark = new JTextField("60.0");
            JLabel credit_pts = new JLabel("Number of Credit Points:");
            final JTextField credit_points = new JTextField("6");
            JButton add_button = new JButton("Add Result");
            
            add_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Unit u = new Unit(unit_name.getText(),Integer.parseInt(credit_points.getText()));
                    u.setFinalGrade((String)gradeComboBox.getSelectedItem());
                    u.setFinalMark(Double.parseDouble(final_mark.getText()));
                    MyUniTracker.past_units.add(u);
                    close();
                }
            });
            
            this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    close();
                }
            });
            
            pane.add(name);
            pane.add(unit_name);
            pane.add(grade);
            pane.add(gradeComboBox);
            pane.add(mark);
            pane.add(final_mark);
            pane.add(credit_pts);
            pane.add(credit_points);
            pane.add(add_button);
            add(pane);
        }
        
        private void close() { this.setVisible(false); this.dispose(); updatePanel(); }
    }
}