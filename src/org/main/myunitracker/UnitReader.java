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
    
    /**
     * @param filepath The location of the file we wish to read/store information.
     */
    public UnitReader() {
        units = new ArrayList(2);
        past_units = new ArrayList(2);
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
                    u = new Unit(sub[1], Integer.parseInt(sub[2]));
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
                u = new Unit(sub[1],Integer.parseInt(sub[4]));
                if (sub.length > 5 && sub[5].equals("CORE")) {
                    u.setCoreUnit(true);
                } else {
                    u.setCoreUnit(false);
                }
                
                u.setFinalGrade(sub[2]);
                u.setFinalMark(Double.parseDouble(sub[3]));
                past_units.add(u);   
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
    
    /**
     * Outputs the unit information in the terminal. REDUNDANT
     */
    public void printUnits() {
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            System.out.println();
            System.out.println("Unit: " + u.getUnitName() + ":");
            for (int j = 0; j < units.get(i).getAssessments().size(); j++) {
                System.out.println(units.get(i).getAssessments().get(j).toString());
            }
            System.out.println("Overall Mark: " + u.getWeightedMark() + " Grade: " + u.getGrade() + " Mark Needed For HD: " + u.percentForGrade("HD"));
        }
    }
}