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
        if (grade.equals("HD")) {
            i = Unit.GRADE_HD;
        } else if (grade.equals("D")) {
            i = Unit.GRADE_D;
        } else if (grade.equals("CR")) {
            i = Unit.GRADE_CR;
        } else if (grade.equals("P")) {
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
            scan = new Scanner(new FileReader(f.getAbsoluteFile()));
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