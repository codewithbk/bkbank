package com.bkbank;

public class ResponseStatus {

    /*{PUBLIC METHOD}
     * ALL METHOD INSIDE THESE CLASS ARE PUBLIC USE MEAN THE ONLY USE ONE TIME AND FOR ONE PURPOSE !!!
     * All FUNCTION ARE ONLY SHOW WHAT HAPPEN BEHIND THE SCENE AS A TEXT ON [CMD].
     * ALL FILE RESPONSE ARE LINK HERE ! BY THIS CLASS
     */

    // --> Method of [DATABASE] start from here

    // -> {PUBLIC METHOD --> database} It's method run --> (when we Create database show status to User)
    public void show_status_of_database_creating(String Database_path){
        methods.delay(500);
        System.out.println("HEY DEAR !! \nWE NOT FOUND ANY (DATABASE) IN YOUR COMPUTER!!");
        methods.delay(2000);
        System.out.println("PLEASE WAIT!\nWe work to solve this problem");
        methods.delay(2000);
        System.out.println("\nCREATING DATABASE - 100%");
        methods.delay(2000);
        System.out.println("CONNECTING DATABASE - 100%");
        methods.delay(3000);
        methods.clear_screen();
        System.out.println("PROBLEM SOLVE!! DATABASE CREATED IN (9.2)s -> 100% on " + Database_path);
    }

    // -> {PUBLIC METHOD --> database} It's method run --> (when we Create admin panel show status to User)
    public void show_status_of_adminPanel_creating(String Database_path){
        System.out.println("ERROR !!! FOUND :- ADMIN PANEL NOT SET :(");
        methods.delay(2200);
        System.out.println("CREATING (ADMIN PANEL) IN YOUR COMPUTER!!");
        methods.delay(2000);
        System.out.println("PLEASE WAIT!\n");
        methods.delay(2000);
        System.out.println("FINDING DATABASE - 100%");
        methods.delay(1900);
        System.out.println("CONNECTING DATABASE - 100%");
        methods.delay(2500);
        System.out.println("CREATING ADMIN PANEL - 100%");
        methods.delay(1000);
        System.out.println("ADMIN PANEL CREATED!! PANEL CREATED IN (6.9)s -> 100% on " + Database_path + "/ADMIN.txt");
        methods.delay(1000);
        System.out.println("(IMPORTANT NOTE !!) \nWE CREATED ADMIN PANEL IN (9.9)s -> 100%  AND SAVE THERE KEY ON !!  on " + Database_path + "/ADMIN_key_.txt" + " PLEASE SAVE AND DOT DELETE IT !!!");
        System.out.println("(IMPORTANT NOTE !!) \nWE CREATED ADMIN PANEL IN (9.9)s -> 100%  AND SAVE THERE KEY ON !!  on " + Database_path + "/ADMIN_key_.txt" + " PLEASE SAVE AND DOT DELETE IT !!!");
        System.out.println("(IMPORTANT NOTE !!) \nWE CREATED ADMIN PANEL IN (9.9)s -> 100%  AND SAVE THERE KEY ON !!  on " + Database_path + "/ADMIN_key_.txt" + " PLEASE SAVE AND DOT DELETE IT !!!");
        System.out.println("(IMPORTANT NOTE !!) \nWE CREATED ADMIN PANEL IN (9.9)s -> 100%  AND SAVE THERE KEY ON !!  on " + Database_path + "/ADMIN_key_.txt" + " PLEASE SAVE AND DOT DELETE IT !!!");
        System.out.println("(IMPORTANT NOTE !!) \nWE CREATED ADMIN PANEL IN (9.9)s -> 100%  AND SAVE THERE KEY ON !!  on " + Database_path + "/ADMIN_key_.txt" + " PLEASE SAVE AND DOT DELETE IT !!!");
        methods.delay(8000);
        methods.clear_screen();
    }

    // -> {PUBLIC METHOD --> database} It's method run --> (when admin create the account)
    public void show_status_admin_account_created(String key){
        methods.delay(1400);
        methods.clear_screen();
        System.out.println("Welcome Admin !! \nAccount was Ready To Created :) Please wait Don't quit");
        methods.delay(2000);
        System.out.println("Generated Your Database Key ");
        methods.delay(3000);
        System.out.println("Your random key generator - 100%");
        methods.delay(1000);
        methods.clear_screen();
        System.out.println("(KEEP REMEMBER) Your Random key was " + key + " ");
        methods.delay(1000);
        System.out.println("Please wait !!");

    }

