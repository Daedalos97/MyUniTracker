package org.main.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.main.myunitracker.Unit;
import org.main.myunitracker.Assessment;
import org.main.myunitracker.MyUniTracker;

/**
 * @author Samuel Heath
 */
public class FormPanel extends JPanel implements ActionListener {
    
    private Unit unit;
    private JButton add,edit,remove,checkFinalMark;
    private JLabel finalMarkLabel,addLabel,editLabel,curGrade,curMark,reqMark;
    private JComboBox finalCB;
    private JComboBox<String> jcb;
    private GraphPanel gp;
    private CombinedPanel cp;
    private final int i;
    
    public FormPanel(int index, GraphPanel graphPanel, CombinedPanel combinedPanel) {
        this.setLayout(new GridLayout(3,4));
        unit = MyUniTracker.units.get(index);
        this.gp = graphPanel;
        this.cp = combinedPanel;
        i = index;
        
        add = new JButton("Add Assessment");
        edit = new JButton("Edit Assessment");
        remove  = new JButton("Remove Assessment");
        checkFinalMark = new JButton("Calculate % needed in final");
        checkFinalMark.setToolTipText("Calculates % needed in final exam to achieve selected grade.");
      
        finalMarkLabel = new JLabel("Select a Grade");
        addLabel = new JLabel(" Click to add an assessment: ");
        editLabel = new JLabel(" Select assessment to edit: ");
        curGrade = new JLabel("Current Grade: " + unit.getGrade());
        curMark = new JLabel("Current(Weighted) Mark: " + unit.getWeightedMark());
        reqMark = new JLabel("Need " + unit.percentForGrade("HD") + " in final exam to get HD.");
        
        Assessment[] u = new Assessment[unit.getAssessments().size()];
        unit.getAssessments().toArray(u);
        String[] grades = new String[] {"HD", "D", "CR","P","N"};
        String[] item = new String[unit.getAssessments().size()+1] ;
        double[] marks = new double[u.length];
        //item[0] = "--------"; Edit this later
        for (int i = 0; i < marks.length; i++) {
            item[i] = unit.getAssessments().get(i).getAssessmentName(); //set it to i+1
            marks[i] = unit.getAssessments().get(i).getPercentage();
        }
        jcb = new JComboBox(item);
        finalCB = new JComboBox(grades);
        jcb.setSelectedIndex(0);
        
        checkFinalMark.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!finalCB.getSelectedItem().equals("--------"))
                reqMark.setText("Need " + unit.percentForGrade((String) finalCB.getSelectedItem()) + " in final exam to get HD.");
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
                    unit.removeAssessment((String)jcb.getSelectedItem());
                    gp.updateGraph(i);
                }
                else remove.setEnabled(false);
            }
        });
        
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog edit = new Dialog(unit.findAssessment((String)jcb.getSelectedItem()));
            }
        });
        
        //table.setModel(u);
        add(addLabel);
        add(add);
        add(editLabel,BorderLayout.WEST);
        add(jcb);
        add(edit);
        add(remove);
        add(curGrade);
        add(curMark);
        add(finalMarkLabel);
        add(finalCB);
        add(reqMark);
        add(checkFinalMark);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {}
    
    private class Dialog extends JFrame {
        
        public Dialog(Assessment a) {
            super(a.getAssessmentName());
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(750,100);
            setLocationRelativeTo(null);
            initialise(a);
        }
        
        public Dialog() {
            super("New Assessment");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(750,100);
            setLocationRelativeTo(null);
            initialise(null);
        }
        
        private void initialise(Assessment a) {
            JPanel p = new JPanel();
            p.setLayout(new GridLayout(2,2));
            final Assessment ass = a;
            
            JLabel assessname;
            JLabel mark;
            JLabel outOf;
            JLabel weight;
            final JTextField edit_assessname;
            final JTextField edit_mark;
            final JTextField edit_weight;
            final JTextField edit_outOf; // what the mark is out of
            JButton submit = new JButton("Save Changes");
                    
            if (a == null) {
                assessname = new JLabel("Name of Assessment: ");
                edit_assessname = new JTextField("Assessment");
                mark = new JLabel("Mark for assessment: ");
                edit_mark = new JTextField("1");
                outOf = new JLabel("Mark out of: ");
                edit_outOf = new JTextField("10");
                weight = new JLabel("Weight of assessment: ");
                edit_weight = new JTextField("5");
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (unit.hasFinal() && !edit_assessname.getText().equals("Final Exam"))
                        unit.addAssessment(new Assessment(edit_assessname.getText(), Double.parseDouble(edit_mark.getText()), Double.parseDouble(edit_outOf.getText()), Double.parseDouble(edit_weight.getText())));
                        else if (!unit.hasFinal()) {
                            unit.addAssessment(new Assessment(edit_assessname.getText(), Double.parseDouble(edit_mark.getText()), Double.parseDouble(edit_outOf.getText()), Double.parseDouble(edit_weight.getText())));
                        }
                        close();
                        jcb.removeAllItems();
                        for (Assessment a : unit.getAssessments())
                            jcb.addItem(a.getAssessmentName());
                        unit.update();
                        curGrade.setText("Current Grade: " + unit.getGrade());
                        curMark.setText("Current(%) Mark: " + unit.getWeightedMark());
                        reqMark.setText("Need " + unit.percentForGrade((String) finalCB.getSelectedItem()) + " in final exam to get " + (String) finalCB.getSelectedItem());
                        gp.updateGraph(i);
                        cp.updateGraph();
                    }
                });
            } else {
                assessname = new JLabel("Name of Assessment: ");
                edit_assessname = new JTextField(ass.getAssessmentName());
                mark = new JLabel("Edit mark: ");
                edit_mark = new JTextField(String.valueOf(ass.getMark()));
                outOf = new JLabel("Assessment mark out of: ");
                edit_outOf = new JTextField(String.valueOf(ass.getOutOf()));
                weight = new JLabel("Weight of assessment on unit: ");
                edit_weight = new JTextField(String.valueOf(ass.getAssessmentWeight()));
                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        ass.setName(edit_assessname.getText());
                        ass.setMark(Double.parseDouble(edit_mark.getText()));
                        ass.setOutOf(Double.parseDouble(edit_outOf.getText()));
                        ass.setWeight(Double.parseDouble(edit_weight.getText()));
                        close();
                        jcb.removeAllItems();
                        for (Assessment a : unit.getAssessments())
                            jcb.addItem(a.getAssessmentName());
                        unit.update();
                        curGrade.setText("Current Grade: " + unit.getGrade());
                        curMark.setText("Current(Weighted) Mark: " + unit.getWeightedMark());
                        reqMark.setText("Need " + unit.percentForGrade((String) finalCB.getSelectedItem()) + " in final exam to get " + (String) finalCB.getSelectedItem());
                        gp.updateGraph(i);
                        cp.updateGraph();
                    }
                });
            }
            p.add(assessname);
            p.add(edit_assessname);
            p.add(mark);
            p.add(edit_mark);
            p.add(outOf);
            p.add(edit_outOf);
            p.add(weight);
            p.add(edit_weight);
            p.add(submit);
            add(p);
            this.setVisible(true);
        }
        
        private void close() { this.setVisible(false); this.dispose();}
    } 
}