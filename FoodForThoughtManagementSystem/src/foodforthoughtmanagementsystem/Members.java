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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LLeNeve
 */
public class Members {
    String user, status; 
    File file; 
    RandomAccessFile r; 
    public Members(String u){
        user=u; 
        file = new File (user+".txt");
        try {
            r = new RandomAccessFile ((user+".txt"),"rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            boolean work = file.createNewFile(); 
            if (work){
                newMember("Participant");
            } else {
                existingMember();
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
	    e.printStackTrace();
        }
    }
    public void newMember (String s){
        status=s; 
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
    public void existingMember (){
        Hours a = new Hours(file, status); 
        a.addHours("13/01/2018", "1:00", "2:00", 1);
        a.displayingHours();
    }
}
