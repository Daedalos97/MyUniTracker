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

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.main.myunitracker.Unit;
import org.main.myunitracker.Assessment;

/**
 * @author Samuel James Serwan Heath
 */
public class FormPanel extends JPanel implements ActionListener {
    
    private Unit unit;
    private JButton add,edit,remove,finalMarkNeeded;
    private JLabel finalMarkLabel,addLabel,editLabel,curGrade,curMark,reqMark;
    private JComboBox finalGradeCB;
    private JComboBox<String> assessmentsCB;
    private GraphPanel gp;
    private CombinedPanel cp;
    
    public FormPanel(Unit u, GraphPanel graphPanel, CombinedPanel combinedPanel) {
        this.setSize(200, 592);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel assessPanel = new JPanel();
        assessPanel.setLayout(new GridBagLayout());
        assessPanel.setBorder(BorderFactory.createTitledBorder("Assessments"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(assessPanel,gbc);
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridBagLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(statsPanel,gbc);
        
        this.unit = u;
        this.gp = graphPanel;
        this.cp = combinedPanel;
        Assessment[] a = new Assessment[unit.getAssessments().size()];
        unit.getAssessments().toArray(a);
        String[] grades = new String[] {"HD", "D", "CR","P","N"};
        String[] item = new String[unit.getAssessments().size()+1] ;
        double[] marks = new double[a.length];
        //item[0] = "--------"; Edit this later
        for (int i = 0; i < marks.length; i++) {
            item[i] = unit.getAssessments().get(i).getAssessmentName(); //set it to i+1
            marks[i] = unit.getAssessments().get(i).getPercentage();
        }
        
        GridBagConstraints gbcAssess = new GridBagConstraints();
        
        add = new JButton("Add Assessment");
        add.setToolTipText("Click to add an assessment to the unit");
        gbcAssess.gridwidth = 2;
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 2;
        assessPanel.add(add,gbcAssess);
        gbcAssess.gridwidth = 1;
        
        edit = new JButton("Edit Assessment");
        edit.setToolTipText("Edits the selected assessment");
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 1;
        assessPanel.add(edit,gbcAssess);
        
        remove = new JButton("Remove Assessment");
        remove.setToolTipText("Removes the selected assessment");
        gbcAssess.gridx = 1;
        gbcAssess.gridy = 1;
        assessPanel.add(remove,gbcAssess);
        
        JLabel selectAssess = new JLabel("Select Assessment:");
        gbcAssess.gridx = 0;
        gbcAssess.gridy = 0;
        assessPanel.add(selectAssess,gbcAssess);
        
        assessmentsCB = new JComboBox(item);
        assessmentsCB.setSelectedIndex(0);
        assessmentsCB.setToolTipText("Selected assessment");
        gbcAssess.gridx = 1;
        gbcAssess.gridy = 0;
        assessPanel.add(assessmentsCB,gbcAssess);
        
        GridBagConstraints gbcStats = new GridBagConstraints();
        
        finalMarkLabel = new JLabel("Select a Grade:");
        gbcStats.gridx = 0;
        gbcStats.gridy = 0;
        statsPanel.add(finalMarkLabel,gbcStats);
        
        finalGradeCB = new JComboBox(grades);
        gbcStats.anchor = GridBagConstraints.WEST;
        gbcStats.gridx = 1;
        gbcStats.gridy = 0;
        statsPanel.add(finalGradeCB,gbcStats);
        gbcStats.anchor = GridBagConstraints.EAST;
        
        reqMark = new JLabel("Need " + unit.percentForGrade("HD") + " in final exam to get HD.");
        gbcStats.gridwidth = 2;
        gbcStats.gridx = 0;
        gbcStats.gridy = 2;
        statsPanel.add(reqMark,gbcStats);
        
        finalMarkNeeded = new JButton("Percent needed in final");
        finalMarkNeeded.setToolTipText("Calculates percent needed in final exam to achieve selected grade");
        gbcStats.gridx = 0;
        gbcStats.gridy = 1;
        statsPanel.add(finalMarkNeeded, gbcStats);
        gbcStats.gridwidth = 1;
        
        JLabel gradeLabel = new JLabel("Current Grade:");
        gbcStats.gridx = 0;
        gbcStats.gridy = 3;
        statsPanel.add(gradeLabel,gbcStats);
        
        curGrade = new JLabel(unit.getGrade());
        gbcStats.gridx = 1;
        gbcStats.gridy = 3;
        statsPanel.add(curGrade,gbcStats);
        
        JLabel markLabel = new JLabel("Current(Weighted) Mark:");
        gbcStats.gridx = 0;
        gbcStats.gridy = 4;
        statsPanel.add(markLabel,gbcStats); 
        
        curMark = new JLabel(String.valueOf(unit.getWeightedMark()));
        gbcStats.gridx = 1;
        gbcStats.gridy = 4;
        statsPanel.add(curMark,gbcStats); 
        
        finalMarkNeeded.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String grade = (String) finalGradeCB.getSelectedItem();
                reqMark.setText("Need " + unit.percentForGrade(grade) + " in final exam to get " + grade + ".");
            }
        });
        
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog add = new Dialog();
            }
        });
        
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (unit.getAssessments().size() > 1) {
                    unit.removeAssessment((String)assessmentsCB.getSelectedItem());
                    gp.updateGraph(unit);
                }
                else remove.setEnabled(false);
            }
        });
        
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog edit = new Dialog(unit.findAssessment((String)assessmentsCB.getSelectedItem()));
            }
        });
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {}
    
    private class Dialog extends JFrame {
        
        public Dialog(Assessment a) {
            super("Edit " + a.getAssessmentName());
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(275,150);
            setLocationRelativeTo(null);
            initialise(a);
        }
        
        public Dialog() {
            super("Add Assessment");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(275,150);
            setLocationRelativeTo(null);
            initialise(null);
        }
        
        private void initialise(Assessment a) {
            JPanel p = new JPanel();
            p.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            final Assessment ass = a;
            
            JLabel assessname = new JLabel("Assessment Name:");
            c.anchor = GridBagConstraints.EAST;
            c.insets = new Insets(0,10,0,0);
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
                c.gridx = 1;
                c.gridy = 0;
                p.add(edit_assessname,c);
                edit_mark = new JTextField("1");
                c.gridx = 1;
                c.gridy = 1;
                p.add(edit_mark,c);
                edit_outOf = new JTextField("10");
                c.gridx = 1;
                c.gridy = 2;
                p.add(edit_outOf,c);
                edit_weight = new JTextField("5");
                c.gridx = 1;
                c.gridy = 3;
                p.add(edit_weight,c);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (unit.hasFinal() && !edit_assessname.getText().equals("Final Exam"))
                        unit.addAssessment(new Assessment(edit_assessname.getText(), Double.parseDouble(edit_mark.getText()), Double.parseDouble(edit_outOf.getText()), Double.parseDouble(edit_weight.getText())));
                        else if (!unit.hasFinal()) {
                            unit.addAssessment(new Assessment(edit_assessname.getText(), Double.parseDouble(edit_mark.getText()), Double.parseDouble(edit_outOf.getText()), Double.parseDouble(edit_weight.getText())));
                        }
                        close();
                        assessmentsCB.removeAllItems();
                        for (Assessment a : unit.getAssessments())
                            assessmentsCB.addItem(a.getAssessmentName());
                        unit.update();
                        curGrade.setText(unit.getGrade());
                        curMark.setText(String.valueOf(unit.getWeightedMark()));
                        reqMark.setText("Need " + unit.percentForGrade((String) finalGradeCB.getSelectedItem()) + " in final exam to get " + (String) finalGradeCB.getSelectedItem());
                        gp.updateGraph(unit);
                        cp.updateGraph();
                    }
                });
            } else {
                edit_assessname = new JTextField(ass.getAssessmentName());
                c.gridx = 1;
                c.gridy = 0;
                p.add(edit_assessname,c);
                edit_mark = new JTextField(String.valueOf(ass.getMark()));
                c.gridx = 1;
                c.gridy = 1;
                p.add(edit_mark,c);
                edit_outOf = new JTextField(String.valueOf(ass.getOutOf()));
                c.gridx = 1;
                c.gridy = 2;
                p.add(edit_outOf,c);
                edit_weight = new JTextField(String.valueOf(ass.getAssessmentWeight()));
                c.gridx = 1;
                c.gridy = 3;
                p.add(edit_weight,c);
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        ass.setName(edit_assessname.getText());
                        ass.setMark(Double.parseDouble(edit_mark.getText()));
                        ass.setOutOf(Double.parseDouble(edit_outOf.getText()));
                        ass.setWeight(Double.parseDouble(edit_weight.getText()));
                        close();
                        assessmentsCB.removeAllItems();
                        for (Assessment a : unit.getAssessments())
                            assessmentsCB.addItem(a.getAssessmentName());
                        unit.update();
                        curGrade.setText(unit.getGrade());
                        curMark.setText(String.valueOf(unit.getWeightedMark()));
                        reqMark.setText("Need " + unit.percentForGrade((String) finalGradeCB.getSelectedItem()) + " in final exam to get " + (String)finalGradeCB.getSelectedItem());
                        gp.updateGraph(unit);
                        cp.updateGraph();
                    }
                });
            }
            add(p);
            this.setVisible(true);
        }
        
        private void close() { this.setVisible(false); this.dispose();}
    } 
}