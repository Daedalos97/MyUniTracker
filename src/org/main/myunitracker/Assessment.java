/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.main.myunitracker;

public class Assessment {
    
    private String name;
    private String type; // Assessment type e.g. quiz, exam
    private double weight;
    private double mark;
    private double outOf;
    
    public Assessment(String name, double mark, double outOf, double weight) {
        this.name = name;
        this.weight = weight;
        this.mark = mark;
        this.outOf = outOf;
    }
    
    public void setName(String new_name) {
        this.name = new_name;
    }
    
    public void setWeight(double new_weight) {
        this.weight = new_weight;
    }
    
    public void setMark(double mark) {
        this.mark = mark;
    }
    
    public void setOutOf(double outOf) { this.outOf = outOf; }
    
    public String getAssessmentName() { return this.name; }
    
    public double getMark() { return this.mark; }
    
    public double getOutOf() { return this.outOf; }
    
    public String getAssessmentType() { return this.type; }
    
    public double getAssessmentWeight() { return this.weight; }
    
    public double getPercentage() { return (getMark()/getOutOf())*100; }
}