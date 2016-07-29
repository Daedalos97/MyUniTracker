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

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Samuel Heath
 */
public class UnitReader {
    
    private ArrayList<Unit> units;
    private ArrayList<Unit> past_units;
    
    public UnitReader() {
        units = new ArrayList(2);
        past_units = new ArrayList(2);
    }
    
    public static int getGrade(String grade) {
        int i = Unit.GRADE_N;
        if (grade == "HD") {
            i = Unit.GRADE_HD;
        } else if (grade == "D") {
            i = Unit.GRADE_D;
        } else if (grade == "CR") {
            i = Unit.GRADE_CR;
        } else if (grade == "P") {
            i = Unit.GRADE_P;
        }
        return i;
    }
    
    /**
     * Reads information from the input location.
     */
    public void readUnits() {
        Scanner scan = new Scanner("e.g.");
        try {
            File f = new File("units.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            scan = new Scanner(new FileReader("units.txt"));
        } catch (IOException IOE) {}
        Unit u = new Unit("", 0);
        Assessment a = new Assessment(null,0.0,0.0,0.0);
        while (scan.hasNextLine()) {
            String[] sub = scan.nextLine().split(" ");
            if (sub[0].contains("[Unit]") && !u.getUnitName().equals(sub[1])) {
                    u = new Unit(sub[1], Double.parseDouble(sub[2]));
                    if (sub.length > 3 && sub[3].equals("CORE")) {
                        u.setCoreUnit(true);
                    } else u.setCoreUnit(false);
                    units.add(u);
            } else if (sub[0].contains("[Assessment]")) {
                a = new Assessment(sub[1],0.0,0.0,0.0);
                u.addAssessment(a);
            } else if (sub[0].contains("[Weighting]")) {
                a.setWeight(Double.parseDouble(sub[1]));
            } else if (sub[0].contains("[Mark]")) {
                String[] l = sub[1].split("/");
                a.setOutOf(Double.parseDouble(l[1]));
                a.setMark(Double.parseDouble(l[0]));
            } else if (sub[0].contains("[Final]")) {
                a = new Assessment("Final Exam",0.0,0.0,0.0);
                u.addAssessment(a);
            } else if (sub[0].contains("[Past-Grade]")) {
                u = new Unit(sub[1],Double.parseDouble(sub[4]));
                if (sub.length > 5 && sub[5].equals("CORE")) {
                    u.setCoreUnit(true);
                } else {
                    u.setCoreUnit(false);
                }
                u.setFinalGrade(getGrade(sub[2]));
                u.setFinalMark(Double.parseDouble(sub[3]));
                past_units.add(u);   
            } else if (sub[0].contains("[Curtin-Student]")) {
                MyUniTracker.setIsCurtin(Boolean.parseBoolean(sub[1]));
            }
            u.update();
        }
        scan.close();
    }
    
    /**
     * @return The units for this student.
     */
    public ArrayList<Unit> getUnits() { return this.units; }
    
    /**
     * @return The past past_units of this student.
     */
    public ArrayList<Unit> getGrades() { return this.past_units; }
}