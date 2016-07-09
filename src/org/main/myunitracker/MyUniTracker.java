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

import java.io.*;
import org.main.gui.MyUniTrackerGUI;
import java.util.ArrayList;

/**
 * 
 * @author Samuel Heath
 */
public class MyUniTracker {
    
    public static ArrayList<Unit> units;
    public static ArrayList<Unit> past_units;
    public static double GPA;
    public static double WAM;
    private static String path = "src/units.txt";
    
    public static double calculateGPA() {
        double credit = 0.0;
        double sum = 0.0;
        for (int i = 0; i < past_units.size(); i++) {
            int cred = past_units.get(i).getCreditPoints();
            switch (past_units.get(i).getFinalGrade()) {
                case "HD": sum += cred*7.0; credit += cred; break;
                case "D": sum += cred*6.0; credit += cred; break;
                case "CR": sum += cred*5.0; credit += cred; break;
                case "P": sum+= cred*4.0; credit += cred; break;
                default: sum+= 0.0; break;
            }
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    public static double expectedGPA() {
        int total_credit = 0;
        double total_sum = 0.0;
        for (int i = 0; i < past_units.size(); i++) {
            int cred = past_units.get(i).getCreditPoints();
            String results = past_units.get(i).getFinalGrade();
            switch (results) {
                case "HD": total_sum += cred*7.0; total_credit += cred; break;
                case "D": total_sum += cred*6.0; total_credit += cred; break;
                case "CR": total_sum += cred*5.0; total_credit += cred; break;
                case "P": total_sum += cred*4.0; total_credit += cred; break;
                default: total_sum+= 0.0; break;
            }
        }
        for (int i = 0; i < units.size(); i++) {
            int cred = units.get(i).getCreditPoints();
            String results = units.get(i).getGrade();
            switch (results) {
                case "HD": total_sum += cred*7.0; total_credit += cred; break;
                case "D": total_sum += cred*6.0; total_credit += cred; break;
                case "CR": total_sum += cred*5.0; total_credit += cred; break;
                case "P": total_sum += cred*4.0; total_credit += cred; break;
                default: total_sum += 0.0; break;
            }
        }
        return (double)Math.round(total_sum/(double)total_credit*1000d)/1000d;
    }
    
    public static double calculateWAM() {
        double credit = 0.0;
        double sum = 0.0;
        for (int i = 0; i < past_units.size(); i++) {
            sum += past_units.get(i).getFinalMark()*past_units.get(i).getCreditPoints();
            credit += past_units.get(i).getCreditPoints();
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    public static double expectedWAM() {
        double credit = 0.0;
        double sum = 0.0;
        for (int i = 0; i < past_units.size(); i++) {
            sum += past_units.get(i).getFinalMark()*past_units.get(i).getCreditPoints();
            credit += past_units.get(i).getCreditPoints();
        }
        for (int i = 0; i < units.size(); i++) {
            sum += units.get(i).getWeightedMark()*units.get(i).getCreditPoints();
            credit += units.get(i).getCreditPoints();
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    /**
     * @return The units for this student.
     */
    public ArrayList<Unit> getUnits() { return this.units; }
    
    /**
     * @return The past past_units of this student.
     */
    public ArrayList<Unit> getGrades() { return this.past_units; }
    
    public static void writeToFile() {
        System.out.println();
        try {
            File f = new File("units.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter b = new BufferedWriter(fw);
            for (Unit u : units) {
                b.newLine();
                b.write("[Unit] " + u.getName() + " " + u.getCreditPoints());
                b.newLine();
                for (Assessment a : u.getAssessments()) {
                    if (!a.getAssessmentName().equals("Final Exam"))
                    b.write("[Assessment] " + a.getAssessmentName());
                    else b.write("[Final] ");
                    b.newLine();
                    b.write("[Mark] " + a.getMark() + "/" + a.getOutOf());
                    b.newLine();
                    b.write("[Weighting] " + a.getAssessmentWeight());
                    b.newLine();
                }
            }
            
            for (Unit u: past_units) {
                b.newLine();
                b.write("[Past-Grade] " + u.getName() + " " + u.getFinalGrade() + " " + u.getFinalMark() + " " + u.getCreditPoints()); //Conncating is shit
            }
            b.close();
        } catch (IOException IOE) { IOE.printStackTrace(); }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //If directory does have a text file, read it. Else Dont
        long time = System.currentTimeMillis();
        UnitReader UR = new UnitReader();
        UR.readUnits();
        units = UR.getUnits();
        past_units = UR.getGrades();
        MyUniTrackerGUI MUT = new MyUniTrackerGUI("MyUniTracker",1118,688);
        System.out.println(System.currentTimeMillis() - time);
        //System.exit(-1);
    }
}