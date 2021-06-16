package com.bkbank;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class methods {

    // These will clear the screen
    public static void clear_screen(){
        for(int i = 0; i <= 200; i++){
            System.out.println("\n");
        }
    }

    // These will stop code or stop jvm on any error
    public static void stop_code(String message){
        if(message.length() > 2) {
            System.out.println(message);
        } else {
            System.out.println("Hey My Mistake ! Please Wait Our Server is Busy");
        }
        System.exit(0);
    }

    // These will we stop / hang code for some time
    public static void delay(int time){
        try{
            Thread.sleep(time);
        } catch (Exception e){
            stop_code("0");
        }
    }

    // These will we help to take input from user and just as a choicebox / checkbox
    public static boolean Selection_Input(String error,String print,char True_vale, char false_value){
        Scanner input = new Scanner(System.in);
        System.out.print(print);
        try {
            char selection = input.next().charAt(0);
            int check_selection_y = Character.compare(selection, True_vale);
            int check_selection_n = Character.compare(selection, false_value);
            if(check_selection_y == 0){
                return true;
            } else if (check_selection_n == 0){
                return false;
            } else {
                System.out.println(error);
            }
        } catch (Exception e){
            System.out.println(error);
        }
        return false;
    }

    // These will return true if string length verified byt give argument
    public static boolean length_check_string(String value,int min, int max,String error){
        boolean min_flag_s = false, max_flag_s = false;
        int string_value_length = 0;
        try {
            string_value_length = value.length();
        } catch (Exception e){
            System.out.println("PLEASE USE WORDS AND INTEGER IN USER NAME EX:- Rahul,sonam45,kitelover54,bkbanku e.t.c");
        }
        if(string_value_length > 1){
            if(string_value_length >= min){
                min_flag_s = true;
            }else {
                System.out.println(error);
            }
            if (string_value_length <= max){
                max_flag_s = true;
                if(min_flag_s){
                    return true;
                }
            } else {
                System.out.println(error);
            }
        } else {
            System.out.println(error);
        }
        return false;
    }

    // These will return true if long length verified byt give argument
    public static boolean length_check_long(long value,int min, int max,String error){
        boolean min_flag_s = false, max_flag_s = false;
        int long_value_length;
        long_value_length =  String.valueOf(value).length();
        if(long_value_length > 1){
            if(long_value_length >= min){
                min_flag_s = true;
            }else {
                System.out.println(error);
            }
            if (long_value_length <= max){
                max_flag_s = true;
                if(min_flag_s){
                    return true;
                }
            } else {
                System.out.println(error);
            }
        } else {
            System.out.println(error);
        }
        return false;
    }

    // These will take input on string and return it
    public static String input(String error,String print){
        Scanner input = new Scanner(System.in);
        System.out.print(print);
        try {
            String u_input = input.next();
            return u_input;
        } catch (Exception e){
            System.out.println(error);
        }
        return null;
    }

    // it gen random string key --> (sequence of char)
    public static String string_key_gen(int lengths){
       String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrsuvwxyz9876543210";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < lengths; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    // These will create a file and write something
    public static boolean file_create_and_write(String path, String data, String fileName){
        File file = new File(path + "\\" + fileName);
        FileWriter fr = null;
        try {
            // Below constructor argument decides whether to append or override
            fr = new FileWriter(file, true);
            fr.write(data);
            return true;
        } catch (IOException e) {
            stop_code("Error occurred" + e);
            e.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                stop_code("Error occurred" + e);
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String open_file_return_string(String path_with_file_name){
        try{
            DataInputStream dis = new DataInputStream (new FileInputStream (path_with_file_name));
            byte[] datainBytes = new byte[dis.available()];
            dis.readFully(datainBytes);
            dis.close();
            return new String(datainBytes, 0, datainBytes.length);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String string_password_mask(String value, int length, int start, int end, String symbol){
        String mask_data = "";
        try{
            String data = value.substring(start, end);
            for(int i = 0; i <= length; i++){
                mask_data += symbol;
            }
            return data + mask_data;
        } catch (Exception e){
            stop_code("ERROR TRY AGAIN !!!");
        }
        return "0";
    }

    public static char[] generate_random_number(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0; i< length ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

    public static long generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public static boolean delete_folder(File file){
       try {
           // store all the paths of files and folders present
           // inside directory
           for (File subfile : file.listFiles()) {

               // if it is a subfolder,e.g Rohan and Ritik,
               // recursiley call function to empty subfolder
               if (subfile.isDirectory()) {
                   delete_folder(subfile);
               }

               // delete files and empty subfolders
               subfile.delete();
           }
           return true;
       } catch (Exception e){
           System.out.println("Error !!");
       }
       return false;
    }

    public static String current_time(){
        Date date=new Date();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return df2.format(date);
    }

    public static boolean delete_File_content(String path){
        try{
            PrintWriter writer = new PrintWriter(path);
            writer.print("");
            writer.close();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
