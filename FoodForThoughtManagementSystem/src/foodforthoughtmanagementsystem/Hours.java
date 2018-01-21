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
    String date, activity, timeIn, timeOut, status, firstName, lastName;
    int verify;

    public static SimpleDateFormat sdfClock = new SimpleDateFormat("hh:mm");
    public static SimpleDateFormat sdfDay = new SimpleDateFormat("MM/dd/yyyy");

    public Hours(RandomAccessFile f){
        file = f;
    } 
    public void top (String s, String fName, String lName){
        status=s; 
        firstName=pad(fName, 20); 
        lastName=pad(lName, 20);
        try {
            file.seek(0);
            System.out.println();
            file.writeBytes("  0,"+firstName+","+lastName+","+status+",  0.0");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    public void addHour(String d, String a, String tI, String tO, int v) {
        date = d;
        activity = a;
        timeIn = tI;
        timeOut = tO;
        hoursEarned = getDuration(timeIn, timeOut);
        verify = v;
        String line = "";
        String[] splitLine = null;
        try {
            file.seek(0);
            line = file.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        }
        splitLine = line.split(",");
        int lineNum = Integer.parseInt(unPad(splitLine[0])); 
        lineNum++; 
        totalHours = Double.parseDouble(unPad(splitLine[4]));
        totalHours += hoursEarned; 
        try {
            file.seek(0);
            file.writeBytes(pad((""+lineNum), 3));
            file.seek(48);
            file.writeBytes(pad((""+totalHours), 5));
            file.seek(53+lineNum*39);
            file.writeBytes("\r\n" + pad((""+lineNum), 3) + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + pad((""+hoursEarned),5) + "," + verify+",");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    public void displayingHours() {
        String line = "";
        String[] splitLine = null;
        int totalLineNum; 
        int lineNum; 
        try {
            file.seek(0);
            line = file.readLine();
            splitLine = line.split(",");
            totalLineNum = Integer.parseInt(unPad(splitLine[0])); 
            firstName = unPad(splitLine[1]); 
            lastName = unPad(splitLine[2]);
            status = splitLine[3]; 
            totalHours = Double.parseDouble(unPad(splitLine[4]));
            if (status.equals("P")){
                System.out.println(firstName+" "+lastName+"\nParticipant\nTotal Hours: "+totalHours);
            }
            System.out.print("\n\tDate\t\tActivity\tTime In\t\tTime Out\tHours Earned\tVerified");
            for (int b=0; b<totalLineNum; b++){
                line = file.readLine();
                splitLine = line.split(",");
                lineNum = Integer.parseInt(unPad(splitLine[0])); 
                date = splitLine[1];
                activity = splitLine[2];
                timeIn = splitLine[3];
                timeOut = splitLine[4];
                hoursEarned = Double.parseDouble(unPad(splitLine[5]));
                verify=Integer.parseInt(splitLine[6]);
                System.out.print("\n"+lineNum+"\t"+date + "\t");
                if (activity.equals("R")){
                    System.out.print("Regular"); 
                }
                System.out.print("\t\t" + timeIn + "\t\t" + timeOut + "\t\t" + hoursEarned); 
                if (verify==0){
                    System.out.print("\t\tNo");
                } else {
                    System.out.print("\t\tYes");
                }
            }
        } catch (IOException ex) {
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
            pw.println(studentNumber + "," + sdfDay.format(timeIn) + "," + activity + "," + sdfClock.format(timeIn));
            pw.close();
        } catch (IOException ex) {
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
                    addHour(storedTimesIn[1], storedTimesIn[3], storedTimesIn[2], sdfClock.format(timeOut), 1);
                }
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
}
