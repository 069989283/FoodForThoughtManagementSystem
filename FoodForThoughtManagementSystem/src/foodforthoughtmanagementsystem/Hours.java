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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LLeNeve
 */
public class Hours {

    RandomAccessFile file;
    double totalHours, logHours, unlogHours, hoursEarned;
    String date, activity, timeIn, timeOut, status;
    int verify;

    public static SimpleDateFormat sdfClock = new SimpleDateFormat("hh:mm");
    public static SimpleDateFormat sdfDay = new SimpleDateFormat("MM/dd/yyyy");

    public Hours(RandomAccessFile f, String s) {
        file = f;
        status = s;
    }

    public void addFirstHours(String d, String a, String tI, String tO) {
        int lineNum = 1;
        date = d;
        activity = status;
        timeIn = tI;
        timeOut = tO;
        hoursEarned = getDuration(timeIn, timeOut);
        verify = 0;
        String line = "";
        String[] splitLine = null;
        try {
            file.seek(0);
            line = file.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        }
        splitLine = line.split(",");
        totalHours = Double.parseDouble(splitLine[1]);
        logHours = Double.parseDouble(splitLine[2]);
        unlogHours = Double.parseDouble(splitLine[3]);
        totalHours += hoursEarned;
        //saving account intfo to database 
        try {
            //file.seek(44);
            file.seek(2);
            System.out.println();
            file.writeBytes(totalHours + ",0,0,001");
            file.seek(13);
            System.out.println();
            file.writeBytes("\r\n00" + lineNum + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + hoursEarned + "," + verify);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    public void addHours(String d, String a, String tI, String tO) {
        int lineNum; 
        date = d;
        activity = status;
        timeIn = tI;
        timeOut = tO;
        hoursEarned = getDuration(timeIn, timeOut);
        verify = 0;
        String line = "";
        String[] splitLine = null;
        try {
            file.seek(0);
            line = file.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        }
        splitLine = line.split(",");
        totalHours = Double.parseDouble(splitLine[1]);
        logHours=Double.parseDouble(splitLine[2]);
        unlogHours=Double.parseDouble(splitLine[3]);
        lineNum=Integer.parseInt(splitLine[4])+1; 
        totalHours += hoursEarned;
        //saving account intfo to database 
        try {
            //file.seek(44);
            file.seek(2);
            System.out.println();
            file.writeBytes(totalHours+",0,0,00"+lineNum);
            file.seek(13+(lineNum-1)*36);
            //file.seek(49+(lineNum*36));
            System.out.println();
            if (lineNum < 10) {
                file.writeBytes("\r\n00" + lineNum + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + hoursEarned + "," + verify);
            } else if (lineNum < 100 && lineNum > 9) {
                file.writeBytes("\r\n0" + lineNum + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + hoursEarned + "," + verify);
            } else {
                file.writeBytes("\r\n" + lineNum + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + hoursEarned + "," + verify);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    public void displayingHours() {
        String line = "";
        String[] splitLine = null;
        System.out.println("Total Hours: " + totalHours + "\nLogged Hours: " + logHours + "\nUnlogged Hours: " + unlogHours + "\nDate\t\tActivity\tTime In\t\tTime Out\tHours");
        try {
            int tracker = 0;
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

    /**
     * Pads the string input from the front
     *
     * @param input
     * @param paddingLength
     * @return
     */
    public static String pad(String input, int paddingLength) {
        int remainingLength = paddingLength - input.length();
        String tempPad = "";
        for (int i = 0; i < remainingLength; i++) {
            tempPad = tempPad + " ";
        }
        return tempPad + input;
    }

    /**
     * Removes the space padding of the input at the front
     *
     * @param input
     * @return
     */
    public static String unPad(String input) {
        int tempUnPad = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                tempUnPad++;
            } else {
                break;
            }
        }
        String unPadded = input.substring(tempUnPad, input.length());
        return unPadded;
    }

    
   
    public static double getDuration(String timeIn, String timeOut) {
        double hours = 0;
        try {
            //convert string to date
            Date dateTimeIn = sdfClock.parse(timeIn);
            Date dateTimeOut = sdfClock.parse(timeOut);
            //get the difference between the dates
            hours = ((double) dateTimeOut.getTime() - (double) dateTimeIn.getTime()) / 3600000;
        } catch (Exception e) {
            System.out.println(e.getMessage() + "getDuration() messed up.");
        }
        return hours;
    }

    /**
     * @param studentNumber
     * @param activity
     * @param timeIn
     */
    public void storeTimeIn(String studentNumber, char activity, Date timeIn) {
        File tempStore = new File("TemporaryStorage.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(tempStore, true));
            //saving temp info into file
            pw.println(studentNumber + "," + pad(sdfDay.format(timeIn), 5) + "," + activity + "," + pad(sdfClock.format(timeIn), 5));
            pw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Tried to store a thing that couldn't be stored");
        }
    }

    public void storeTimeOut(String studentNumber, Date timeOut) {
        File tempStore = new File("TemporaryStorage.txt");
        try {
            Scanner s = new Scanner(tempStore);
            while (s.hasNextLine()) {
                String[] storedTimesIn = s.nextLine().split(",");
                if (storedTimesIn[0].equals(studentNumber)) {
                    //store the stuff in the actual file
                    addHours(storedTimesIn[1], storedTimesIn[3], storedTimesIn[2], sdfClock.format(timeOut));
                }
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
}