    // --> Method of [DATABASE] end from here

    // --> Method of [BANK HOME SCREEN] start from here

    // -> {PUBLIC METHOD --> HomeScreen}
    public boolean show_status_of_bank_start_Screen(boolean Top_Context){
        if(Top_Context){
            methods.clear_screen();
        }
        System.out.println("\n\t\t\t\t\tBk Bank\t\t\t\t\n");
        methods.delay(2000);
        System.out.println("Log to continue...");
        methods.delay(1500);
        System.out.println("Press (R) to Register or (L) To login (A) to Admin Panel");
        methods.delay(2200);
        return true;
    }

    // --> Method of [BANK HOME SCREEN] end from here

    // --> Method of [REGISTER] start from here

    public boolean show_register_start_status(){
        methods.clear_screen();
        System.out.println("Hi User");
        methods.delay(200);
        System.out.println("Thanks For Choosing Bk Bank");
        methods.delay(700);
        System.out.println("Your Account Are Open in Minutes :)");
        methods.delay(800);
        System.out.println("Hey, Kindly enter your write detail");
        return true;
    }

    public boolean show_register_all_input_complete_message(){
        System.out.println("\n\n\nYou enter You all detail / check it time to validate your detail ! \n\n\n");
        methods.delay(4200);
        return true;
    }

    public boolean show_register_Phone_number_verification_check(){
        methods.clear_screen();
        System.out.println("\n\n\n Good to Go !! But You need to Verify Your Phone Number\n");
        methods.delay(3500);
        System.out.println("We will send a Otp in your register mobile number -> " + methods.string_password_mask(Long.toString(RegisterScreen.Phone_Number),4,0,6,"*"));
        return true;
    }

    public boolean show_registration_complete_message(){
        System.out.println("Otp verify Successfully !! \n\n\n\n\n\n\n\nPLEASE WAIT !! \n\n");
        methods.delay(1000);
        methods.clear_screen();
        methods.delay(3000);
        System.out.println("Hi " + RegisterScreen.User_Name + " !! You are now register !! It time to login. \n\n");
        methods.clear_screen();
        System.out.println("Your Account Number Was (IMPORTANT KEEP REMEMBER YOUR ACCOUNT NUMBER) :-  " + RegisterScreen.AccountNumber + "\n");
        System.out.println("Welcome in your Bk Bank !! \n\nPLEASE WAIT IT TIME TO LOGIN (KEEP REMEMBER YOUR ACCOUNT NUMBER FOR LOGIN) !!");
        methods.delay(12000);
        return true;
    }
    // --> Method of [REGISTER] start from here

    public boolean show_login_intro(boolean user_name){
        methods.clear_screen();
        if(user_name){
            System.out.println("\n\nHi " + RegisterScreen.User_Name + " It time to login : ");
        } else {System.out.println("\n\nHi User Please enter you detail : ");}
        return true;
    }

    public boolean show_home_screen(boolean show_name_and_description){
        methods.clear_screen();
        if(show_name_and_description){
            System.out.println("Welcome " + LoginScreen.R_UserName + " !! \n");
            System.out.println("Please chose a option : 1,2,3 e.t.c \n\n");
            methods.delay(800);
        }
        return true;
    }
    public boolean show_home_screen_context(){
        System.out.println("1). Balance Enquiry ");
        System.out.println("2). Add Money");
        System.out.println("3). Withdraw Money");
        System.out.println("4). Transfer Money");
        System.out.println("5). Mini Statement");
        System.out.println("6). Transition History : ");
        methods.delay(600);
        HomeScreen.User_Selection = methods.input("Invalid Input","Enter your option : ");
        return true;
    }

    public boolean show_login_in_waiting_status(){
        methods.clear_screen();
        System.out.println("Your Account Open In 24 Hours !! (PLEASE WAIT)");
        methods.delay(3000);
        System.out.println("\nHey " + LoginScreen.R_UserName + " your detail are Properly enter.");
        methods.delay(2000);
        System.out.println("It's time Now it our to Process it Process does not take to much time Only (24 Hours).");
        methods.delay(3000);
        System.out.println("\n\nYour Current Status :- ");
        System.out.println("Your Account Timing :- " + LoginScreen.R_Date);
        return true;
    }

    public boolean show_login_in_waiting_status_helpline_footer(){
        methods.delay(3000);
        System.out.println("\n\nCall on 1800-000-000 To see status");
        methods.stop_code("Login not Possible at time");
        return true;
    }
}
