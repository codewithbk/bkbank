package com.bkbank;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

public class Server {

    public static int Otp;
    public static String Balance = "0.0";
    public static List<String> FinalData = new ArrayList<String>();
    public static String Transfer_UserName, Transfer_Phone_Number,account_number,transfer_amount;
    public static int Current_Balance;

    DatabaseMethods databaseMethods = new DatabaseMethods();


    public static boolean sendOtp(String message, String number) {
        int status;
        boolean send;
        try
        {
            final String API ="AoYciTbr8t7wlf5xR43U0g9QPFuaI6v1pKMBkyZmeqJXGODdNjYCFz1fEqw9klxDPiyIGbga6dXMK3ZS";
            final String SEND_ID="FSTSMS";
            final String MESSAGE = URLEncoder.encode(message, "UTF-8");
            final String Language ="english";
            final String Route = "p";

            String Url="https://www.fast2sms.com/dev/bulk?authorization="+API+"&sender_id="+SEND_ID+"&message="+MESSAGE+"&language="+Language+"&route="+Route+"&numbers="+number;

            URL url = new URL(Url);

            HttpsURLConnection connection =(HttpsURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("cache-control", "no-cache");

            status = connection.getResponseCode();

            StringBuffer response = new StringBuffer();

            BufferedReader reading =new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while(true)
            {
                String line = reading.readLine();
                if(line == null)
                {
                    break;
                }
                response.append(line);
            }

            if(status == 200){
                return true;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean VerifyOtp(String user_input){
        if(user_input.equals(String.valueOf(Otp))){
            return true;
        }
        return false;
    }

    public static boolean Balance_Query(){

        Server_methods server_methods = new Server_methods();

        DatabaseMethods databaseMethods = new DatabaseMethods();

        File file = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Balance.txt");
        if(!file.exists()){
            server_methods.Create_Balance_file();
            System.out.println("Your Account Have No Balance :- 00");
            return true;
        }
        if(file.exists()){
            try {
                java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt"));
                java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
                Balance = SecurityEncryptionDecryptionAES.decrypt(Data.get(0),Data_key.get(0));

            } catch (Exception ignored){ }
            return true;
        }
        return false;
    }

    public static boolean Money_Transition_history(String past_Balance,String Current_Balance, String Money,String Action){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        try{
        java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
        long transition_id = methods.generateRandom(16);
        int transitionId = (int) transition_id;
        String data = Action + " on " + methods.current_time() + " Your Balance Will be " + "'" + past_Balance + "'" + " (Modified) to --> " + "'" + Current_Balance + "'" + " Your " + "'" + Action + "'" + " is " + "'" + Money + "'" + " . " + " Transition id will be :- " + "'" + Math.abs(transitionId) + "'";
        methods.file_create_and_write(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number ,SecurityEncryptionDecryptionAES.encrypt(data,Data_key.get(0)) + "\n","Add_And_Withdraw_Money_Transition.txt");
            return true;
        } catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    public static boolean Add_Money(){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        File file = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Balance.txt");
        File file_2 = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Add_And_Withdraw_Money_Transition.txt");
        if(!file.exists()){
            server_methods.Create_Balance_file();
        }

        if(!file_2.exists()){
            server_methods.Create_Add_And_Withdraw_Money_Transition();
        }
        if(file.exists()){
            try
            {
                java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt"));
                java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
                Balance = SecurityEncryptionDecryptionAES.decrypt(Data.get(0),Data_key.get(0));
                System.out.println("\t\tAdd Amount\n\n");
                System.out.println( "Your Current Balance is :- " + Balance);
                methods.delay(2000);
                int Current_Balance = Integer.parseInt(Balance);
                String Current_B = Balance;
                String Add_amount = methods.input("Invalid Input","Enter your amount : ");
                try {
                    Current_Balance = Integer.parseInt(Add_amount) + Current_Balance;
                }catch (Exception e){
                    System.out.println("WRONG INPUT !!! --> Enter a Number");
                }
                Balance = Integer.toString(Current_Balance);

                FileWriter myWriter = new FileWriter(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt");
                myWriter.write(SecurityEncryptionDecryptionAES.encrypt(Balance,Data_key.get(0)));
                myWriter.close();
                methods.delay(1500);
                System.out.println("Balance add Successfully !! :- " + Add_amount);
                Money_Transition_history(Current_B,Integer.toString(Current_Balance),Add_amount,"Add Money");
                return true;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else {
            methods.stop_code("405 -> Server on Heavy load try again after sometime");
        }
        return false;
    }

    public static boolean Withdraw_Money(){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();

        File file = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Balance.txt");
        File file_2 = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Add_And_Withdraw_Money_Transition.txt");
        if(!file.exists()){
            server_methods.Create_Balance_file();
            System.out.println("\nYour Account have no Balance to Withdraw.");
            return true;
        }
        if(!file_2.exists()){
            server_methods.Create_Add_And_Withdraw_Money_Transition();
        }
        if(file.exists()){
            try
            {
                java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt"));
                java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
                Balance = SecurityEncryptionDecryptionAES.decrypt(Data.get(0),Data_key.get(0));
                System.out.println("\t\tWithdraw Amount\n\n");
                System.out.println( "Your Current Balance is :- " + Balance);
                methods.delay(1000);
                int Current_Balance = Integer.parseInt(Balance);
                String Current_B = Balance;
                String Withdraw_amount = methods.input("Invalid Input","Enter your withdraw amount : ");
                try {
                    if(Integer.parseInt(Withdraw_amount) <= Current_Balance){
                        Current_Balance = Current_Balance - Integer.parseInt(Withdraw_amount);
                    } else {
                        System.out.println("You enter a greater Amount than in your Amount !!");
                        return false;
                    }
                }catch (Exception e){
                    System.out.println("WRONG INPUT !!! --> Enter a Number");
                }
                Balance = Integer.toString(Current_Balance);
                FileWriter myWriter = new FileWriter(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt");
                myWriter.write(SecurityEncryptionDecryptionAES.encrypt(Balance,Data_key.get(0)));
                myWriter.close();
                methods.delay(1500);
                System.out.println("Balance Withdraw Successfully !! :- " + Withdraw_amount);
                Money_Transition_history(Current_B,Integer.toString(Current_Balance),Withdraw_amount,"Withdraw Money");
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        } else {
            methods.stop_code("405 -> Server on Heavy load try again after sometime");
        }
        return false;
    }

    public static boolean Money_Transition_history_show(){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        File file_2 = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Add_And_Withdraw_Money_Transition.txt");
        if(!file_2.exists()){
            methods.clear_screen();
            System.out.println("You Does not do any transition.");
            return true;
        }
        try {
            List<String> fileData = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Add_And_Withdraw_Money_Transition.txt"));
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
            if(SecurityEncryptionDecryptionAES.decrypt(fileData.get(0), Data_key.get(0)) == ""){
                methods.clear_screen();
                System.out.println("You Does not do any transition.");
            }
            System.out.println(" \n Please wait !!! (Load Your Transition history) \n");
            methods.delay(2000);
            methods.clear_screen();
            int list_size = fileData.size();

            System.out.println("Transition History Start :- (PRESS ENTER TO SEE MORE AND 'q' TO EXIT)");
            methods.delay(800);

            for (int i = 0; i < list_size; i++) {
                Scanner scan = new Scanner(System.in);
                System.out.print("\n::");
                String str = scan.nextLine();
                str = str.toLowerCase();
                if(str.equals("q")){
                    methods.clear_screen();
                    System.out.println("\nYou Exit from Transition History.");
//                    methods.delay(6000);
                    return true;
                }
                methods.delay(400);
                System.out.println( i + "). " + " " + SecurityEncryptionDecryptionAES.decrypt(fileData.get(i), Data_key.get(0)));
            }
            System.out.println("\n\nThis is the only transition history in your account. \n\n");
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static boolean Mini_Statement(){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        File file_2 = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Add_And_Withdraw_Money_Transition.txt");
        if(!file_2.exists()){
            methods.clear_screen();
            System.out.println("You Does not do any transition.");
            return true;
        }
        try {
            List<String> fileData = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Add_And_Withdraw_Money_Transition.txt"));
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
            if(SecurityEncryptionDecryptionAES.decrypt(fileData.get(0), Data_key.get(0)) == ""){
                methods.clear_screen();
                System.out.println("You Does not do any transition.");
                return true;
            }

            System.out.println("\n\n\n(Load Your Mini Statement of Transition history) \n");
            int list_size = fileData.size();

            if(list_size > 10){
                list_size = 10;
            }

            for (int i = 0; i < list_size; i++) {
                methods.delay(400);
                System.out.println( i + "). " + " " + SecurityEncryptionDecryptionAES.decrypt(fileData.get(i), Data_key.get(0)));
            }
            return true;

        } catch (Exception e){
            return false;
        }

    }

    public static boolean Transfer_method(){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        RegisterMethods registerMethods = new RegisterMethods();

        System.out.println("Transfer Money Fast and Easy By Bk Bank. ");

        account_number = methods.input("Invalid Input","Enter the Account Number of Other User : ");
        boolean account_number_exist = registerMethods.checkUserAccountInDatabase(account_number);

        File file = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Balance.txt");
        if(!file.exists()){
            server_methods.Create_Balance_file();
            System.out.println("Your Account Have No Balance :- 00");
            return true;
        }else {
            if (!account_number.equals(LoginScreen.Account_Number)) {
                if (account_number_exist) {
                    try {
                        java.util.List<java.lang.String> AmountReceived = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + account_number + "//User_detail.txt"));
                        java.util.List<java.lang.String> DataKeyReceived = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + account_number + "//User_key.txt"));
                        java.util.List<java.lang.String> AmountSend = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt"));
                        java.util.List<java.lang.String> DataKeySend = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
                        Transfer_UserName = SecurityEncryptionDecryptionAES.decrypt(AmountReceived.get(0), DataKeyReceived.get(0));
                        Transfer_Phone_Number = SecurityEncryptionDecryptionAES.decrypt(AmountReceived.get(4), DataKeyReceived.get(0));
                        Transfer_Phone_Number = Transfer_Phone_Number.replaceAll("\\d(?=\\d{6})", "*");
                        String Balance = SecurityEncryptionDecryptionAES.decrypt(AmountSend.get(0), DataKeySend.get(0));
                        System.out.println("A.NO :- " + account_number);
                        System.out.println("User Name :- " + Transfer_UserName);
                        System.out.println("Phone No :- " + Transfer_Phone_Number);
                        System.out.println("\nCHECK TOP DEATIL ARE PROPER THAN TRANSFER MONEY.\n");
                        transfer_amount = methods.input("Invalid Input", "Enter Transfer Amount : ");
                        methods.clear_screen();
                        String pin = methods.input("Invalid Input", "\nEnter Your Pin : ");

                        if(pin.equals(LoginScreen.R_Pin)) {
                            if (Integer.parseInt(transfer_amount) <= Integer.parseInt(Balance)) {
                                if (server_methods.Cut_Balance(LoginScreen.Account_Number, Integer.parseInt(transfer_amount),LoginScreen.Account_Number)) {
                                    if (server_methods.Transfer_Balance(account_number, Integer.parseInt(transfer_amount),LoginScreen.Account_Number)){
                                        methods.delay(1000);
                                        System.out.println("Transfer Amount Please wait !!");
                                        methods.delay(3000);
                                        methods.clear_screen();
                                        System.out.println("Transfer Amount " + "'" + transfer_amount + "'" + " == " + "'" + LoginScreen.Account_Number + "'" + " ---> " + "'" + account_number + "'");
                                        methods.delay(1000);
                                        methods.clear_screen();
                                        System.out.println("Transfer Amount " + "'" + transfer_amount + "'" + "Successfully !!");
                                        return true;
                                    } else {
                                        System.out.println("\nError Occurred");
                                    }
                                } else {
                                    System.out.println("\nError Occurred");
                                }
                            } else {
                                System.out.println("You Have Enter a Greater amount that your Balance");
                            }
                        }else {
                            System.out.println("Pin does not match !!");
                        }
                    } catch (Exception ingnored){
                        System.out.println(ingnored);
                        return false;
                    }

                } else {
                    System.out.println("CHECK NUMBER:- THESE " + "'" + account_number + "'" + " ACCOUNT ARE NOT EXIST.");
                    return false;
                }
            } else {
                System.out.println("You Enter your own account");
                return false;
            }
        }
        return false;
    }

}
