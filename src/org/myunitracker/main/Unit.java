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

package org.myunitracker.main;

import java.util.ArrayList;

/**
 * Represents a university unit
 * @author Samuel Heath
 */
public class Unit {
    
    private ArrayList<Assessment> assessments; //Stores the assessment objects.
    private String UnitName;
    private int grade;
    private int final_grade;
    private double credit_points; // We need this for calculating the WAM and GPA
    private double weightedMark;
    /** The percentage mark for the unit */
    private double percent;
    private double completed_weight;
    private double exam_weight; //weight for the final exam
    private boolean hasFinal = false;
    private double final_mark;
    private boolean hasFinalMark;
    private boolean displayOverview = true;
    private boolean isCore;
    private boolean isFinalisable = false;
    //Use an ENUM you DUM DUM
    public static final int GRADE_HD = 0;
    public static final int GRADE_D = 1;
    public static final int GRADE_CR = 2;
    public static final int GRADE_P = 3;
    public static final int GRADE_N = 4;
    
    /**
     * Creates the unit object by initialising it.
     * @param name          The name of the unit.
     * @param creditPoints  The number of credit points the unit is.
     */
    public Unit(String name, double creditPoints) {
        this.UnitName = name;
        this.credit_points = creditPoints;
        this.assessments = new ArrayList(1);
        this.grade = 0;
        this.final_grade = 0;
        this.weightedMark = 0.0;
        this.percent = 0.0;
        this.completed_weight = 0.0;
        this.exam_weight = 0.0;
        this.final_mark = 0.0;
    }
    
    public void setUnitName(String new_name) { this.UnitName = new_name; }
    
    public String getUnitName() { return this.UnitName; }
    
    public void setCreditPoints(double new_creditPoints) { this.credit_points = new_creditPoints; }
    
    public double getCreditPoints() { return this.credit_points; }
    
    public void addAssessment(Assessment a) { this.assessments.add(a); }
    
    public ArrayList<Assessment> getAssessments() { return this.assessments; }
    
    public Assessment findAssessment(String ass_Name) {
        for (Assessment a : assessments) {
            if (a.getAssessmentName().equals(ass_Name)) return a;
        }
        return null;
    }
    
    public void removeAssessment(String assessment_name) { this.assessments.remove(findAssessment(assessment_name)); }
    
    public int getGrade() { return this.grade; }
    
    /**
     * @param n
     * @return      Converted a said grade to string.
     */
    public static String gradeToString(int n) { 
        String g = "N";
        int grad = n;
        if (n == GRADE_HD) { 
            g = "HD"; 
        } else if (n == GRADE_D) {
            g = "D";
        } else if (n == GRADE_CR) { 
            g = "CR"; 
        } else if (n == GRADE_P) {
            g = "P";
        } else {
            g = "N";
        }
        return g;
    }
    
    public void setGrade(int i) { this.grade = i; }
    
    /**
     * @return  
     */
    public double getPercentage() { return this.percent; }
    
    /**
     * @return The weight of all assessments completed assessments;
     */
    public double getWeight() { return this.completed_weight; }
    
    /**
     * @return Determines whether or not this unit has a final assessment added or not.
     */
    public boolean hasFinal() { return this.hasFinal;}
    
    public void setHasFinal(boolean b) { this.hasFinal = b;} 
    
    /**
     * @return      If this unit is a final unit
     */
    public double getFinalMark() { return this.final_mark; }
    
    public void setFinalMark(double mark) { 
        this.final_mark = mark;
        if (mark == 0.0) {
            this.hasFinalMark = false;
        } else this.hasFinalMark = true;
    }
    
    public boolean getHasFinalMark() { return this.hasFinalMark; }
    
    public String getFinalGrade() { return gradeToString(this.final_grade); }
    
    public int getIntFinalGrade() { return this.final_grade; }
    
    public void setFinalGrade(int s) { this.final_grade = s; }
    
    public void setIsDisplayed(boolean b) { this.displayOverview = b; }
    
    /**
     * @return Check whether or not the unit is displayed in the Overview panel
     */
    public boolean isDisplayed() { return this.displayOverview; }
    
    public boolean isCoreUnit() { return this.isCore; }
    
    public void setCoreUnit(boolean isCoreUnit) { this.isCore = isCoreUnit; }
    
    /**
     * Checks whether or not editing an assessment's weighting will put the unit
     * on a weighting greater than 100, which is normally not allowed.
     * @param a                 The assessment which already exists.
     * @param new_weighting     The weighted assessment hoping to be added.
     * @return                  Whether weighting has breached max.
     */
    public boolean editHasMaxWeighting(Assessment a, double new_weighting) {
        if ((getWeight()-a.getAssessmentWeight()+new_weighting) <= 100.5) { 
            return true; 
        } else {
            return false;
        }
    }
    
    /**
     * Checks whether or not adding an assessment will put the unit on a weighting
     * greater than 100, which is normally not allowed.
     * @param new_weighting     The weighted assessment hoping to be added.
     * @return                  Whether weighting has breached max.
     */
    public boolean hasMaxWeighting(double new_weighting) {
        if (new_weighting + getWeight() <= 100.5) {
            return true;
        }
        return false;
    }
    
    /**
     * Calculates the percentage the user requires in the final exam, to get the
     * input grade.
     * @param grade The grade the user wishes to get in the unit.
     * @return      The percentage the user requires to achieve this.
     */
    public double percentForGrade(int grade) {
        double mark_needed = 0.0;
        switch (grade) {
            case GRADE_N: mark_needed = 49.4d; break;
            case GRADE_P: mark_needed = 49.6d; break;
            case GRADE_CR: mark_needed = 59.6d; break;
            case GRADE_D: mark_needed = 69.6d; break;
            case GRADE_HD: mark_needed = 79.6d; break;
        }
        return (double)Math.round(((mark_needed-(getPercentage()*(getWeight()/100.0)))/(exam_weight))*1000d)/1000d;
    }
    
    /**
     * Updates the most important fields of this class.
     */
    public void update() {
        updateWeighting();
        updateGrade();
    }
    
    /**
     * Updates the total weighting of this unit.
     */
    private void updateWeighting() {
        double completedweight = 0.0;
        double mark_weighted = 0.0;
        for (int i = 0; i < getAssessments().size(); i++) {
            completedweight += assessments.get(i).getAssessmentWeight();
            mark_weighted += (assessments.get(i).getPercentage()*assessments.get(i).getAssessmentWeight())/100.0;
        }
        this.completed_weight = completedweight;
        this.exam_weight = (100.0-completedweight)/100;
        
        if (!getHasFinalMark()) {
            double mark = (mark_weighted/completedweight)*100.0;
            this.percent = (double)Math.round(mark*100d)/100d;
        } else {
            double mark = getFinalMark();
            this.percent = (double)Math.round(mark);
        }
    }
    
    /**
     * Updates the grade the student is currently on for this unit.
     */
    private void updateGrade() {
        if (getPercentage() >= 79.6) this.grade = GRADE_HD;
        else if (getPercentage() >= 69.6) this.grade = GRADE_D;
        else if (getPercentage() >= 59.6) this.grade = GRADE_CR;
        else if (getPercentage() >= 49.6) this.grade = GRADE_P;
        else {
            this.grade = GRADE_N;
        }
    }
    
    /**
     * Moves this unit to past units and removes it from the current units.
     */
    public void finaliseUnit() {
        System.out.println("Finalising Unit");
        setFinalMark((double) Math.round(getPercentage()));
        setFinalGrade(getGrade());
        MyUniTracker.units.remove(this);
        MyUniTracker.past_results.add(this);
    }
}