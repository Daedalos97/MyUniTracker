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

import java.io.*;
import org.myunitracker.gui.MyUniTrackerGUI;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 * An Application designed to track university students units one semester at a
 time, as well as an overview of their past grade.
 * @author Samuel Heath
 */
public class MyUniTracker {
    
    public static ArrayList<Unit> units;
    public static ArrayList<Unit> past_results;
    private static double GPA;
    private static double WAM;
    private static boolean isCurtin = false;
    private static double DEFAULT_CREDIT = 6.0;
    
    /*
     * @return Whether user is a curtin student or not.
     */
    public static boolean isCurtin() { return isCurtin; }
    
    /**
     * Sets the boolean    isCurtin to be equal to some boolean param.
     * @param curtin       The value we want isCurtin to be.
     */
    public static void setIsCurtin(boolean curtin) { 
        isCurtin = curtin;
        if (isCurtin()) {
            DEFAULT_CREDIT = 25.0;
        } else { DEFAULT_CREDIT = 6.0; }
    }
    
    public static double getDefaultCredit() { return DEFAULT_CREDIT; }
    
    /**
     * @param past_result  The name of the unit in the past result.
     * @return             The stored result if it exists.
     */
    public static Unit findResult(String past_result) {
        for (Unit u : past_results) {
            if (u.getUnitName().equals(past_result)) return u;
        }
        return null;
    }
    
