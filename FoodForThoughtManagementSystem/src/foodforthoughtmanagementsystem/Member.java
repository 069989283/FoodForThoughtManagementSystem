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
public class Member {
    String user, status, firstName, lastName; 
    Hours b=null; 
    RandomAccessFile file; 
    public Member(String u) {
        user=u;
        try {
            file = new RandomAccessFile ((user+".txt"),"rw");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
        b = new Hours (file);
        //newMember(a);
        addHours("01/20/2018", "R", "01:00", "02:00", 0); 
    }
    public void newMember (Hours a){
        a.top("P", "Allie", "LeNeve");
    }
    public void addHours (String d, String a, String tI, String tO, int v){
        b.addHour(d, a, tI, tO, v);
    }
//     public Participant(String firstName,String lastName, int studentNumber,double totalHours, char status) {
//       String fn= firstName;
//       String ln= lastName;
//       int sn= studentNumber;
//       double th= totalHours;
//       char s= status;
//       
//    }    
//    public void updateHours(double moreHours){
//        String login= Panel01_LoginScreen.loginNumber;
//        File user= new File(login);
//        try {
//            Scanner s= new Scanner(user);
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
