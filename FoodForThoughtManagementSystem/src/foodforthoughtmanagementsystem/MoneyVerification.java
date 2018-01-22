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
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author 069989283
 */
public class MoneyVerification {
    int verify; 
    String user, date, store, status; 
    double amount, totalAmount; 
    RandomAccessFile file;
    
    public MoneyVerification(String u){
        user=u; 
        try {
            file = new RandomAccessFile((user+" money tracker.txt"), "rw");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    } 
    
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
        //adding hours earned to total hours 
        totalAmount += amount; 
        //writing hours information to the user's file 
        try {
            file.seek(0);
            file.writeBytes(pad((""+lineNum), 3));
            file.seek(4);
            file.writeBytes(pad((""+totalAmount), 7));
            file.seek(12+(lineNum-1)*20);
            file.writeBytes("\r\n" + pad((""+lineNum), 3) + "," + date + "," + pad(store,10) + "," + pad((""+amount),6) + "," + verify+",");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
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
                    System.out.print("\n\tDate\t\tActivity\tTime In\t\tTime Out\tHours Earned");
                    System.out.print("\n"+lineNum+"\t"+date + "\t");
                    if (activity.equals("R")){
                        System.out.print("Regular"); 
                    } else if (activity.equals("P")){
                        System.out.print("Purchase"); 
                    } else if (activity.equals("L")){
                        System.out.print("Leading"); 
                    } 
                    System.out.print("\t\t" + timeIn + "\t\t" + timeOut + "\t\t" + hoursEarned);
                    //seeing if hours have already been verified 
                    if (verify==0){
                        //asking if user wants to verify hours 
                        Object[] options = {"Yes","No"};
                        int dialogButton = JOptionPane.showOptionDialog(null, "Would you like to verify this?","Verify",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
                        //indicating the hours have been verified 
                        if(dialogButton == 0){
                            verify=1; 
                            file.seek(53+(lineNum-1)*39+38);
                            file.writeBytes(verify+",");
                        //deleting line if hours are not verified 
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
                                file.seek(53+(lineNum-1)*39);
                                file.writeBytes("\r\n"+pad((""+lineNum), 3)+","+splitLine[1]+","+splitLine[2]+","+splitLine[3]+","+splitLine[4]+","+splitLine[5]+","+splitLine[6]);
                            }
                            totalHours-=hoursEarned;
                            file.seek(0);
                            file.writeBytes(pad((""+(lineNum)), 3));
                            file.seek(48);
                            file.writeBytes(pad((""+totalHours), 5));
                            file.setLength(size-39);
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
}
