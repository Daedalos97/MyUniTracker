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

package org.main.myunitracker;

import java.util.ArrayList;

/**
 * Represents a university unit
 * @author Samuel Heath
 */
public class Unit {
    
    private ArrayList<Assessment> assessments; //Stores the assessment objects.
    private String UnitName;
    private String grade;
    private String final_grade;
    private double percentage = 0.0;
    private int credit_points; // We need this for calculating the WAM and GPA
    /**
     * The percentage mark for the unit
     */
    private double weighted_mark;
    private double completed_weight;
    private double exam_weight; //weight for the final assessment (exam)
    private boolean hasFinal = false;
    private double final_mark;
    private boolean displayOverview = true;
    private boolean isCore;
    
    /**
     * Creates the unit object by initialising it.
     * @param name          The name of the unit.
     * @param creditPoints  The number of credit points the unit is.
     */
    public Unit(String name, int creditPoints) {
        this.UnitName = name;
        this.assessments = new ArrayList(1);
        this.grade = "F";
        this.final_grade = "";
        this.credit_points = creditPoints;
        this.weighted_mark = 0.0;
        this.completed_weight = 0.0;
        this.percentage = 0.0;
        this.exam_weight = 0.0;
        this.final_mark = 0.0;
    }
    
    public void setUnitName(String new_name) { this.UnitName = new_name; }
    
    public String getUnitName() { return this.UnitName; }
    
    public void setCreditPoints(int new_creditPoints) { this.credit_points = new_creditPoints; }
    
    public int getCreditPoints() { return this.credit_points; }
    
    public void addAssessment(Assessment a) { this.assessments.add(a); }
    
    public ArrayList<Assessment> getAssessments() { return this.assessments; }
    
    public Assessment findAssessment(String ass_Name) {
        for (Assessment a : assessments) {
            if (a.getAssessmentName().equals(ass_Name)) return a;
        }
        return null;
    }
    
    public void removeAssessment(String assessment_name) { this.assessments.remove(findAssessment(assessment_name)); }
    
    public String getGrade() { return this.grade; }
    
    public void setGrade(String s) { this.grade = s; }
    
    public double getWeightedMark() { return this.weighted_mark; }
    
    /**
     * @return The weight of all assessments;
     */
    public double getWeight() { return this.completed_weight; }
    
    public boolean hasFinal() { return this.hasFinal;}
    
    private void setHasFinal(boolean b) { this.hasFinal = b;} 
    
    public double getFinalMark() { return this.final_mark; }
    
    public void setFinalMark(double mark) { this.final_mark = mark; }
    
    public String getFinalGrade() { return this.final_grade; }
    
    public void setFinalGrade(String s) { this.final_grade = s; }
    
    public void setIsDisplayed(boolean b) { this.displayOverview = b; }
    
    /**
     * @return Check whether or not the unit is displayed in the Overview panel
     */
    public boolean isDisplayed() { return this.displayOverview; }
    
    public boolean isCoreUnit() { return this.isCore; }
    
    public void setCoreUnit(boolean isCoreUnit) { this.isCore = isCoreUnit; }
    
    public boolean hasMaxWeighting(double new_weighting) {
        double total_weighting = new_weighting + this.completed_weight;
        if (total_weighting <= 100.0) {
            return false;
        }
        return true;
    }
    
    /**
     * Calculates the percentage the user requires in the final exam, to get the
     * input grade.
     * @param grade The grade the user wishes to get in the unit.
     * @return      The percentage the user requires to achieve this.
     */
    public double percentForGrade(String grade) {
        double mark_needed = 0.0;
        if (!hasFinal()) return 0.0;
        else {
            switch (grade) {
                case "N": mark_needed = 43.6d; break;
                case "P": mark_needed = 49.6d; break;
                case "CR": mark_needed = 59.6d; break;
                case "D": mark_needed = 69.6d; break;
                case "HD": mark_needed = 79.6d; break;
            }
        }
        return (double)Math.round(((mark_needed-(getWeightedMark()*(getWeight()/100.0)))/(exam_weight/100.0))*1000d)/1000d;
    }
    
    /**
     * Updates the fields of this class.
     */
    public void update() {
        double totalWeighting = 0.0;
        double weightedMark = 0.0;
        double finalExamWeighting = 0.0;
        for (int i = 0; i < getAssessments().size(); i++) {
            if (!assessments.get(i).getAssessmentName().contains("Final Exam")) {
                totalWeighting += assessments.get(i).getAssessmentWeight();
                weightedMark += (assessments.get(i).getPercentage()*assessments.get(i).getAssessmentWeight())/100.0;
            } else {
                finalExamWeighting = getAssessments().get(i).getAssessmentWeight();
            }
        }
        if (finalExamWeighting > 0.0) setHasFinal(true);
        double mark = (weightedMark/totalWeighting)*100.0;
        switch ((int) Math.floor(mark/10.0)) {
            default: this.grade = "N"; break;
            case 5: this.grade = "P"; break;
            case 6: this.grade = "CR"; break;
            case 7: this.grade = "D"; break;
            case 8: this.grade = "HD"; break;
            case 9: this.grade = "HD"; break;
            case 10: this.grade = "HD"; break;
        }
        this.completed_weight = totalWeighting;
        this.exam_weight = finalExamWeighting;
        this.weighted_mark = (double)Math.round(mark*100d)/100d; 
        if (hasFinal()) {
            this.percentage = (double)Math.round(((79.6-weightedMark)/(finalExamWeighting/100.0))*1000d)/1000d;
        }
    }
}