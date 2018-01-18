/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import foodforthoughtmanagementsystem.panels.Panel01_LoginScreen;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 340745868
 */
public class TestLogin {
String a="33";
for(int i=0; i< a.length(); i++){
    if(a.charAt(i) >100){
        break;
    } 
}
        File file= new File (a+".txt");
        boolean exists= file.createNewFile();
            if (exists){
                System.out.println("no");
            } else {
                System.out.println("in");
            }
}
