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