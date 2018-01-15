/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author 069989283
 */
public class MoneyVerification {
    String user; 
    File file; 
    public MoneyVerification(String u){
        user=u; 
        file = new File (user+".txt");
        try{
            boolean work = file.createNewFile(); 
            if (work){
                newMember();
            } else {
                existingMember();
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
	    e.printStackTrace();
        }
    } 
    public void newMember (){
        
    }
    public void existingMember (){
        
    }
}
