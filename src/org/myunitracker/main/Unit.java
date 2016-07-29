/*
 * Copyright (c) 2016, Samuel James Serwan Heath
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
    private double percentage = 0.0;
    private double credit_points; // We need this for calculating the WAM and GPA
    private double weightedMark;
    /** The percentage mark for the unit */
    private double percent;
    private double completed_weight;
    private double exam_weight; //weight for the final assessment (exam)
    private boolean hasFinal = false;
    private double final_mark;
    private boolean displayOverview = true;
    private boolean isCore;
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
        this.percentage = 0.0;
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
    
    public static String gradeToString(int n) { 
        String g = "";
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
    
    public double getWeightedMark() { return this.weightedMark; }
    
    public double getPercentage() { return this.percent; }
    
    /**
     * @return The weight of all assessments completed assessments;
     */
    public double getWeight() { return this.completed_weight; }
    
    public boolean hasFinal() { return this.hasFinal;}
    
    public void setHasFinal(boolean b) { this.hasFinal = b;} 
    
    public double getFinalMark() { return this.final_mark; }
    
    public void setFinalMark(double mark) { this.final_mark = mark; }
    
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
    
    public boolean editHasMaxWeighting(Assessment a, double new_weighting) {
        if ((getWeight()-a.getAssessmentWeight()+new_weighting) <= 100.5) { 
            return true; 
        } else {
            return false;
        }
    }
    
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
        if (!hasFinal()) return 0.0;
        else {
            switch (grade) {
                case GRADE_N: mark_needed = 49.4d; break;
                case GRADE_P: mark_needed = 49.6d; break;
                case GRADE_CR: mark_needed = 59.6d; break;
                case GRADE_D: mark_needed = 69.6d; break;
                case GRADE_HD: mark_needed = 79.6d; break;
            }
        }
        return (double)Math.round(((mark_needed-(getPercentage()*(getWeight()/100.0)))/(exam_weight/100.0))*1000d)/1000d;
    }
    
    private void updateWeighting() {
        double completedweight = 0.0;
        double weightedMark = 0.0;
        double finalExamWeighting = 0.0;
        for (int i = 0; i < getAssessments().size(); i++) {
            if (!assessments.get(i).getAssessmentName().contains("Final Exam")) {
                completedweight += assessments.get(i).getAssessmentWeight();
                weightedMark += (assessments.get(i).getPercentage()*assessments.get(i).getAssessmentWeight())/100.0;
            } else {
                finalExamWeighting = getAssessments().get(i).getAssessmentWeight();
            }
        }
        if (finalExamWeighting > 0.0) setHasFinal(true);
        if (hasFinal()) {
            this.percentage = (double)Math.round(((79.6-weightedMark)/(finalExamWeighting/100.0))*1000d)/1000d;
        }
        this.completed_weight = completedweight;
        this.exam_weight = finalExamWeighting;
        double mark = (weightedMark/completedweight)*100.0;
        this.percent = (double)Math.round(mark*100d)/100d; 
    }
    
    private void updateGrade() {
        switch ((int) Math.floor(getPercentage()/10.0)) {
            default: this.grade = GRADE_N; break;
            case 5: this.grade = GRADE_P; break;
            case 6: this.grade = GRADE_CR; break;
            case 7: this.grade = GRADE_D; break;
            case 8: this.grade = GRADE_HD; break;
            case 9: this.grade = GRADE_HD; break;
            case 10: this.grade = GRADE_HD; break;
        }
    }
    
    /**
     * Updates the fields of this class.
     */
    public void update() {
        updateWeighting();
        updateGrade();
    }
}