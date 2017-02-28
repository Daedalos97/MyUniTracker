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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javafx.scene.chart.CategoryAxis;
import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.AnchorPane;


//MyUniTracker Imports
import org.myunitracker.main.MyUniTracker;
import org.myunitracker.main.Unit;


/* GridBoxLayout Styling
int gridx,int gridy,int gridwidth,int gridheight,double weightx, double weighty,
int anchor,int fill,Insets insets,int ipadx,int ipady
*/

/**
 * @author Samuel James Serwan Heath
 */
public class CombinedPanel extends JPanel {
    
    private ArrayList<XYChart.Series> data;
    private JButton removePast_result, editPast_result, addPast_result;
    private JComboBox past_unitsCB;
    private JLabel cur_wam,expect_wam,core_wam,cur_gpa,expect_gpa,core_expectwam;
    private JPanel checkBoxPanel;
    private JTabbedPane tab;
    private Font fontTitle = MyUniTrackerGUI.FONT_TITLE, fontSubTitle = MyUniTrackerGUI.fontSubTitle, fontText = MyUniTrackerGUI.fontText;
    private LineChart<String,Number> lineChart;
    private Map<JCheckBox,String> checkMap;
    private String[] past_unit_data;
    
    //All the colours the FXGraph uses.
    private final String[] colour = new String[] {"#f3622d","#fba71b","#57b757","#44aaca","#4258c9","#9a42c8","#c84164","#888888"};
    
    public CombinedPanel() {
        tab = MyUniTrackerGUI.getTabbedPane();
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(MyUniTrackerGUI.BACKGROUND_COLOUR, 5));
        initialisePanel();
        
