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
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LLeNeve
 */
public class Members {
    String user, status; 
    RandomAccessFile file; 
    public Members(String u) {
        user=u; 
        newMember("P");
        Hours a = new Hours (file, status);
        a.addFirstHours("13/01/2018", "R", "01:00", "02:00");
        a.addHours("13/01/2018", "R", "01:00", "02:00");
    }
    public void newMember (String s){
        status=s; 
        try {
            file = new RandomAccessFile ((user+".txt"),"rw");
            file.seek(0);
            System.out.println();
            //file.writeChars(status+"000");
            file.writeBytes(status+",0.0,0,0,000");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }
    public void existingMember (){
        //Hours a = new Hours(file, status); 
        //a.addHours("13/01/2018", "1:00", "2:00", 1);
        //a.displayingHours();
    }
     public Participant(String firstName,String lastName, int studentNumber,double totalHours, char status) {
       String fn= firstName;
       String ln= lastName;
       int sn= studentNumber;
       double th= totalHours;
       char s= status;
       
    }    
    public void updateHours(double moreHours){
        String login= Panel01_LoginScreen.loginNumber;
        File user= new File(login);
        try {
            Scanner s= new Scanner(user);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
