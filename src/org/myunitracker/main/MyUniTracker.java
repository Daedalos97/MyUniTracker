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

//Java Lang Imports
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

//MyUniTracker Imports
import org.myunitracker.gui.MyUniTrackerGUI;

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
        double result = (double)Math.round(sum/credit*1000d)/1000d;
        if (!isCurtin()) {
            return result;
        } else {
            if (result == 0.0) {
                return result;
            } else {
                return result-3.0;
            }
        }
    }
    
    /**
     * @return    The expected GPA based on the current units being tracked
     *            and based on the past results of the student.
     */
    public static double expectedGPA() {
        int total_credit = 0;
        double total_sum = 0.0;
        double[] grade_ptsUWA = new double[] { 7.0,6.0,5.0,4.0};
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
            if (units.get(i).getPercentage() != 0) {
                switch (units.get(i).getGrade()) {
                    case 0: total_sum += cred*grade_ptsUWA[0]; total_credit += cred; break;
                    case 1: total_sum += cred*grade_ptsUWA[1]; total_credit += cred; break;
                    case 2: total_sum += cred*grade_ptsUWA[2]; total_credit += cred; break;
                    case 3: total_sum += cred*grade_ptsUWA[3]; total_credit += cred; break;
                    default: total_sum += 0.0; total_credit += cred; break;
                }
            }
        }
        
        double result = (double)Math.round(total_sum/(double)total_credit*1000d)/1000d;
        
         if (!isCurtin()) {
            return result;
        } else {
            if (result == 0.0) {
                return result;
            } else {
                return result-3.0;
            }
        }
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
    
    /**
     * Calculate The expected WAM of a student.
     * @param majorWAM  Checks if we are using only core units.
     * @return  Expected Weighted Average Mark.
     */
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
                    if (units.get(i).getPercentage() != 0) {
                        sum += units.get(i).getPercentage()*units.get(i).getCreditPoints();
                        credit += units.get(i).getCreditPoints();
                    }
                }
            } else {
                if (units.get(i).getPercentage() != 0) {
                    sum += units.get(i).getPercentage()*units.get(i).getCreditPoints();
                    credit += units.get(i).getCreditPoints();
                }
            }
        }
        return (double)Math.round(sum/credit*1000d)/1000d;
    }
    
    /**
     * @return The units for this student.
     */
    public ArrayList<Unit> getUnits() { return units; }
    
    /**
     * @return The past past_results of this student.
     */
    public static ArrayList<Unit> getGrades() { return past_results; }
    
    public static Unit findPastUnit(String unit_name) {
        for (Unit u : getGrades()) {
            if (u.getUnitName().equals(unit_name)) return u;
        }
        
        return null;
    }
    
    public static int getCurrentUnitCount() {
        return units.size();
    }
    
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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        MyUniTrackerGUI MUT = new MyUniTrackerGUI("MyUniTracker",screenSize.width,screenSize.height);
        MUT.getTabbedPane().setSelectedIndex(MUT.getTabbedPane().getTabCount()-1);
        System.out.println(System.currentTimeMillis() - time);
    }
}