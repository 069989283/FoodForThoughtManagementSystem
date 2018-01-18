/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author LLeNeve
 */
public class Hours {

    File file;
    double totalHours, logHours, unlogHours, hoursEarned;
    String date, activity, timeIn, timeOut, status;

    public Hours(File f, String s) {
        file = f;
        status = s;
    }

    public void addHours(String d, String tI, String tO, double h) {
        try {
            Scanner s = new Scanner(file);
            //get status 
            status = s.nextLine();
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        date = d;
        activity = status;
        timeIn = tI;
        timeOut = tO;
        hoursEarned = h;
        getTop();
        totalHours += hoursEarned;
        unlogHours += hoursEarned;
        //setting up file writer 
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file, true));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        }
        //saving account intfo to database 
        pw.println(totalHours + "," + logHours + "," + unlogHours + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + hoursEarned);
        pw.close();
    }

    public void displayingHours() {
        String line = "";
        String[] splitLine = null;
        //scans file 
        Scanner s = null;
        getTop();
        System.out.println("Total Hours: " + totalHours + "\nLogged Hours: " + logHours + "\nUnlogged Hours: " + unlogHours + "\nDate\t\tActivity\tTime In\t\tTime Out\tHours");
        try {
            int tracker = 0;
            s = new Scanner(file);
            line = s.nextLine();
            while (s.hasNextLine()) {
                if (line.equals("")) {
                    break;
                } else {
                    //sees if inputed username equals a username in the database 
                    line = s.nextLine();
                    splitLine = line.split(",");
                    date = splitLine[3];
                    activity = splitLine[4];
                    timeIn = splitLine[5];
                    timeOut = splitLine[6];
                    hoursEarned = Double.parseDouble(splitLine[7]);
                    System.out.println(date + "\t" + activity + "\t" + timeIn + "\t\t" + timeOut + "\t\t" + hoursEarned);
                }
                tracker++;
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }

    private void getTop() {
        String line = "";
        String[] splitLine = null;
        //boolean justStatus=true; 
        try {
            //scans file 
            Scanner s = new Scanner(file);
            line = s.nextLine();
            //loops through file 
            while (s.hasNextLine()) {
                //justStatus=false; 
                //sees if inputed username equals a username in the database 
                line = s.nextLine();
                splitLine = line.split(",");
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        if (splitLine.equals(null)) {
            totalHours = 0;
            logHours = 0;
            unlogHours = 0;
        } else {
            totalHours = Double.parseDouble(splitLine[0]);
            logHours = Double.parseDouble(splitLine[1]);
            unlogHours = Double.parseDouble(splitLine[2]);
        }
    }

    /**
     * @param studentNumber
     * @param activity
     * @param timeIn
     */
    public void storeTimeIn(int studentNumber, char activity, Date timeIn) {
        File tempStore = new File("TemporaryStorage.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(tempStore, true));
            //saving temp info into file
            pw.println(studentNumber + "," + activity + "," + timeIn);
            pw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Tried to store a thing that couldn't be stored");
        }
    }

    public void storeTimeOut(int studentNumber, Date timeOut) {
        File tempStore = new File("TemporaryStorage.txt");
        try {
            Scanner s = new Scanner(tempStore);
            while (s.hasNextLine()) {
                String[] timeIn = s.nextLine().split(",");
                int tempStudentID = Integer.parseInt(timeIn[0]);
                if (tempStudentID == studentNumber) {
                    //store the stuff in the actual file
                    addHours("date", "String tI", timeOut.toString(), 8);
                }
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
}
