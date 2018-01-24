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
import java.text.DecimalFormat;
import java.util.ArrayList;
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

    public final static SimpleDateFormat sdfClock = new SimpleDateFormat("hh:mm");
    public final static SimpleDateFormat sdfDay = new SimpleDateFormat("MM/dd/yyyy");

    //Allie
    public Hours(RandomAccessFile f) {
        file = f;
    }

    //Allie
    /**
     * This method is to record the user's the number of lines, first name, last
     * name, status, and total hours.
     *
     * @param s This is the status of the user.
     * @param fName This is the first name of the user.
     * @param lName This is the last name of the user.
     */
    public void top(String s, String fName, String lName) {
        //setting some of the global variables 
        status = s;
        firstName = pad(fName, 20);
        lastName = pad(lName, 20);
        //writing the information to the file 
        try {
            file.seek(0);
            System.out.println();
            file.writeBytes("  0," + firstName + "," + lastName + "," + status + ",  0.0,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    //Allie
    /**
     * This method adds hours to the user's file.
     *
     * @param d This is date the hours were completed on.
     * @param a This is the activity the user is performing.
     * @param tI This is the time the user signs in.
     * @param tO This is the time the user signed out.
     * @param v This is for whether the hours have been verified or not.
     */
    public void addHour(String d, String a, String tI, String tO, int v) {
        //setting some of the global variables 
        date = d;
        activity = a;
        timeIn = tI;
        timeOut = tO;
        hoursEarned = getDuration(timeIn, timeOut);
        verify = v;
        //reading in top of the file 
        String line = "";
        String[] splitLine = null;
        try {
            file.seek(0);
            line = file.readLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        }
        splitLine = line.split(",");
        //getting line number 
        int lineNum = Integer.parseInt(unPad(splitLine[0]));
        lineNum++;
        totalHours = Double.parseDouble(unPad(splitLine[4]));
        //adding hours earned to total hours 
        totalHours += hoursEarned;
        //writing hours information to the user's file 
        try {
            int e = 54 + (lineNum-1) * 39; 
            file.seek(93);
            String Line=file.readLine();
            file.seek(0);
            file.writeBytes(pad(("" + lineNum), 3));
            file.seek(48);
            file.writeBytes(pad(("" + totalHours), 5));
            file.seek(54 + (lineNum-1) * 37);
            file.writeBytes("\r\n" + pad(("" + lineNum), 3) + "," + date + "," + activity + "," + timeIn + "," + timeOut + "," + pad(("" + hoursEarned), 5) + "," + verify + ",");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }

    //Allie
    /**
     * This method displays the hours for the user.
     */
    public ArrayList<String> displayingHours() {
        //getting the info needed to create the top 
        String line = "";
        String[] splitLine = null;
        int totalLineNum;
        int lineNum;
        //creating an array list to store all the information that needs to be displayed 
        ArrayList<String> fileInfo = new ArrayList<String>();
        try {
            file.seek(0);
            line = file.readLine();
            splitLine = line.split(",");
            totalLineNum = Integer.parseInt(unPad(splitLine[0]));
            firstName = unPad(splitLine[1]);
            lastName = unPad(splitLine[2]);
            status = splitLine[3];
            totalHours = Double.parseDouble(unPad(splitLine[4]));
            //storing the information from the top into the array list
            fileInfo.add(firstName + " " + lastName);
            if (status.equals("R")) {
                fileInfo.add("Regular");
            } else if (status.equals("P")) {
                fileInfo.add("Purchaser");
            } else if (status.equals("L")) {
                fileInfo.add("Leader");
            } else if (status.equals("T")) {
                fileInfo.add("Teacher");
            }
            fileInfo.add("Total Hours: " + totalHours);
            //getting the info for the actual amount 
            fileInfo.add("  Date        Activity    Time In     Time Out    Hours Earned    Verified");
            for (int b = 0; b < totalLineNum; b++) {
                line = file.readLine();
                splitLine = line.split(",");
                lineNum = Integer.parseInt(unPad(splitLine[0]));
                date = splitLine[1];
                activity = splitLine[2];
                timeIn = splitLine[3];
                timeOut = splitLine[4];
                hoursEarned = Double.parseDouble(unPad(splitLine[5]));
                verify = Integer.parseInt(splitLine[6]);
                String info=""; 
                info=lineNum + "    " + date + "    ";
                if (activity.equals("R")) {
                    info=info+"Regular";
                } else if (activity.equals("P")) {
                    info=info+("Purchase");
                } else if (activity.equals("L")) {
                    info=info+("Leading");
                }
                info=info+("        " + timeIn + "        " + timeOut + "        " + hoursEarned);
                //getting the amount of logged vs. unlogged hours 
                if (verify == 0) {
                    info=info+("        No");
                    unlogHours++;
                } else {
                    info=info+("        Yes");
                    logHours++;
                }
                //store the amount info into the array list 
                fileInfo.add(info);
            }
            //storing the amount of logged vs. unlogged hours into the array list 
            fileInfo.add("Unlogged Hours: " + unlogHours); 
            fileInfo.add("Logged Hours:" + logHours);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        return fileInfo; 
    }

    //Allie
    /**
     * This method allows the user to verify hours
     *
     * @param lineNumber
     */
    public void verify(int lineNumber) {
        //creating variables 
        String line = "";
        String[] splitLine = null;
        int totalLineNum;
        int lineNum;
        try {
            //finding desired line 
            file.seek(0);
            line = file.readLine();
            splitLine = line.split(",");
            totalLineNum = Integer.parseInt(unPad(splitLine[0]));
            totalHours = Double.parseDouble(unPad(splitLine[4]));
            for (int b = 0; b < totalLineNum; b++) {
                line = file.readLine();
                splitLine = line.split(",");
                lineNum = Integer.parseInt(unPad(splitLine[0]));
                date = splitLine[1];
                activity = splitLine[2];
                timeIn = splitLine[3];
                timeOut = splitLine[4];
                hoursEarned = Double.parseDouble(unPad(splitLine[5]));
                verify = Integer.parseInt(splitLine[6]);
                if (lineNumber == lineNumber) {
                    //displaying line 
                    System.out.print("\n    Date        Activity    Time In        Time Out    Hours Earned");
                    System.out.print("\n" + lineNum + "    " + date + "    ");
                    if (activity.equals("R")) {
                        System.out.print("Regular");
                    } else if (activity.equals("P")) {
                        System.out.print("Purchase");
                    } else if (activity.equals("L")) {
                        System.out.print("Leading");
                    }
                    System.out.print("        " + timeIn + "        " + timeOut + "        " + hoursEarned);
                    //seeing if hours have already been verified 
                    if (verify == 0) {
                        //asking if user wants to verify hours 
                        Object[] options = {"Yes", "No"};
                        int dialogButton = JOptionPane.showOptionDialog(null, "Would you like to verify this?", "Verify", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        //indicating the hours have been verified 
                        if (dialogButton == 0) {
                            verify = 1;
                            file.seek(53 + (lineNum - 1) * 39 + 38);
                            file.writeBytes(verify + ",");
                            //deleting line if hours are not verified 
                        } else {
                            long size = file.length();
                            String[] lineFile = new String[totalLineNum - lineNum];
                            for (int c = 0; c < lineFile.length; c++) {
                                line = file.readLine();
                                lineFile[c] = line;
                            }
                            for (int c = 0; c < lineFile.length; c++) {
                                splitLine = lineFile[c].split(",");
                                lineNum = lineNumber + (1 * c);
                                file.seek(53 + (lineNum - 1) * 39);
                                file.writeBytes("\r\n" + pad(("" + lineNum), 3) + "," + splitLine[1] + "," + splitLine[2] + "," + splitLine[3] + "," + splitLine[4] + "," + splitLine[5] + "," + splitLine[6]);
                            }
                            totalHours -= hoursEarned;
                            file.seek(0);
                            file.writeBytes(pad(("" + (lineNum)), 3));
                            file.seek(48);
                            file.writeBytes(pad(("" + totalHours), 5));
                            file.setLength(size - 39);
                        }
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "This entry has already been verified so there is no need to verify it. ");
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }

    //Sherry
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

    //Sherry
    /**
     * 
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

    //Sherry
    /**
     * Gets the time difference in hours between two Dates. It will be returned as a double.
     *
     * @param timeIn
     * @param timeOut
     * @return
     */
    public static double getDuration(String timeIn, String timeOut) {
        double hours = 0;
        try {
            //convert string to date
            Date dateTimeIn = sdfClock.parse(timeIn);
            Date dateTimeOut = sdfClock.parse(timeOut);
            double valueTimeIn = (double) dateTimeIn.getTime() / 3600000;
            double valueTimeOut = (double) dateTimeOut.getTime() / 3600000;
            if (valueTimeOut >= valueTimeIn) {
                //get the difference between the dates
                hours = valueTimeOut / valueTimeIn;
            }
            //putting hours into proper format (with one decimal place)
            DecimalFormat df = new DecimalFormat("###.#"); 
            hours=Double.parseDouble(df.format(hours)); 
        } catch (Exception e) {
            System.out.println(e.getMessage() + "getDuration() messed up.");
        }
        return hours;
    }
}
