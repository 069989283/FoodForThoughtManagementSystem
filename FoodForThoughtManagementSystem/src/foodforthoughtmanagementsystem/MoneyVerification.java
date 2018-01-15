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
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author 069989283
 */
public class MoneyVerification {
    String user, date, store, status; 
    boolean verify; 
    File file; 
    public MoneyVerification(String u){
        user=u; 
        file = new File (user+" money tracker.txt");
        try{
            boolean work = file.createNewFile(); 
            if (work){
                newMember();
            } else {
                existingMember("13/01/2018", "Superstore", false);
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
	    e.printStackTrace();
        }
    } 
    public void newMember (){
        status="Food Buyer"; 
        //setting up file writer 
        PrintWriter pw = null;
        try {
            pw = new PrintWriter (new FileWriter(file));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        } 
        //saving account intfo to database 
	pw.println(status);
        pw.close(); 
    }
    public void existingMember (String d, String inStore, boolean v){
        try {
            Scanner s = new Scanner(file);
            //get status 
            status = s.nextLine();
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        date=d; 
        store=inStore; 
        verify=v; 
        //setting up file writer 
        PrintWriter pw = null;
        try {
            pw = new PrintWriter (new FileWriter(file, true));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        } 
        //saving account intfo to database 
	pw.println(date+","+store+","+verify);
        pw.close(); 
        displayingHours(); 
    } 
    public void displayingHours (){
        String line="";
        String[] splitLine=null; 
        //scans file 
        Scanner s=null;
        System.out.println("Date\t\tStore\t\tVerified");
        try {
            int tracker=0; 
            s = new Scanner(file);
            while (s.hasNextLine()) {
                if (tracker==0){
                    line = s.nextLine();
                }
                else {
                    //sees if inputed username equals a username in the database 
                    line = s.nextLine();
                    splitLine = line.split(",");
                    date=splitLine[0]; 
                    store=splitLine[1];
                    verify=Boolean.parseBoolean(splitLine[2]);
                    if (verify==true){
                        System.out.println(date+"\t"+store+"\tYes"); 
                    } else {
                        System.out.println(date+"\t"+store+"\tNo"); 
                    }
                }
                tracker++; 
            }
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
}
