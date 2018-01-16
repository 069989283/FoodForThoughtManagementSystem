/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodforthoughtmanagementsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 069989283
 */
public class FoodForThoughtManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Members a = new Members("069989283");
        MoneyVerification b = new MoneyVerification("069989283");

        Date d = new Date();

        SimpleDateFormat y = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");

        System.out.println(y.format(d));
        System.out.println(s.format(d));

        try {
            Date d2 = y.parse("01/16/2018"); //test date
            
            double hours = ((double) d2.getTime() - (double) d.getTime()) / 3600000; //code to get hours
            System.out.println(hours);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

    }

}
