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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.myunitracker.main.Unit;
import org.myunitracker.main.Assessment;
import org.myunitracker.main.MyUniTracker;
import org.myunitracker.main.UnitReader;

/**
 * @author Samuel James Serwan Heath
 */
public class FormPanel extends JPanel implements ActionListener {
    
    private Unit unit;
    private JButton add,edit,remove,finalMarkNeeded;
    private JLabel finalMarkLabel,curGrade,curMark,reqMark;
    private JComboBox finalGradeCB;
    private JComboBox<String> assessmentsCB;
    private JTabbedPane tab;
    private GraphPanel gp;
    private Font fontTitle = MyUniTrackerGUI.FONT_TITLE, fontSubTitle = MyUniTrackerGUI.fontSubTitle, fontText = MyUniTrackerGUI.fontText;
    
    public FormPanel(Unit u, GraphPanel graphPane) {
        this.setLayout(new GridBagLayout());
        tab = MyUniTrackerGUI.getTabbedPane();
        
        /*
        Initialise variables
        */
        this.unit = u;
        this.gp = graphPane;
        
        Assessment[] a = new Assessment[unit.getAssessments().size()];
        unit.getAssessments().toArray(a);
        String[] grades = new String[] {"HD", "D", "CR","P","N"};
        final String[] item = new String[unit.getAssessments().size()+1] ;
        double[] marks = new double[a.length];
        //item[0] = "--------"; Edit this later
        for (int i = 0; i < marks.length; i++) {
            item[i] = unit.getAssessments().get(i).getAssessmentName(); //set it to i+1
            marks[i] = unit.getAssessments().get(i).getPercentage();
        }
        
        /*
         * Layout Setup
         */
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        
        JPanel assessPanel = new JPanel();
        assessPanel.setBackground(Color.WHITE);
        assessPanel.setLayout(new GridBagLayout());
        assessPanel.setBorder(BorderFactory.createTitledBorder("Assessments"));
        ((javax.swing.border.TitledBorder) assessPanel.getBorder()).setTitleFont(fontTitle);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(assessPanel,gbc);
        
        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setLayout(new GridBagLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        ((javax.swing.border.TitledBorder) statsPanel.getBorder()).setTitleFont(fontTitle);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(statsPanel,gbc);
        
        JPanel unitPanel = new JPanel();
        unitPanel.setBackground(Color.WHITE);
        unitPanel.setLayout(new GridBagLayout());
        unitPanel.setBorder(BorderFactory.createTitledBorder("Edit Unit Information"));
        ((javax.swing.border.TitledBorder) unitPanel.getBorder()).setTitleFont(fontTitle);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(unitPanel,gbc);
        
        /*
         *Assessments Panel 
         */
        
        GridBagConstraints gbcAssess = new GridBagConstraints();
        
        add = new JButton("Add Assessment");
        add.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        add.setToolTipText("Click to add an assessment to the unit");
        add.setFont(fontText);
        
        gbcAssess.insets = new Insets(3,3,3,3);
        gbcAssess.gridwidth = 2;
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 2;
        assessPanel.add(add,gbcAssess);
        gbcAssess.fill = GridBagConstraints.NONE;
        gbcAssess.gridwidth = 1;
        
        edit = new JButton("Edit Assess");
        edit.setFont(fontText);
        edit.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        edit.setToolTipText("Edits the selected assessment");
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 1;
        assessPanel.add(edit,gbcAssess);
        
        remove = new JButton("Remove Assess");
        remove.setFont(fontText);
        remove.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        remove.setToolTipText("Removes the selected assessment");
        gbcAssess.gridx = 1;
        gbcAssess.gridy = 1;
        assessPanel.add(remove,gbcAssess);
        
        JLabel selectAssess = new JLabel("<html>Select<br>Assessment:</html>");
        selectAssess.setFont(fontText);
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 0;
        assessPanel.add(selectAssess,gbcAssess);
        
        assessmentsCB = new JComboBox(item);
        assessmentsCB.setFont(fontText);
        assessmentsCB.setSelectedIndex(0);
        assessmentsCB.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        assessmentsCB.setToolTipText("Selected assessment");
        gbcAssess.fill = GridBagConstraints.HORIZONTAL;
        gbcAssess.gridx = 1;
        gbcAssess.gridy = 0;
        assessPanel.add(assessmentsCB,gbcAssess);
        
        /*
            Simply check to see if there are any assessments and if 
        there is then allow users to use the edit and remove button,
        if not then make sure that they cant because they makes no sense
        and results in an error.
        */
        if (!unit.getAssessments().isEmpty()) { remove.setEnabled(true); edit.setEnabled(true);
            assessmentsCB.setEnabled(true);
        } else { remove.setEnabled(false); edit.setEnabled(false); assessmentsCB.setEnabled(false);}
        
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog add = new Dialog();
            }
        });
        
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String)assessmentsCB.getSelectedItem();
                if (s.equals("Final Exam")) {
                    unit.setHasFinal(false);
                    finalMarkNeeded.doClick();
                }
                unit.removeAssessment(s);
                //Update the Graph
                gp.updateGraph(unit);
                assessmentsCB.removeAllItems();
                for (Assessment a : unit.getAssessments()) { assessmentsCB.addItem(a.getAssessmentName()); }
                if (assessmentsCB.getItemCount() == 0) {
                    remove.setEnabled(false);
                    edit.setEnabled(false);
                    assessmentsCB.setEnabled(false);
                } else {
                    remove.setEnabled(true);
                    edit.setEnabled(true);
                    assessmentsCB.setEnabled(true);
                }
                unit.update();
                curGrade.setText(unit.gradeToString(unit.getGrade()));
                curMark.setText(String.valueOf(unit.getPercentage()));
                finalMarkNeeded.doClick();
                updatePanes();
            }
        });
        
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog edit = new Dialog(unit.findAssessment((String)assessmentsCB.getSelectedItem()));
            }
        });
        
        /*
         * Statistics Panel
         */
        
        GridBagConstraints gbcStats = new GridBagConstraints();
        gbcStats.insets = new Insets(3,3,3,3);
        
        finalMarkLabel = new JLabel("Select a Grade:");
        finalMarkLabel.setFont(fontText);
        gbcStats.anchor = GridBagConstraints.EAST;
        gbcStats.gridx = 0;
        gbcStats.gridy = 0;
        statsPanel.add(finalMarkLabel,gbcStats);
        
        finalGradeCB = new JComboBox(grades);
        finalGradeCB.setFont(fontText);
        finalGradeCB.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbcStats.fill = GridBagConstraints.HORIZONTAL;
        gbcStats.gridx = 1;
        gbcStats.gridy = 0;
        statsPanel.add(finalGradeCB,gbcStats);
        gbcStats.anchor = GridBagConstraints.CENTER;
        
        if (unit.hasFinal()) {
            reqMark = new JLabel("Need " + unit.percentForGrade(0) + " in final exam to get HD.");
            reqMark.setFont(fontText);
            gbcStats.gridwidth = 2;
            gbcStats.gridx = 0;
            gbcStats.gridy = 2;
            statsPanel.add(reqMark,gbcStats);
        } else {
            reqMark = new JLabel("No final exam has been added to this unit.");
            reqMark.setFont(fontText);
            gbcStats.gridwidth = 2;
            gbcStats.gridx = 0;
            gbcStats.gridy = 2;
            statsPanel.add(reqMark,gbcStats);
        }
        gbcStats.fill = GridBagConstraints.NONE;
        
        finalMarkNeeded = new JButton("% needed in exam");
        finalMarkNeeded.setFont(fontText);
        finalMarkNeeded.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        finalMarkNeeded.setToolTipText("Calculates percent needed in final exam to achieve selected grade");
        gbcStats.gridx = 0;
        gbcStats.gridy = 1;
        statsPanel.add(finalMarkNeeded, gbcStats);
        gbcStats.gridwidth = 1;
        
        gbcStats.fill = GridBagConstraints.NONE;
        
        JLabel gradeLabel = new JLabel("Current Grade:");
        gradeLabel.setFont(fontText);
        gbcStats.anchor = GridBagConstraints.EAST;
        gbcStats.gridx = 0;
        gbcStats.gridy = 3;
        statsPanel.add(gradeLabel,gbcStats);
        
        curGrade = new JLabel(unit.gradeToString(unit.getGrade()));
        curGrade.setFont(fontText);
        gbcStats.anchor = GridBagConstraints.WEST;
        gbcStats.gridx = 1;
        gbcStats.gridy = 3;
        statsPanel.add(curGrade,gbcStats);
        gbcStats.anchor = GridBagConstraints.CENTER;
        
        JLabel markLabel = new JLabel("Current Percentage:");
        markLabel.setFont(fontText);
        gbcStats.anchor = GridBagConstraints.EAST;
        gbcStats.gridx = 0;
        gbcStats.gridy = 4;
        statsPanel.add(markLabel,gbcStats); 
        gbcStats.anchor = GridBagConstraints.CENTER;
        
        curMark = new JLabel(String.valueOf(unit.getPercentage()));
        curMark.setFont(fontText);
        gbcStats.anchor = GridBagConstraints.WEST;
        gbcStats.gridx = 1;
        gbcStats.gridy = 4;
        statsPanel.add(curMark,gbcStats); 
        gbcStats.anchor = GridBagConstraints.CENTER;
        
        finalMarkNeeded.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (unit.hasFinal()) {
                    String grade = (String) finalGradeCB.getSelectedItem();
                    reqMark.setText("Need " + unit.percentForGrade(UnitReader.getGrade(grade)) + " in final exam to get " + grade + ".");
                } else {
                    reqMark.setText("No final exam has been added to this unit.");
                }
            }
        });
        
        /*
         * Unit Panel
         */
        
        GridBagConstraints gbcUnit = new GridBagConstraints();
        
        gbcUnit.insets = new Insets(3,3,3,3);
        final int unitColNum = 8;

        JLabel name = new JLabel("Unit Code:");
        name.setFont(fontText);
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 0;
        gbcUnit.anchor = GridBagConstraints.EAST;
        unitPanel.add(name,gbcUnit);
        
        final JTextField unit_name = new JTextField(unit.getUnitName());
        unit_name.setFont(fontText);
        unit_name.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        unit_name.setColumns(unitColNum);
        gbcUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 0;
        unitPanel.add(unit_name,gbcUnit);
        gbcUnit.fill = GridBagConstraints.NONE;

        JLabel credit_pts = new JLabel("No. Credit Points:");
        credit_pts.setFont(fontText);
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 1;
        unitPanel.add(credit_pts,gbcUnit);
        
        final JTextField credit_points = new JTextField(String.valueOf(unit.getCreditPoints()));
        credit_points.setFont(fontText);
        credit_points.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        credit_points.setColumns(unitColNum);
        gbcUnit.fill = GridBagConstraints.HORIZONTAL;
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 1;
        unitPanel.add(credit_points,gbcUnit);
        gbcUnit.fill = GridBagConstraints.NONE;
        
        JLabel core_unit = new JLabel("Is core unit:");
        core_unit.setFont(fontText);
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 2;
        unitPanel.add(core_unit,gbcUnit);
        
        final JCheckBox core_unitCheck = new JCheckBox();
        core_unitCheck.setSelected(unit.isCoreUnit());
        core_unitCheck.setBackground(Color.WHITE);
        gbcUnit.anchor = GridBagConstraints.WEST;
        gbcUnit.gridx = 1;
        unitPanel.add(core_unitCheck,gbcUnit);
        
        JButton edit_button = new JButton("Save Changes");
        edit_button.setFont(fontText);
        edit_button.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbcUnit.gridx = 0;
        gbcUnit.gridy = 3;
        unitPanel.add(edit_button,gbcUnit);
        
        JButton remove_button = new JButton("Remove Unit");
        remove_button.setFont(fontText);
        remove_button.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
        gbcUnit.gridx = 1;
        gbcUnit.gridy = 3;
        unitPanel.add(remove_button,gbcUnit);
       
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                tab.setTitleAt(tab.getSelectedIndex(),unit_name.getText());
                unit.setUnitName(unit_name.getText());
                unit.setCreditPoints(Double.parseDouble(credit_points.getText()));
                unit.setCoreUnit(core_unitCheck.isSelected());
                updatePanes();
            }
        });
        
        remove_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                tab.removeTabAt(tab.getSelectedIndex());
                MyUniTracker.units.remove(tab.getSelectedIndex());
                updatePanes();
            }
        });
        
        this.setBackground(Color.red);
        setVisible(true);
    }
    
    /**
     * Updates other panels in the application
     */
    private void updatePanes() { MyUniTrackerGUI.getCombinedPanel().updateStats(); 
    MyUniTrackerGUI.getCombinedPanel().updateGraph(); MyUniTrackerGUI.getCombinedPanel().updateAll(); gp.updateGraph(unit);}
    
    @Override
    public void actionPerformed(ActionEvent event) {}
    
    private class Dialog extends JFrame {
        
        public Dialog(Assessment a) {
            super("Edit " + a.getAssessmentName());
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(275,175);
            setLocationRelativeTo(null);
            initialise(a);
        }
        
        public Dialog() {
            super("Add Assessment");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(275,175);
            setLocationRelativeTo(null);
            initialise(null);
        }
        
        private void initialise(Assessment a) {
            JPanel p = new JPanel();
            p.setBackground(Color.WHITE);
            p.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(3,3,3,3);
            final Assessment ass = a;
            
            JLabel assessname = new JLabel("Assessment Name:");
            c.anchor = GridBagConstraints.EAST;
            c.gridx = 0;
            c.gridy = 0;
            p.add(assessname,c);
            
            final JTextField edit_assessname;
            
            JLabel mark = new JLabel("Assessment Mark:");
            c.gridx = 0;
            c.gridy = 1;
            p.add(mark, c);
            
            final JTextField edit_mark;
            
            JLabel outOf = new JLabel("Mark Out Of:");
            c.gridx = 0;
            c.gridy = 2;
            p.add(outOf, c);
            
            final JTextField edit_outOf; // what the mark is out of
            
            JLabel weight = new JLabel("Weight Of Assessment:");
            c.gridx = 0;
            c.gridy = 3;
            p.add(weight,c);
            
            final JTextField edit_weight;
            
            JButton submit = new JButton("Save Changes");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 1;
            c.gridy = 4;
            p.add(submit,c);
                    
            if (a == null) {
                edit_assessname = new JTextField("Assessment");
                edit_assessname.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 0;
                p.add(edit_assessname,c);
                edit_mark = new JTextField("1");
                edit_mark.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 1;
                p.add(edit_mark,c);
                edit_outOf = new JTextField("10");
                edit_outOf.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 2;
                p.add(edit_outOf,c);
                edit_weight = new JTextField("5");
                edit_weight.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 3;
                p.add(edit_weight,c);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (unit.hasMaxWeighting(Double.parseDouble(edit_weight.getText())) && !edit_weight.getText().equals("Too high a weight")) {
                            unit.addAssessment(new Assessment(edit_assessname.getText(), Double.parseDouble(edit_mark.getText()), Double.parseDouble(edit_outOf.getText()), Double.parseDouble(edit_weight.getText())));
                            close();
                            assessmentsCB.removeAllItems();
                            for (Assessment a : unit.getAssessments())
                                assessmentsCB.addItem(a.getAssessmentName());
                            if (assessmentsCB.getItemCount() == 0) {
                                remove.setEnabled(false);
                                edit.setEnabled(false);
                                assessmentsCB.setEnabled(false);
                            } else {
                                remove.setEnabled(true);
                                edit.setEnabled(true);
                                assessmentsCB.setEnabled(true);
                            }
                            unit.update();
                            curGrade.setText(unit.gradeToString(unit.getGrade()));
                            curMark.setText(String.valueOf(unit.getPercentage()));
                            finalMarkNeeded.doClick();
                            updatePanes();
                        } else {
                            edit_weight.setForeground(Color.red);
                            edit_weight.setText("Too high a weight");
                        }
                    }
                });
            } else {
                edit_assessname = new JTextField(ass.getAssessmentName());
                edit_assessname.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 0;
                p.add(edit_assessname,c);
                edit_mark = new JTextField(String.valueOf(ass.getMark()));
                edit_mark.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 1;
                p.add(edit_mark,c);
                edit_outOf = new JTextField(String.valueOf(ass.getOutOf()));
                edit_outOf.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 2;
                p.add(edit_outOf,c);
                edit_weight = new JTextField(String.valueOf(ass.getAssessmentWeight()));
                edit_weight.setBackground(MyUniTrackerGUI.BACKGROUND_COLOUR01);
                c.gridx = 1;
                c.gridy = 3;
                p.add(edit_weight,c);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (unit.editHasMaxWeighting(ass,Double.parseDouble(edit_weight.getText())) && !edit_weight.getText().equals("Too high a weight")) {
                            ass.setName(edit_assessname.getText());
                            ass.setMark(Double.parseDouble(edit_mark.getText()));
                            ass.setOutOf(Double.parseDouble(edit_outOf.getText()));
                            ass.setWeight(Double.parseDouble(edit_weight.getText()));
                            close();
                            assessmentsCB.removeAllItems();
                            for (Assessment a : unit.getAssessments())
                                assessmentsCB.addItem(a.getAssessmentName());
                            if (assessmentsCB.getItemCount() == 0) {
                                remove.setEnabled(false);
                                edit.setEnabled(false);
                                assessmentsCB.setEnabled(false);
                            } else {
                                remove.setEnabled(true);
                                edit.setEnabled(true);
                                assessmentsCB.setEnabled(true);
                            }
                            unit.update();
                            curGrade.setText(unit.gradeToString(unit.getGrade()));
                            curMark.setText(String.valueOf(unit.getPercentage()));
                            finalMarkNeeded.doClick();
                            updatePanes();
                        } else {
                            edit_weight.setForeground(Color.red);
                            edit_weight.setText("Too high a weight");
                        }
                    }
                });
            }
            add(p);
            this.setVisible(true);
        }
        
        private void close() { this.setVisible(false); this.dispose();}
    } 
}