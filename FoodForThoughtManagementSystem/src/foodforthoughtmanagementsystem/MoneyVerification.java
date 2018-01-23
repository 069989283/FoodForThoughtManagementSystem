/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import static foodforthoughtmanagementsystem.Hours.pad;
import static foodforthoughtmanagementsystem.Hours.unPad;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author 069989283
 */
public class MoneyVerification {
    int verify; 
    String user, date, store, status; 
    double amount, totalAmount, unloggedAmount, loggedAmount; 
    RandomAccessFile file;
    
    public MoneyVerification(String u){
        user=u; 
        try {
            file = new RandomAccessFile((user+" money tracker.txt"), "rw");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
        addEntry("01/20/2018", "Superstore", 50.00, 0); 
        display(); 
    } 
    //Allie
    /**
     * This method helps to create a file for the new food purchaser.
     */
    public void newMember (){
        //writing the information to the file 
        try {
            file.seek(0);
            System.out.println();
            file.writeBytes("  0,   0.00,");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }
    
    //Allie
    /**
     * This method adds an entry for the amount the user spent.
     *
     * @param d The date the user spent the money. 
     * @param s The store where the user spent the money. 
     * @param a The amount the user spent. 
     * @param v Whether that amount was verified or not. 
     */
    public void addEntry (String d, String s, double a, int v){ 
        String line="";
        String[] splitLine=null;
        int numLine; 
        date=d; 
        store=s;
        amount=a; 
        verify=v; 
        //setting up file writer 
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
        totalAmount = Double.parseDouble(unPad(splitLine[1]));
        //adding the amount spent to total amount spent 
        totalAmount += amount; 
        //writing amount information to the user's file 
        try {
            file.seek(0);
            file.writeBytes(pad((""+lineNum), 3));
            file.seek(4);
            file.writeBytes(pad((""+totalAmount), 7));
            file.seek(12+(lineNum-1)*35);
            String l = file.readLine();
            file.writeBytes("\r\n" + pad((""+lineNum), 3) + "," + date + "," + pad(store,10) + "," + pad((""+amount),6) + "," + verify+",");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    } 
    
    //Allie
    /**
     * This method is to display the information in the file. 
     * @return  Returns an array list of each line that should be displayed. 
     */
    public ArrayList<String> display (){ 
        //getting the info needed to create the top 
        String line = "";
        String[] splitLine = null;
        int totalNumLine;
        int lineNum;
        //creating an array list to store all the information that needs to be displayed 
        ArrayList<String> fileInfo = new ArrayList<String>();
        try {
            file.seek(0);
            line = file.readLine();
            splitLine = line.split(",");
            totalNumLine = Integer.parseInt(unPad(splitLine[0]));
            totalAmount = Double.parseDouble(unPad(splitLine[1]));
            //storing the display format into the array list 
            fileInfo.add("    Date        Store    Amount Paid    Verified");
            String lineInfo=""; 
            //getting the info for the actual hours and displaying it 
            for (int b = 0; b < totalNumLine; b++) {
                line = file.readLine();
                splitLine = line.split(",");
                lineNum = Integer.parseInt(unPad(splitLine[0]));
                date = splitLine[1];
                store = unPad(splitLine[2]); 
                amount = Double.parseDouble(unPad(splitLine[3]));
                verify = Integer.parseInt(splitLine[4]);
                lineInfo=(lineNum + "    " + date + "    "+store+"    $"+amount);
                if (verify == 0) {
                    lineInfo=lineInfo+("        No");
                    unloggedAmount++;
                } else {
                    lineInfo=lineInfo+("        Yes");
                    loggedAmount++;
                }
            }
            //store the entry info into the array list 
            fileInfo.add(lineInfo); 
            //store the amount of logged vs. unlogged hours into the array list  
            fileInfo.add("Unlogged Amount: " + unloggedAmount);
            fileInfo.add("Logged Amount:" + loggedAmount);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "You really messed up!");
        }
        return fileInfo; 
    }
    //Allie
    /**
     * This method allows the user to verify hours 
     * @param lineNumber 
     */
    public void verify (int lineNumber){
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
            totalAmount = Double.parseDouble(unPad(splitLine[1]));
            for (int b=0; b<totalLineNum; b++){
                line = file.readLine();
                splitLine = line.split(",");
                lineNum = Integer.parseInt(unPad(splitLine[0]));
                date = splitLine[1];
                store = unPad(splitLine[2]);
                amount = Double.parseDouble(unPad(splitLine[3]));
                verify=Integer.parseInt(splitLine[4]);
                if (lineNumber==lineNumber){
                    //displaying line 
                    System.out.print("\n    Date        Activity    Time In        Time Out    Hours Earned");
                    System.out.print("\n"+lineNum+"    "+date + "    " + store + "        $" + amount);
                    //seeing if amounts have already been verified 
                    if (verify==0){
                        //asking if user wants to verify amounts 
                        Object[] options = {"Yes","No"};
                        int dialogButton = JOptionPane.showOptionDialog(null, "Would you like to verify this?","Verify",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
                        //indicating the amounts have been verified 
                        if(dialogButton == 0){
                            verify=1; 
                            file.seek(53+(lineNum-1)*39+38);
                            file.writeBytes(verify+",");
                        //deleting line if amounts are not verified 
                        } else {
                            long size=file.length();
                            String [] lineFile=new String[totalLineNum-lineNum]; 
                            for (int c=0; c<lineFile.length; c++){
                                line = file.readLine();
                                lineFile[c]=line; 
                            }
                            for (int c=0; c<lineFile.length; c++){
                                splitLine = lineFile[c].split(",");
                                lineNum=lineNumber+(1*c); 
                                file.seek(12+(lineNum-1)*35);
                                file.writeBytes("\r\n"+pad((""+lineNum), 3)+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+",");
                            }
                            totalAmount-=amount;
                            file.seek(0);
                            file.writeBytes(pad((""+(lineNum)), 3));
                            file.seek(4);
                            file.writeBytes(pad((""+totalAmount), 7));
                            file.setLength(size-35);
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
    private static String pad(String input, int paddingLength) {
        int remainingLength = paddingLength - input.length();
        String tempPad = "";
        for (int i = 0; i < remainingLength; i++) {
            tempPad = tempPad + " ";
        }
        return tempPad + input;
    }

    //Sherry
    /**
     * Removes the space padding of the input at the front
     *
     * @param input
     * @return
     */
    private static String unPad(String input) {
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
}
