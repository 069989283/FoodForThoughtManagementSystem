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
    RandomAccessFile file; 
    public Member(String u) {
        user=u;
        try {
            file = new RandomAccessFile ((user+".txt"),"rw");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
        Hours a = new Hours (file);
        //newMember(a);
        a.addHours("13/01/2018", "R", "1:00", "2:00", 0);
        a.addHours("13/01/2018", "R", "13:00", "14:00", 1);
    }
    public void newMember (Hours a){
        a.top("P", "Allie", "LeNeve");
    }
    public void existingMember (){
        //Hours a = new Hours(file, status); 
        //a.addHours("13/01/2018", "1:00", "2:00", 1);
        //a.displayingHours();
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
