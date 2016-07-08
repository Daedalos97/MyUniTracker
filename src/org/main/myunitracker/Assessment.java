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