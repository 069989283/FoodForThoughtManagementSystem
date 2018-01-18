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
    public Members(String u) throws IOException{
        user=u; 
        try {
            file.getFD();
        } catch (NullPointerException ex) {
            newMember("Participant"); 
        }
    }
    public void newMember (String s){
        status=s; 
        try {
                file = new RandomAccessFile ((user+".txt"),"rw");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex1);
            }
        RandomAccessFile raf=null;
        try {
            raf = new RandomAccessFile("test1.txt", "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            raf.writeBytes("This is an example");
        } catch (IOException ex) {
            Logger.getLogger(Members.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void existingMember (){
        Hours a = new Hours(file, status); 
        a.addHours("13/01/2018", "1:00", "2:00", 1);
        a.displayingHours();
    }
}
