package com.bkbank;

import java.io.*;
import java.nio.file.*;
import java.nio.file.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.Period;
import java.text.*;
import java.util.Calendar;

public class LoginScreen {

    public static String Account_Number;
    public static String Password;

    static String R_UserName;
    static String R_Password;
    static String R_Pin;
    static String R_Phone_Number;
    static String R_Gender;
    static String R_Date;
    static String R_Age;
    RegisterScreen registerScreen = new RegisterScreen();

    RegisterMethods registerMethods = new RegisterMethods();

    DatabaseMethods databaseMethods = new DatabaseMethods();

    ResponseStatus responseStatus = new ResponseStatus();

//    static java.util.List<java.lang.String> Data;


    public boolean TakeInformationFromUser(){
        Account_Number = methods.input("Invalid Input","Enter Your Account Number : ");
        Password = methods.input("Invalid Input","Enter Password : ");
        return true;
    }

    public boolean CheckUserByCaptcha(){
        return registerScreen.ValidateUserByCaptcha();
    }

    public boolean FindUserInDatabase(){
        return registerMethods.checkUserAccountInDatabase(String.valueOf(Account_Number));
    }

    public boolean CheckUserDetail(){
        try {
            java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//User_detail.txt"));
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//User_key.txt"));
            if(Data.get(1).equals(Password)){
                R_Password = Data.get(1);
                R_UserName = SecurityEncryptionDecryptionAES.decrypt(Data.get(0),Data_key.get(0));
                R_Pin = SecurityEncryptionDecryptionAES.decrypt(Data.get(6),Data_key.get(0));
                methods.clear_screen();
                System.out.println("Hi " + R_UserName + " Welcome in Bk Bank !! ");
                String pin = methods.input("Invalid Input","Enter Your 6 Digit Pin : ");
                if(pin.equals(R_Pin)){
                    R_Gender = SecurityEncryptionDecryptionAES.decrypt(Data.get(2),Data_key.get(0));
                    R_Date = SecurityEncryptionDecryptionAES.decrypt(Data.get(3),Data_key.get(0));
                    R_Phone_Number = SecurityEncryptionDecryptionAES.decrypt(Data.get(4),Data_key.get(0));
                    R_Age = SecurityEncryptionDecryptionAES.decrypt(Data.get(5),Data_key.get(0));
                        return true;
                } else {
                    System.out.println("Invalid Pin :( \n Try Again ");
                    methods.stop_code(".....");
                }
            } else {
                System.out.println("Invalid Password :( \n Try Again ");
                methods.stop_code("......");
            }
        } catch (Exception e) {
            System.out.println("Connection time out !!");
        }

        return false;
    }

    public boolean CheckUserAccountOpenTimeValidate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String current_time = dtf.format(now);
        String user_register_time = R_Date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            Date date1 = format.parse(user_register_time);
            Date date2 = format.parse(current_time);
            long diff = date1.getTime() - date2.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);


            if(Math.abs(diffDays) >= 1){
                return true;
            } else {
                if(responseStatus.show_login_in_waiting_status()){
                    System.out.println("Total time happen In Account Opening :- " + Math.abs(diffDays) + " Days " + Math.abs(diffHours) + " Hours " + Math.abs(diffMinutes) + " Minutes " + Math.abs(diffSeconds) + " Second ");
                    responseStatus.show_login_in_waiting_status_helpline_footer();
                }
            }

        } catch (Exception e){
            System.out.println(e);
        }
            return false;
    }

}
