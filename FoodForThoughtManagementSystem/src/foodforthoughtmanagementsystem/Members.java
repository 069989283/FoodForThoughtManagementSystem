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
    RandomAccessFile file; 
    public Members(String u) {
        user=u; 
        newMember("P");
        Hours a = new Hours (file, status);
        a.addHours("13/01/2018", "R", "1:00", "2:00", 1);
    }
    public void newMember (String s){
        status=s; 
        try {
            file = new RandomAccessFile ((user+".txt"),"rw");
            file.seek(0);
            System.out.println();
            //file.writeChars(status+"000");
            file.writeBytes(status+",0,0,0");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "There was an error. ");
        }
    }
    public void existingMember (){
        //Hours a = new Hours(file, status); 
        //a.addHours("13/01/2018", "1:00", "2:00", 1);
        //a.displayingHours();
    }
    public boolean fileExists (){
        File f = new File (user+".txt"); 
        if (f.exists()==true){
            return true; 
        } else {
            return false; 
        }
    }
}
