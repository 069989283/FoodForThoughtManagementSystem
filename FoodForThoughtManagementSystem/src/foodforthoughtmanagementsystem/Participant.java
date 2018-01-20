/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 073685257
 */
public class Participant{ //extends Hours{

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