    /**
     * @return    The user's current GPA based on past grade they have entered.
     */
    public static double calculateGPA() {
        double credit = 0.0;
        double sum = 0.0;
        if (!isCurtin()) {
            for (int i = 0; i < past_results.size(); i++) {
                double cred = past_results.get(i).getCreditPoints();
                switch (past_results.get(i).getIntFinalGrade()) {
                    case 0: sum += cred*7.0; credit += cred; break;
                    case 1: sum += cred*6.0; credit += cred; break;
                    case 2: sum += cred*5.0; credit += cred; break;
                    case 3: sum+= cred*4.0; credit += cred; break;
                    default: sum += 0.0; credit += cred; break;
                }
            }
        } else {
            for (int i = 0; i < past_results.size(); i++) {
                double cred = past_results.get(i).getCreditPoints();
                switch (past_results.get(i).getIntFinalGrade()) {
                    case 0: sum += cred*4.0; credit += cred; break;
                    case 1: sum += cred*3.0; credit += cred; break;
                    case 2: sum += cred*2.0; credit += cred; break;
                    case 3: sum+= cred*1.0; credit += cred; break;
                    default: sum += 0.0; credit += cred; break;
                }
            }
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    /**
     * @return    The expected GPA based on the current units being tracked
     */
    public static double expectedGPA() {
        int total_credit = 0;
        double total_sum = 0.0;
        double[] grade_ptsCurt = new double[] {4.0,3.0,2.0,1.0};
        double[] grade_ptsUWA = new double[] { 7.0,6.0,5.0,4.0};
        if (!isCurtin()) {
            for (int i = 0; i < past_results.size(); i++) {
                double cred = past_results.get(i).getCreditPoints();
                switch (past_results.get(i).getIntFinalGrade()) {
                    case Unit.GRADE_HD: total_sum += cred*grade_ptsUWA[0]; total_credit += cred; break;
                    case Unit.GRADE_D: total_sum += cred*grade_ptsUWA[1]; total_credit += cred; break;
                    case Unit.GRADE_CR: total_sum += cred*grade_ptsUWA[2]; total_credit += cred; break;
                    case Unit.GRADE_P: total_sum += cred*grade_ptsUWA[3]; total_credit += cred; break;
                    default: total_sum += 0.0; total_credit += cred; break;
                }
            }
            for (int i = 0; i < units.size(); i++) {
                double cred = units.get(i).getCreditPoints();
                switch (units.get(i).getGrade()) {
                    case 0: total_sum += cred*grade_ptsUWA[0]; total_credit += cred; break;
                    case 1: total_sum += cred*grade_ptsUWA[1]; total_credit += cred; break;
                    case 2: total_sum += cred*grade_ptsUWA[2]; total_credit += cred; break;
                    case 3: total_sum += cred*grade_ptsUWA[3]; total_credit += cred; break;
                    default: total_sum += 0.0; total_credit += cred; break;
                }
            }
        } else {
            for (int i = 0; i < past_results.size(); i++) {
                double cred = past_results.get(i).getCreditPoints();
                switch (past_results.get(i).getIntFinalGrade()) {
                    case 0: total_sum += cred*grade_ptsCurt[0]; total_credit += cred; break;
                    case 1: total_sum += cred*grade_ptsCurt[1]; total_credit += cred; break;
                    case 2: total_sum += cred*grade_ptsCurt[2]; total_credit += cred; break;
                    case 3: total_sum += cred*grade_ptsCurt[3]; total_credit += cred; break;
                    default: total_sum += 0.0; total_credit += cred; break;
                }
            }
            for (int i = 0; i < units.size(); i++) {
                double cred = units.get(i).getCreditPoints();
                switch (units.get(i).getGrade()) {
                    case 0: total_sum += cred*grade_ptsCurt[0]; total_credit += cred; break;
                    case 1: total_sum += cred*grade_ptsCurt[1]; total_credit += cred; break;
                    case 2: total_sum += cred*grade_ptsCurt[2]; total_credit += cred; break;
                    case 3: total_sum += cred*grade_ptsCurt[3]; total_credit += cred; break;
                    default: total_sum += 0.0; total_credit += cred; break;
                }
            }
        }
        return (double)Math.round(total_sum/(double)total_credit*1000d)/1000d;
    }
    
    /**
     * @param majorWAM    says whether or not we are looking at only core units.
     * @return            The WAM current WAM for the major.
     */
    public static double calculateWAM(boolean majorWAM) {
        double credit = 0.0;
        double sum = 0.0;
        for (int i = 0; i < past_results.size(); i++) {
            if (majorWAM) {
                if (past_results.get(i).isCoreUnit()) {
                    sum += past_results.get(i).getFinalMark()*past_results.get(i).getCreditPoints();
                    credit += past_results.get(i).getCreditPoints();
                }
            } else {
                sum += past_results.get(i).getFinalMark()*past_results.get(i).getCreditPoints();
                credit += past_results.get(i).getCreditPoints();
            }
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    public static double expectedWAM(boolean majorWAM) {
        double credit = 0.0;
        double sum = 0.0;
        for (int i = 0; i < past_results.size(); i++) {
            if (majorWAM) {
                if (past_results.get(i).isCoreUnit()) {
                    sum += past_results.get(i).getFinalMark()*past_results.get(i).getCreditPoints();
                    credit += past_results.get(i).getCreditPoints();
                }
            } else {
                sum += past_results.get(i).getFinalMark()*past_results.get(i).getCreditPoints();
                credit += past_results.get(i).getCreditPoints();
            }
        }
        for (int i = 0; i < units.size(); i++) {
            if (majorWAM) {
                if (units.get(i).isCoreUnit()) {
                    sum += units.get(i).getPercentage()*units.get(i).getCreditPoints();
                    credit += units.get(i).getCreditPoints();
                }
            } else {
                sum += units.get(i).getPercentage()*units.get(i).getCreditPoints();
                credit += units.get(i).getCreditPoints();
            }
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    /**
     * @return The units for this student.
     */
    public ArrayList<Unit> getUnits() { return this.units; }
    
    /**
     * @return The past past_results of this student.
     */
    public ArrayList<Unit> getGrades() { return this.past_results; }
    
    /**
     * Writes the data to a file on exit.
     */
    public static void writeToFile() {
        try {
            File f = new File("units.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(f.getCanonicalFile());
            BufferedWriter b = new BufferedWriter(fw);
            b.write("[Curtin-Student] " + isCurtin());
            b.newLine();
            for (Unit u : units) {
                b.newLine();
                String s = "";
                if (u.isCoreUnit())
                    s = "CORE";
                b.write("[Unit] " + u.getUnitName() + " " + u.getCreditPoints() + " " + s);
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
            
            for (Unit u: past_results) {
                String s = "";
                if (u.isCoreUnit())
                    s = " CORE";
                b.newLine();
                b.write("[Past-Grade] " + u.getUnitName() + " " + u.getFinalGrade() + " " + u.getFinalMark() + " " + u.getCreditPoints() + s); //Conncating is shit
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
        past_results = UR.getGrades();
        if (isCurtin()) {
            DEFAULT_CREDIT = 25.0;
        } else { DEFAULT_CREDIT = 6.0; }
        JWindow window = new JWindow();
            ImageIcon icon = new ImageIcon(MyUniTrackerGUI.class.getResource("images/splash.gif"));
            window.getContentPane().add(
                new JLabel("", icon, SwingConstants.CENTER));
        window.setSize(640, 400);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        MyUniTrackerGUI MUT = new MyUniTrackerGUI("MyUniTracker",1000,767);
        MUT.getTabbedPane().setSelectedIndex(MUT.getTabbedPane().getTabCount()-1);
        System.out.println(System.currentTimeMillis() - time);
    }
}