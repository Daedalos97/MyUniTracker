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