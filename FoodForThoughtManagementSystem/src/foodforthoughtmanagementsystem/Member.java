/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import static foodforthoughtmanagementsystem.Hours.sdfClock;
import static foodforthoughtmanagementsystem.Hours.sdfDay;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LLeNeve
 */
public class Member {

    private String user;
    private  String status, firstName, lastName;
    Hours b = null;
    RandomAccessFile file;

    //Allie
    public Member(String u) {
        //set user 
        user = u;
        //create or call file 
        try {
            file = new RandomAccessFile((getUser() + ".txt"), "rw");
            //file = new RandomAccessFile(("user.txt"), "rw");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
        //create hours class 
        b = new Hours(file);
        //newMember("P", "Allie", "LeNeve");
        //addHours("01/20/2018", "R", "01:00", "02:00", 0);
        //verify(1);
    }

    //Allie
    /**
     * This method helps to create a file for the new members.
     */
    public void newMember(String s, String fName, String lName) {
        //caliing the get top 
        b.top(s, fName, lName);
    }

    //Allie
    /**
     * This method adds a new hour to the user's file.
     *
     * @param d Date
     * @param a Activity
     * @param tI time in
     * @param tO time out
     * @param v verification
     */
    public void addHours(String d, String a, String tI, String tO, int v) {
        //call add hours
        b.addHour(d, a, tI, tO, v);
        //call display hours 
        //b.displayingHours();
    }
    
//    public void displayingHours(){
//      b.displayingHours();
//    }
    public void verify(int lineNumber) {
        b.verify(lineNumber);
    }
    
    
    
//Sherry
    /**
     * This stores the timeIn of a user.
     *
     * @param studentNumber
     * @param activity
     * @param timeIn
     */
    public void storeTimeIn(char activity, Date timeIn) {
        File tempStore = new File("TemporaryStorage.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(tempStore, true));
            //saving temp info into file
            pw.println(getUser() + "," + sdfDay.format(timeIn) + "," + activity + "," + sdfClock.format(timeIn));
            pw.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Tried to store a thing that couldn't be stored");
        }
    }
    
//Sherry
    /**
     * This signs the user out who was signed in before for the regular hour type.
     *
     * @param timeOut is the sign out time of the user
     */
    public void storeTimeOut(Date timeOut) {
        File tempStore = new File("TemporaryStorage.txt");

        try {
            Scanner s = new Scanner(tempStore);
            ArrayList<String> temporaryStorage = new ArrayList(); //takes in every line
            String current;
            while (s.hasNextLine()) {
                current = s.nextLine(); //used to keep track of the line being read
                String[] storedTimesIn = current.split(",");
                if (storedTimesIn[0].equals(getUser())) {
                    // public void addHour(String d, String a, String tI, String tO, int v)
                    addHours(storedTimesIn[1], storedTimesIn[2], storedTimesIn[3], sdfClock.format(timeOut), 0);
                } //erase the current line being used
                else {
                    temporaryStorage.add(current);
                }
            }
            // reprints the unused items
            try {
                PrintWriter pw = null;
                pw = new PrintWriter(new FileWriter(tempStore));
                //put the information back into file
                for (int i = 0; i < temporaryStorage.size(); i++) {
                    pw.println(temporaryStorage.get(i));
                }
                pw.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Tried to store a thing that couldn't be stored");
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Were you signed in?");
        }
    }
    //Sherry
/**
 * Checks to see if the person was already signed in.
 * @return 
 */
    public boolean wasSignedIn() {
        boolean signedIn = false;

        File tempStore = new File("TemporaryStorage.txt");

        try {
            Scanner s = new Scanner(tempStore);
            String current;
            while (s.hasNextLine()) {
                current = s.nextLine(); //used to keep track of the line being read
                String[] storedTimesIn = current.split(",");
                if (storedTimesIn[0].equals(getUser())) {
                signedIn=true;
                break;
                } 
            }
            //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Were you signed in?");
        }

        return signedIn;
    }

    //Shirley
//     public Participant(String firstName,String lastName, int studentNumber,double totalHours, char status) {
//       String fn= firstName;
//       String ln= lastName;
//       int sn= studentNumber;
//       double th= totalHours;
//       char s= status;
//       
//    }    
    //Shirley
    /**
     *
     * @param moreHours
     */
    public void updateHours(double moreHours) {
        String login = Panel01_LoginScreen.loginNumber;
        File user = new File(login);
        try {
            Scanner s = new Scanner(user);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
