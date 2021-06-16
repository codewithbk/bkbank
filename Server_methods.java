package com.bkbank;

import java.io.*;
import java.nio.file.*;

public class Server_methods {
    DatabaseMethods databaseMethods = new DatabaseMethods();

    public boolean Create_Balance_file() {
        try {
            File file = new File(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Balance.txt");
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
            file.createNewFile();
            FileWriter fr = new FileWriter(file, true);
            fr.write(SecurityEncryptionDecryptionAES.encrypt("00", Data_key.get(0)));
            fr.close();
            return true;
        } catch (Exception e) {
            methods.stop_code("405 -> Server on Heavy load try again after sometime");
        }
        return false;
    }

    public boolean Create_Add_And_Withdraw_Money_Transition() {
        try {
            File file = new File(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//Add_And_Withdraw_Money_Transition.txt");
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + LoginScreen.Account_Number + "//User_key.txt"));
            file.createNewFile();
            FileWriter fr = new FileWriter(file, true);
            fr.write("");
            fr.close();
            return true;
        } catch (Exception e) {
            methods.stop_code("405 -> Server on Heavy load try again after sometime");
        }
        return false;
    }

    public static boolean Money_Transition_history_for_transfer_money(String file_path, String key_path, String past_Balance,String Current_Balance, String Money,String Action){
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        try{
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(key_path + "//User_key.txt"));
            long transition_id = methods.generateRandom(16);
            int transitionId = (int) transition_id;
            String data = Action + " on " + methods.current_time() + " Your Balance Will be " + "'" + past_Balance + "'" + " (Modified) to --> " + "'" + Current_Balance + "'" + " Your " + "'" + Action + "'" + " is " + "'" + Money + "'" + " . " + " Transition id will be :- " + "'" + Math.abs(transitionId) + "'";
            methods.file_create_and_write(file_path ,SecurityEncryptionDecryptionAES.encrypt(data,Data_key.get(0)) + "\n","//Add_And_Withdraw_Money_Transition.txt");
            return true;
        } catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    public boolean Cut_Balance(String Account_Number, int How_Much, String which_account) {
        try {
            java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//Balance.txt"));
            java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//User_key.txt"));
            String Balance = SecurityEncryptionDecryptionAES.decrypt(Data.get(0), Data_key.get(0));
            int Current_Balance = Integer.parseInt(Balance);
            Current_Balance = Current_Balance - How_Much;
            FileWriter myWriter = new FileWriter(databaseMethods.Database_path() + "//" + Account_Number + "//Balance.txt");
            myWriter.write(SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Current_Balance), Data_key.get(0)));
            myWriter.close();
            Money_Transition_history_for_transfer_money(databaseMethods.Database_path() + "//" + Account_Number,databaseMethods.Database_path() + "//" + Account_Number,Balance, String.valueOf(Current_Balance),String.valueOf(How_Much),"You Gave to " + which_account);

            return true;
        } catch (NoSuchFileException e) {
            System.out.println("These Account will be not used any time. No transition. \nSo it dismiss at the time. \nActivate Account :- By Just Use & Or Login");
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }



    public boolean Transfer_Balance(String Account_Number, int How_Much, String which_account) {
        Server_methods server_methods = new Server_methods();
        DatabaseMethods databaseMethods = new DatabaseMethods();
        File file = new File(databaseMethods.Database_path() + "\\" + LoginScreen.Account_Number + "\\Balance.txt");
        if (!file.exists()) {
            server_methods.Create_Balance_file();
        }
        if (file.exists()) {
            try {
                java.util.List<java.lang.String> Data = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//Balance.txt"));
                java.util.List<java.lang.String> Data_key = Files.readAllLines(Paths.get(databaseMethods.Database_path() + "//" + Account_Number + "//User_key.txt"));
                String Balance = SecurityEncryptionDecryptionAES.decrypt(Data.get(0),Data_key.get(0));
                int Current_Balance = Integer.parseInt(Balance);
                Current_Balance = How_Much + Current_Balance;
                FileWriter myWriter = new FileWriter(databaseMethods.Database_path() + "//" + Account_Number + "//Balance.txt");
                myWriter.write(SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Current_Balance),Data_key.get(0)));
                myWriter.close();
                Money_Transition_history_for_transfer_money(databaseMethods.Database_path() + "//" + Account_Number,databaseMethods.Database_path() + "//" + Account_Number,Balance,String.valueOf(Current_Balance),String.valueOf(How_Much),"You Received From " + which_account);
                return true;
            } catch (NoSuchFileException e) {
                System.out.println("These Account will be not used any time. No transition. \nSo it dismiss at the time. \nActivate Account :- By Just Use & Or Login");
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        return false;
    }
}