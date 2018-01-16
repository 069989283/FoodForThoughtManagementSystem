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
    int entry; 
    String user, date, store, status; 
    String line="";
    String[] splitLine=null; 
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
                existingMember("13/01/2018", "Superstore");
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
    public void existingMember (String d, String inStore){
        try {
            Scanner s = new Scanner(file);
            //get status 
            status = s.nextLine();
            while (s.hasNextLine()) {
                line = s.nextLine();
                splitLine = line.split(",");
            }
            entry=(int)Double.parseDouble(splitLine[0])+1;
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        date=d; 
        store=inStore; 
        verify=false; 
        //setting up file writer 
        PrintWriter pw = null;
        try {
            pw = new PrintWriter (new FileWriter(file, true));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        } 
        //saving account intfo to database 
	pw.println(entry+","+date+","+store+","+verify);
        pw.close(); 
        displayingHours(); 
    } 
    public void displayingHours (){ 
        //scans file 
        Scanner s=null;
        System.out.println("\tDate\t\tStore\t\tVerified");
        try {
            s = new Scanner(file);
            line = s.nextLine();
            while (s.hasNextLine()) {
                //sees if inputed username equals a username in the database 
                line = s.nextLine();
                splitLine = line.split(",");
                entry=(int)Double.parseDouble(splitLine[0]); 
                date=splitLine[1]; 
                store=splitLine[2];
                verify=Boolean.parseBoolean(splitLine[3]);
                if (verify==true){
                    System.out.println(entry+".\t"+date+"\t"+store+"\tYes"); 
                } else {
                    System.out.println(entry+".\t"+date+"\t"+store+"\tNo"); 
                }
            }
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
    public void verifying (int lineNumber){
        //scans file 
        Scanner s=null;
        line = s.nextLine();
        PrintWriter pw = null;
        try {
            s = new Scanner(file);
            line = s.nextLine();
            status=line; 
            try {
                pw = new PrintWriter (new FileWriter(file));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "There was an Error. ");
            } 
            pw.println(status);
            while (s.hasNextLine()) {
                //sees if inputed username equals a username in the database 
                line = s.nextLine(); 
                splitLine = line.split(",");
                entry=Integer.parseInt(splitLine[0]);
                if (entry==lineNumber){
                    verify=Boolean.parseBoolean(splitLine[3]);
                    if (verify==true){
                        JOptionPane.showMessageDialog(null, "This entry has already been verified so there is no need to verify it. ");
                    } else {
                        System.out.println("\tDate\t\tStore\t\tVerified");
                        date=splitLine[1]; 
                        store=splitLine[2];
                        System.out.println(entry+".\t"+date+"\t"+store+"\tNo"); 
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        JOptionPane.showConfirmDialog (null, "Would you like to verify this?","Question",dialogButton);
                        if(dialogButton == JOptionPane.YES_OPTION){
                            verify=true; 
                            pw.println(entry+","+date+","+store+","+verify);
                        } 
                    }
                } else {
                    pw.println(line);
                }
            }
            pw.close(); 
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
    }
}