        tab.setSelectedIndex(tab.getTabCount()-1);
    }
    
    private void initialisePanel() {
        //Make this panel thread safe.
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                JPanel combinedGraphPanel = new JPanel();
                combinedGraphPanel.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                combinedGraphPanel.setLayout(new GridBagLayout());

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10,10,5,5);

                JPanel statsPanel = new JPanel(new GridBagLayout());
                statsPanel.setBackground(java.awt.Color.WHITE);
                statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
                ((javax.swing.border.TitledBorder) statsPanel.getBorder()).setTitleFont(fontTitle);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weighty = 0;
                gbc.weightx = 0.1;
                combinedGraphPanel.add(statsPanel,gbc);

                JPanel unitPanel = new JPanel(new GridBagLayout());
                unitPanel.setBackground(java.awt.Color.WHITE);
                unitPanel.setBorder(BorderFactory.createTitledBorder("Add Units"));
                ((javax.swing.border.TitledBorder) unitPanel.getBorder()).setTitleFont(fontTitle);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.weighty = 0;
                gbc.weightx = 0.1;
                combinedGraphPanel.add(unitPanel,gbc);

                JPanel pastPanel = new JPanel(new GridBagLayout());
                pastPanel.setBackground(java.awt.Color.WHITE);
                pastPanel.setBorder(BorderFactory.createTitledBorder("Past Results"));
                ((javax.swing.border.TitledBorder) pastPanel.getBorder()).setTitleFont(fontTitle);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.anchor = GridBagConstraints.NORTH;
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.weighty = 0;
                gbc.weightx = 0.1;
                combinedGraphPanel.add(pastPanel,gbc);

                checkBoxPanel = new JPanel(new GridBagLayout());
                checkBoxPanel.setBackground(java.awt.Color.WHITE);
                checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Display Units"));
                ((javax.swing.border.TitledBorder) checkBoxPanel.getBorder()).setTitleFont(fontTitle);
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.weighty = 0;
                gbc.weightx = 0.1;
                combinedGraphPanel.add(checkBoxPanel,gbc);

                final JFXPanel fxPanel = new JFXPanel();
                fxPanel.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                fxPanel.setLayout(new GridBagLayout());
                gbc.insets = new Insets(10,5,10,10);
                gbc.gridheight = 4;
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1.0;
                gbc.weightx = 0.9;
                combinedGraphPanel.add(fxPanel,gbc);
                
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        fxPanel.setScene(createScene());
                        updateGraph();
                    }
                });

                GridBagConstraints gbcStats = new GridBagConstraints();
                gbcStats.fill = GridBagConstraints.HORIZONTAL;
                gbcStats.insets = new Insets(3,0,0,3);

                /*
                 * Sub Panel of Stats For WAM
                 */
                JPanel wamPanel = new JPanel(new GridBagLayout());
                wamPanel.setBackground(java.awt.Color.WHITE);
                wamPanel.setBorder(BorderFactory.createTitledBorder("Weighted Average Mark"));
                ((javax.swing.border.TitledBorder) wamPanel.getBorder()).setTitleFont(fontSubTitle);
                gbcStats.fill = GridBagConstraints.HORIZONTAL;
                gbcStats.anchor = GridBagConstraints.WEST;
                gbcStats.gridx = 0;
                gbcStats.gridy = 0;
                statsPanel.add(wamPanel,gbcStats);

                GridBagConstraints gbcWAM = new GridBagConstraints();
                gbcWAM.insets = new Insets(3,3,3,3);

                JLabel wamLabel = new JLabel("Current WAM:");
                wamLabel.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.EAST;
                gbcWAM.gridx = 0;
                gbcWAM.gridy = 0;
                wamPanel.add(wamLabel,gbcWAM);
                cur_wam = new JLabel(String.valueOf(MyUniTracker.calculateWAM(false)));
                cur_wam.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.WEST;
                gbcWAM.gridx = 1;
                gbcWAM.gridy = 0;
                wamPanel.add(cur_wam,gbcWAM);
                JLabel expect_wamLabel = new JLabel("Expected WAM (cur. units):");
                expect_wamLabel.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.EAST;
                gbcWAM.gridx = 0;
                gbcWAM.gridy = 1;
                wamPanel.add(expect_wamLabel,gbcWAM);
                expect_wam = new JLabel(String.valueOf(MyUniTracker.expectedWAM(false)));
                expect_wam.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.WEST;
                gbcWAM.gridx = 1;
                gbcWAM.gridy = 1;
                wamPanel.add(expect_wam,gbcWAM);
                JLabel core_wamLabel = new JLabel("Current WAM (Major/s):");
                core_wamLabel.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.EAST;
                gbcWAM.gridx = 0;
                gbcWAM.gridy = 2;
                wamPanel.add(core_wamLabel,gbcWAM);
                core_wam = new JLabel(String.valueOf(MyUniTracker.calculateWAM(true)));
                core_wam.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.WEST;
                gbcWAM.gridx = 1;
                gbcWAM.gridy = 2;
                wamPanel.add(core_wam,gbcWAM);
                JLabel core_expectwamLabel = new JLabel("Expected WAM (Major/s):");
                core_expectwamLabel.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.EAST;
                gbcWAM.gridx = 0;
                gbcWAM.gridy = 3;
                wamPanel.add(core_expectwamLabel,gbcWAM);
                core_expectwam = new JLabel(String.valueOf(MyUniTracker.expectedWAM(true)));
                core_expectwam.setFont(fontText);
                gbcWAM.anchor = GridBagConstraints.WEST;
                gbcWAM.gridx = 1;
                gbcWAM.gridy = 3;
                wamPanel.add(core_expectwam,gbcWAM);

                /*
                 * Sub Panel of Stats For GPA
                 */

                JPanel gpaPanel = new JPanel(new GridBagLayout());
                gpaPanel.setBackground(java.awt.Color.WHITE);
                gpaPanel.setBorder(BorderFactory.createTitledBorder("Grade Point Average"));
                ((javax.swing.border.TitledBorder) gpaPanel.getBorder()).setTitleFont(fontSubTitle);
                gbcStats.fill = GridBagConstraints.HORIZONTAL;
                gbcStats.gridx = 0;
                gbcStats.gridy = 1;
                statsPanel.add(gpaPanel,gbcStats);

                GridBagConstraints gbcGPA = new GridBagConstraints();
                gbcGPA.insets = new Insets(3,3,3,3);

                JLabel gpaLabel = new JLabel("Current GPA:");
                gpaLabel.setFont(fontText);
                gbcGPA.anchor = GridBagConstraints.EAST;
                gbcGPA.gridx = 0;
                gbcGPA.gridy = 0;
                gpaPanel.add(gpaLabel,gbcGPA);
                cur_gpa = new JLabel(String.valueOf(MyUniTracker.calculateGPA()));
                cur_gpa.setFont(fontText);
                gbcGPA.anchor = GridBagConstraints.WEST;
                gbcGPA.gridx = 1;
                gbcGPA.gridy = 0;
                gpaPanel.add(cur_gpa,gbcGPA);
                JLabel expect_gpaLabel = new JLabel("Expected GPA (cur. units):");
                expect_gpaLabel.setFont(fontText);
                gbcGPA.anchor = GridBagConstraints.EAST;
                gbcGPA.gridx = 0;
                gbcGPA.gridy = 1;
                gpaPanel.add(expect_gpaLabel,gbcGPA);
                expect_gpa = new JLabel(String.valueOf(MyUniTracker.expectedGPA()));
                expect_gpa.setFont(fontText);
                gbcGPA.anchor = GridBagConstraints.WEST;
                gbcGPA.gridx = 1;
                gbcGPA.gridy = 1;
                gpaPanel.add(expect_gpa,gbcGPA);

                /*
                 * Layout for unit panel
                 */

                GridBagConstraints gbcUnit = new GridBagConstraints();
                gbcUnit.insets = new Insets(3,3,3,3);

                JLabel name = new JLabel("Unit Code:");
                name.setFont(fontText);
                gbcUnit.gridx = 0;
                gbcUnit.gridy = 0;
                gbcUnit.anchor = GridBagConstraints.EAST;
                unitPanel.add(name,gbcUnit);

                final JTextField unit_name = new JTextField("Unit Code");
                unit_name.setToolTipText("Enter the name of the unit wish to add");
                unit_name.setFont(fontText);
                unit_name.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbcUnit.fill = GridBagConstraints.HORIZONTAL;
                gbcUnit.gridx = 1;
                gbcUnit.gridy = 0;
                unitPanel.add(unit_name,gbcUnit);
                gbcUnit.fill = GridBagConstraints.NONE;

                JLabel credit_pts = new JLabel("No. Credit Points:");
                credit_pts.setToolTipText("Enter the number of credit points for this unit");
                credit_pts.setFont(fontText);
                gbcUnit.gridx = 0;
                gbcUnit.gridy = 1;
                unitPanel.add(credit_pts,gbcUnit);

                final JTextField credit_points = new JTextField(String.valueOf(MyUniTracker.getDefaultCredit()));
                credit_points.setFont(fontText);
                credit_points.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbcUnit.fill = GridBagConstraints.HORIZONTAL;
                gbcUnit.gridx = 1;
                gbcUnit.gridy = 1;
                unitPanel.add(credit_points,gbcUnit);
                gbcUnit.fill = GridBagConstraints.NONE;

                JLabel core_unit = new JLabel("Core Unit:");
                core_unit.setFont(fontText);
                gbcUnit.gridx = 0;
                gbcUnit.gridy = 2;
                unitPanel.add(core_unit,gbcUnit);

                final JCheckBox core_unitCheck = new JCheckBox();
                core_unitCheck.setSelected(true);
                core_unitCheck.setBackground(Color.WHITE);
                gbcUnit.anchor = GridBagConstraints.WEST;
                gbcUnit.gridx = 1;
                unitPanel.add(core_unitCheck,gbcUnit);

                JLabel curtin_student = new JLabel("Curtin Student:");
                curtin_student.setFont(fontText);
                gbcUnit.anchor = GridBagConstraints.EAST;
                gbcUnit.gridx = 0;
                gbcUnit.gridy = 4;
                unitPanel.add(curtin_student,gbcUnit);

                final JCheckBox iscurtin_student = new JCheckBox();
                iscurtin_student.setSelected(MyUniTracker.isCurtin());
                iscurtin_student.setBackground(Color.WHITE);
                gbcUnit.anchor = GridBagConstraints.WEST;
                gbcUnit.gridx = 1;
                unitPanel.add(iscurtin_student,gbcUnit);

                JButton add_button = new JButton("Add Unit");
                add_button.setFont(fontText);
                add_button.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                add_button.setToolTipText("Add unit based on above information");
                //gbcUnit.fill = GridBagConstraints.HORIZONTAL;
                gbcUnit.anchor = GridBagConstraints.CENTER;
                gbcUnit.gridwidth = 2;
                gbcUnit.gridx = 0;
                gbcUnit.gridy = 3;
                unitPanel.add(add_button,gbcUnit);
                
                //Listener
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (!unit_name.getText().equals("Unit Code") && !credit_points.getText().equals("Credit Points")) {
                            Unit u = new Unit(unit_name.getText(), Double.parseDouble(credit_points.getText()));
                            u.setCoreUnit(core_unitCheck.isSelected());
                            MyUniTracker.units.add(u);
                            UnitsPanel up = new org.myunitracker.gui.UnitsPanel(u);
                            up.setVisible(true);
                            tab.insertTab(u.getUnitName(),null,up,null,tab.getTabCount()-1);
                            updateAll();
                            unit_name.setText("Unit Code");
                        }
                    }
                });
                
                gbcUnit.gridwidth = 1;

                /*
                 Layout for past results panel.
                */

                GridBagConstraints gbcPast = new GridBagConstraints();
                gbcPast.insets = new Insets(3,3,3,3);

                JLabel past_resultLabel = new JLabel("Add Past Results:");
                past_resultLabel.setFont(fontText);
                gbcPast.anchor = GridBagConstraints.EAST;
                gbcPast.gridx = 0;
                gbcPast.gridy = 1;
                pastPanel.add(past_resultLabel,gbcPast);
                gbcPast.anchor = GridBagConstraints.CENTER;

                addPast_result = new JButton("Add Results");
                addPast_result.setFont(fontText);
                addPast_result.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                addPast_result.setToolTipText("Click to add previous results");
                gbcPast.fill = GridBagConstraints.HORIZONTAL;
                gbcPast.anchor = GridBagConstraints.WEST;
                gbcPast.gridx = 1;
                gbcPast.gridy = 1;
                pastPanel.add(addPast_result,gbcPast);
                gbcPast.fill = GridBagConstraints.NONE;

                JLabel past_unitLabel = new JLabel("Result:");
                past_unitLabel.setFont(fontText);
                gbcPast.anchor = GridBagConstraints.EAST;
                gbcPast.gridx = 0;
                gbcPast.gridy = 2;
                pastPanel.add(past_unitLabel,gbcPast);

                gbcPast.fill = GridBagConstraints.HORIZONTAL;

                editPast_result = new JButton("Edit Result");
                editPast_result.setFont(fontText);
                editPast_result.setToolTipText("Click to edit the selected result");
                gbcPast.gridx = 0;
                gbcPast.gridy = 3;
                pastPanel.add(editPast_result,gbcPast);

                removePast_result = new JButton("Remove Result");
                removePast_result.setFont(fontText);
                removePast_result.setToolTipText("Click to remove the selected result");
                gbcPast.gridx = 1;
                pastPanel.add(removePast_result,gbcPast);
                
                past_unitsCB = new JComboBox();
                past_unitsCB.setToolTipText("Select past results");
                past_unitsCB.setFont(fontText);
                past_unitsCB.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbcPast.gridx = 1;
                gbcPast.gridy = 2;
                pastPanel.add(past_unitsCB,gbcPast);
                
                //Updates the above combobox.
                updatePastResults();
                /*
                    Simply check to see if there are any past results and if 
                there is then allow users to use the edit and remove button,
                if not then make sure that they cant because they makes no sense
                and results in an error.
                */
                if (MyUniTracker.past_results.isEmpty()) { removePast_result.setEnabled(false);
                    editPast_result.setEnabled(false);  past_unitsCB.setEnabled(false);
                } else { removePast_result.setEnabled(true); editPast_result.setEnabled(true);
                    past_unitsCB.setEnabled(true);
                }

                iscurtin_student.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MyUniTracker.setIsCurtin(iscurtin_student.isSelected());
                        credit_points.setText(String.valueOf(MyUniTracker.getDefaultCredit()));
                        updateStats();
                    }
                });

                /*
                 * Check Box Panel and Listener Init.
                 */
                updateAll();
                addListeners();
                
                add(combinedGraphPanel);
                setVisible(true);
            }
        });
    }
    
    /**
     * Adds listeners to buttons which don't rely on other variables.
     */
    private void addListeners() {
        addPast_result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) { 
                Dialog addPast = new Dialog(null);
            }
        });

        editPast_result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) { 
                Dialog editPast = new Dialog(MyUniTracker.findResult((String)past_unitsCB.getSelectedItem()));
            }
        });

        removePast_result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                MyUniTracker.past_results.remove(MyUniTracker.findResult((String)past_unitsCB.getSelectedItem()));
                past_unitsCB.removeAllItems();
                for (Unit u : MyUniTracker.past_results) 
                    past_unitsCB.addItem(u.getUnitName());
                if (past_unitsCB.getItemCount() == 0) {
                    removePast_result.setEnabled(false);
                    editPast_result.setEnabled(false);
                    past_unitsCB.setEnabled(false);
                } else {
                    editPast_result.setEnabled(true);
                    editPast_result.setEnabled(true);
                    past_unitsCB.setEnabled(true);
                }
                updateAll();
            }
        });
    }
    
    public void updatePastResults() {
        past_unitsCB.removeAllItems();
        for (Unit u : MyUniTracker.past_results) {
                past_unitsCB.addItem(u.getUnitName());
            if (past_unitsCB.getItemCount() == 0) {
                removePast_result.setEnabled(false);
                editPast_result.setEnabled(false);
                past_unitsCB.setEnabled(false);
            } else {
                removePast_result.setEnabled(true);
                editPast_result.setEnabled(true);
                past_unitsCB.setEnabled(true);
            }
        }
    }
    
    private void setCheckBoxVisible() {
        if (MyUniTracker.getCurrentUnitCount() > 0) {
            checkBoxPanel.setVisible(true);
        } else {
            checkBoxPanel.setVisible(false);
        }
    }

    private Scene createScene() {
        data = new ArrayList(1);
        Stage s = new Stage();
        s.setTitle("Semester Progress");
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
            series.setName(m.getUnitName());
            for (int i = 0; i < m.getAssessments().size(); i++) {
                if (!m.getAssessments().get(i).getAssessmentName().equals("Final Exam")) 
                series.getData().add(new XYChart.Data(m.getAssessments().get(i).getAssessmentName(),(m.getAssessments().get(i).getPercentage())));  
            }
            data.add(series);
            lineChart.getData().add(series);
        }
        
        AnchorPane anchorPane = new AnchorPane();
        
        AnchorPane.setTopAnchor(lineChart, 0.0);
        AnchorPane.setBottomAnchor(lineChart, 0.0);
        AnchorPane.setLeftAnchor(lineChart, 0.0);
        AnchorPane.setRightAnchor(lineChart, 0.0);
        
        anchorPane.getChildren().add(lineChart);
        
        Scene scene = new Scene(anchorPane);
       
        Platform.setImplicitExit(false);
        return scene;
    }
    
    public void updateStats() {
        cur_wam.setText(String.valueOf(MyUniTracker.calculateWAM(false)));
        expect_wam.setText(String.valueOf(MyUniTracker.expectedWAM(false)));
        core_wam.setText(String.valueOf(MyUniTracker.calculateWAM(true)));
        core_expectwam.setText(String.valueOf(MyUniTracker.expectedWAM(true)));
        cur_gpa.setText(String.valueOf(MyUniTracker.calculateGPA()));
        expect_gpa.setText(String.valueOf(MyUniTracker.expectedGPA()));
        this.repaint();
    } 
    
    public void updateGraph() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                for (XYChart.Series s: data) {
                    s.getData().clear();
                }
                lineChart.getData().clear();
                int i = 0;
                for (Unit unit : MyUniTracker.units) {
                    if (unit.isDisplayed()) {
                        XYChart.Series series = new XYChart.Series();
                        tab.setForegroundAt(i, Color.decode(colour[i%colour.length]));
                        i++;
                        series.setName(unit.getUnitName());
                        for (int j = 0; j < unit.getAssessments().size(); j++) {
                            if (!unit.getAssessments().get(j).getAssessmentName().equals("Final Exam")) 
                                series.getData().add(new XYChart.Data(unit.getAssessments().get(j).getAssessmentName(),(unit.getAssessments().get(j).getPercentage())));  
                        }
                        data.add(series);
                        lineChart.getData().add(series);
                    }
                }
            }
        });
    }
    
    protected void updateAll() {
        checkBoxPanel.removeAll();
        checkMap = new HashMap();
        
        for (int i = 0; i < MyUniTracker.units.size(); i++) {
            GridBagConstraints gbcCheck = new GridBagConstraints();
            JLabel unit = new JLabel(MyUniTracker.units.get(i).getUnitName());
            unit.setFont(fontText);
            gbcCheck.anchor = GridBagConstraints.EAST;
            gbcCheck.gridx = 0;
            gbcCheck.gridy = i;
            checkBoxPanel.add(unit,gbcCheck);
            JCheckBox cb = new JCheckBox();
            cb.setFont(fontText);
            cb.setBackground(Color.WHITE);
            cb.setSelected(MyUniTracker.units.get(i).isDisplayed());
            gbcCheck.anchor = GridBagConstraints.CENTER;
            gbcCheck.gridx = 1;
            checkBoxPanel.add(cb,gbcCheck);
            
            checkMap.put(cb, MyUniTracker.units.get(i).getUnitName());
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox b = (JCheckBox) e.getSource();
                    for (Unit u : MyUniTracker.units) {
                        if (u.getUnitName().equals(checkMap.get(b))) {
                            u.setIsDisplayed(!u.isDisplayed());
                        }
                    }
                    updateStats();
                    updateGraph();
                }
            });
            unit.setForeground(Color.decode(colour[i%colour.length]));
        }
    }
    
    private class Dialog extends JFrame {
        
        private Unit result;
        
        public Dialog(Unit res) {
            super("Add Past Results");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.result = res;
            setSize(330,250);
            setLocationRelativeTo(null);
            initialiseDialog();
            setVisible(true);
        }
        
        private int getGradeFromMark(double mark) {
            switch ((int)Math.floor(mark/10.0)) {
                case 5: return Unit.GRADE_P;
                case 6: return Unit.GRADE_CR;
                case 7: return Unit.GRADE_D;
                case 8: return Unit.GRADE_HD;
                case 9: return Unit.GRADE_HD;
                case 10: return Unit.GRADE_HD;
                default: return Unit.GRADE_N;
            }
        }
        
        private void initialiseDialog() {
            JPanel pane = new JPanel();
            pane.setLayout(new GridBagLayout());
            pane.setBackground(Color.WHITE);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(3,5,5,3);
            gbc.anchor = GridBagConstraints.EAST;
            
            JLabel name = new JLabel("Unit Code:");
            name.setFont(fontText);
            gbc.gridx = 0;
            gbc.gridy = 0;
            pane.add(name,gbc);
            
            final JTextField unit_name;
            
            JLabel mark = new JLabel("Mark:");
            mark.setFont(fontText);
            gbc.gridx = 0;
            gbc.gridy = 2;
            pane.add(mark,gbc);
            
            final JTextField final_mark;
            
            JLabel credit_pts = new JLabel("No. Credit Points:");
            credit_pts.setFont(fontText);
            gbc.gridx = 0;
            gbc.gridy = 3;
            pane.add(credit_pts,gbc);
            
            final JTextField credit_points;
            
            JLabel core_unit = new JLabel("Core Unit:");
            core_unit.setFont(fontText);
            gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 0;
            gbc.gridy = 4;
            pane.add(core_unit,gbc);
            
            JButton add_button = new JButton("Add Result");
            add_button.setFont(fontText);
            add_button.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 6;
            pane.add(add_button,gbc);
            
            final JLabel error_message;
            
            final JCheckBox core_unitCheck = new JCheckBox();
            
            if (result != null) {
                add_button.setText("Save Changes");
                
                unit_name = new JTextField(result.getUnitName());
                unit_name.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                unit_name.setFont(fontText);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                pane.add(unit_name,gbc);
                
                
                final_mark = new JTextField(String.valueOf(result.getFinalMark()));
                final_mark.setFont(fontText);
                final_mark.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 2;
                pane.add(final_mark,gbc);

                credit_points = new JTextField(String.valueOf(result.getCreditPoints()));
                credit_points.setFont(fontText);
                credit_points.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 3;
                pane.add(credit_points,gbc);
                
                core_unitCheck.setSelected(result.isCoreUnit());
                core_unitCheck.setBackground(Color.WHITE);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.gridx = 1;
                gbc.gridy = 4;
                pane.add(core_unitCheck,gbc);
                
                error_message = new JLabel("");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 3;
                pane.add(error_message, gbc);
                
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String unitName = unit_name.getText();
                            if (unitName.isEmpty() || unitName.equals("Enter valid name") || MyUniTracker.findPastUnit(unitName) != result) {
                                throw new IllegalArgumentException();
                            }
                            
                            Double finalMark = Double.parseDouble(final_mark.getText());
                            int finalGrade = getGradeFromMark(Double.parseDouble(final_mark.getText()));
                            if (finalMark > 100) {
                                throw new NumberFormatException();
                            }
                            Double credPts = Double.parseDouble(credit_points.getText());
                            
                            result.setUnitName(unitName);
                            result.setFinalGrade(finalGrade);
                            result.setFinalMark(finalMark);
                            result.setCreditPoints(credPts);
                            result.setCoreUnit(core_unitCheck.isSelected());
                            updatePastResults();
                            close();
                        } catch (NumberFormatException NFE) {
                            error_message.setForeground(Color.red);
                            error_message.setText("Check you entered valid numbers");
                        } catch (IllegalArgumentException IAE) {
                            unit_name.setForeground(Color.red);
                            unit_name.setText("Enter valid name");
                        }
                    }
                });
            } else {
                unit_name = new JTextField("Unit Code");
                unit_name.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                unit_name.setFont(fontText);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 0;
                pane.add(unit_name,gbc);
                
                gbc.fill = GridBagConstraints.NONE;
                
                final_mark = new JTextField("80.0");
                final_mark.setFont(fontText);
                final_mark.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 2;
                pane.add(final_mark,gbc);
                gbc.fill = GridBagConstraints.NONE;

                credit_points = new JTextField(String.valueOf(MyUniTracker.getDefaultCredit()));
                credit_points.setFont(fontText);
                credit_points.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.gridx = 1;
                gbc.gridy = 3;
                pane.add(credit_points,gbc);
                
                core_unitCheck.setSelected(true);
                core_unitCheck.setBackground(Color.WHITE);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.gridx = 1;
                gbc.gridy = 4;
                pane.add(core_unitCheck,gbc);
                
                error_message = new JLabel("");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 3;
                pane.add(error_message, gbc);
                
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        try {
                            String unitName = unit_name.getText();
                            if (unitName.isEmpty() || unitName.equals("Unit Code") || unitName.equals("Enter valid name") || MyUniTracker.findPastUnit(unitName) != null ) {
                                throw new IllegalArgumentException();
                            }
                            
                            Double finalMark = Double.parseDouble(final_mark.getText());
                            int finalGrade = getGradeFromMark(Double.parseDouble(final_mark.getText()));
                            if (finalMark > 100) {
                                throw new NumberFormatException();
                            }
                            Double credPts = Double.parseDouble(credit_points.getText());
                        
                            Unit res = new Unit(unitName,credPts);
                            res.setFinalGrade(finalGrade);
                            res.setFinalMark(finalMark);
                            res.setCoreUnit(core_unitCheck.isSelected());
                            MyUniTracker.past_results.add(res);
                            updatePastResults();
                            close();
                            
                        } catch (NumberFormatException NFE) {
                            error_message.setForeground(Color.red);
                            error_message.setText("Check ypu entered valid numbers");
                        } catch (IllegalArgumentException IAE) {
                            unit_name.setForeground(Color.red);
                            unit_name.setText("Enter valid name");
                        }
                    }
                });
            }
            
            this.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    close();
                }
            });
            add(pane);
        }
        
        private void close() { this.setVisible(false); this.dispose(); updateStats(); }
    }
}