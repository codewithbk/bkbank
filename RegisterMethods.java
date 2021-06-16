package com.bkbank;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.nio.file.*;
import java.nio.file.*;

public class RegisterMethods {
    static java.util.List<java.lang.String> Data;

    public String String_input(String error, String print, boolean validate, boolean stop_on_error){
        Scanner input = new Scanner(System.in);
        System.out.print(print);
        String user_input = null;
        try{
            if(validate){
                if(input.hasNext("[A-Za-z0-9]*")) {
                    user_input = input.next();
                    return user_input;
                } else{
                    System.out.println("WARN !!! You enter a wrong input.");
                    methods.stop_code("");
                }
            } else {
                user_input = input.next();
                return user_input;
            }

        } catch (Exception e){
            System.out.println(error);
            if(stop_on_error){
                methods.stop_code("Input error Try agian");
            }
        }


        return user_input;

    }

    public int integer_input(String error, String print, boolean validate, boolean stop_on_error){
        Scanner input = new Scanner(System.in);
        System.out.print(print);
        int user_input = 0;
        try{
            if(validate){
                if(input.hasNext("[0-9]*")) {
                    user_input = input.nextInt();
                    return user_input;
                } else{
                    System.out.println("WARN !!! You enter a wrong input.");
                }
            } else {
                user_input = input.nextInt();
                return user_input;
            }

        } catch (Exception e){
            System.out.println(error);
            if(stop_on_error){
                methods.stop_code("Input error Try agian");
            }
        }
        return user_input;
    }

    public long long_input(String error, String print, boolean validate, boolean stop_on_error){
        Scanner input = new Scanner(System.in);
        System.out.print(print);
        long user_input = 0L;
        try{
            if(validate){
                if(input.hasNext("[0-9]*")) {
                    user_input =input.nextLong();
                    return user_input;
                } else{
                    System.out.println("WARN !!! You enter a wrong input.");
                }
            } else {
                user_input = input.nextLong();
                return user_input;
            }

        } catch (Exception e){
            System.out.println(error);
            if(stop_on_error){
                methods.stop_code("Input error Try agian");
            }
        }
        return user_input;
    }

    public boolean checkUserAccountInDatabase(String Account_Number){
      try {
          DatabaseMethods databaseMethods = new DatabaseMethods();
          File folder = new File(databaseMethods.Database_path() + "//" + Account_Number);
          return folder.isDirectory();
      } catch (Exception e){
          System.out.println("Try Again Connection time out !!");
      }
      return false;
      }

    public static void GenerateAccountNumber(){
        RegisterScreen.AccountNumber = methods.generateRandom(12);
    }

    public static boolean checkUserAllReadyInDatabase(){
        DatabaseMethods databaseMethods = new DatabaseMethods();
        String key = methods.open_file_return_string(databaseMethods.Database_path() + "//" + "ADMIN_key_(DON'TDELETEIT).txt");
        ArrayList<String> list = new ArrayList<String>();
        try{Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//UserPhoneNumberList.txt"));} catch (Exception e){System.out.println(e);}
        for(int i = 0; i < Data.size(); i++){
            list.add(SecurityEncryptionDecryptionAES.decrypt(Data.get(i),key));
        }
        int total = Collections.frequency(list, Long.toString(RegisterScreen.Phone_Number));
        if(total > 1){
             return false;
         } else {
             return true;
         }
//        return !list.contains(Long.toString((RegisterScreen.Phone_Number)));
    }
}
