package org.main.myunitracker;

import java.util.ArrayList;

/**
 * @author Sam
 */
public class Student {
    
    public static ArrayList<Unit> units;
    private static ArrayList<String> grades;
    public static double GPA;
    public static double WAM;
    
    public Student() {}
    
    public Student(ArrayList<Unit> units, ArrayList<String> grades) {
        this.units = units;
        this.grades = grades;
    }
    
    /**
     * @return The units for this student.
     */
    public ArrayList<Unit> getUnits() { return this.units; }
    
    /**
     * @return The past grades of this student.
     */
    public ArrayList<String> getGrades() { return this.grades; }
}