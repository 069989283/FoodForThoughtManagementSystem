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
    double amount; 
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
        entry=findNumberOfEntries(); 
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
        String [] lineFile = new String[findNumberOfEntries()];
        int lineNum=0; 
        Scanner s=null;
        PrintWriter pw = null;
        try {
            s = new Scanner(file);
            line = s.nextLine();
            //status=line;
            lineFile[lineNum]=line; 
            while (s.hasNextLine()) {
                lineNum++; 
                line = s.nextLine(); 
                splitLine = line.split(",");
                entry=Integer.parseInt(splitLine[0]);
                if (entry==lineNumber){
                    verify=Boolean.parseBoolean(splitLine[3]);
                    if (verify==true){
                        JOptionPane.showMessageDialog(null, "This entry has already been verified so there is no need to verify it. ");
                        lineFile[lineNum]=(entry+","+date+","+store+","+verify); 
                    } else {
                        System.out.println("\tDate\t\tStore\t\tVerified");
                        date=splitLine[1]; 
                        store=splitLine[2];
                        System.out.println(entry+".\t"+date+"\t"+store+"\tNo"); 
                        //Custom button text
                        Object[] options = {"Yes","No"};
                        int dialogButton = JOptionPane.showOptionDialog(null, "Would you like to verify this?","Verify",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
                        if(dialogButton == 0){
                            verify=true; 
                            lineFile[lineNum]=(entry+","+date+","+store+","+verify); 
                        } else {
                            verify=false; 
                            lineFile[lineNum]=(entry+","+date+","+store+","+verify);
                        }
                    }
                } else {
                    lineFile[lineNum]=line; 
                }
            } 
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        try {
            pw = new PrintWriter (new FileWriter(file));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an Error. ");
        } 
        for (int a=0; a<=lineNum; a++){
            pw.println(lineFile[a]);
        }
        pw.close();
    }
    private int findNumberOfEntries (){
        int Entry=0; 
        try {
            Scanner s = new Scanner(file);
            //get status 
            status = s.nextLine();
            while (s.hasNextLine()) {
                line = s.nextLine();
                splitLine = line.split(",");
            }
            Entry=(int)Double.parseDouble(splitLine[0])+1;
        //catches errors and displays error box 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        return Entry; 
    }
}
