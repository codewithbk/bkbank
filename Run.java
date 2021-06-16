package com.bkbank;
import java.io.Console;
import java.io.File;
import java.util.*;

public class Run {

    static boolean database = true;
    static boolean admin_panel = true;
    static boolean admin_secure = true;
    static boolean top_context = false;
    static int User_selection, User_Selection_Of_Home_Screen;
    static boolean Ready_to_Account_Screen = false;
    static boolean Ready_to_Home_Screen = false;
    static String User_Login_time = "0000/00/00 00:00:00";

    public static void main(String[] args) {

        // First stage
        /*
         * [FIRST STAGE --> 1] (Home Screen) --> If database are exist or admin of bank are exist then we stop the code. else.
         * [FIRST STAGE --> 2] (Collect & Generate Resource) --> We create all database or admin of bank.
         * [FIRST STAGE --> 3] (Check Database don't delete in run time) --> run time database delete.
         * */

        //  [FIRST STAGE --> 2] (Collect & Generate Resource) --> And also check;

        DatabaseManagement databaseManagement = new DatabaseManagement();
        if (!databaseManagement.CheckDataBase()) {
            database = false;
            top_context = true;
            if (databaseManagement.CreateDataBase()) {
                System.out.println("Database was created");
                database = true;
            }
        }
        if (!databaseManagement.CheckAdminPanel()) {
            admin_panel = false;
            top_context = true;
            if (databaseManagement.CreateAdminPanel()) {
                System.out.println("Admin Panel was Created");
                admin_panel = true;
            }
        }
        if (databaseManagement.CheckAdminDataInDataBase()) {
            admin_secure = false;
            top_context = true;
            if (databaseManagement.SetAdminUsernameOrPassword()) {
                System.out.println("Admin Account Created Successfully !!");
                admin_secure = true;
            }
        }

        //  [FIRST STAGE --> 1] (Home Screen) --> Now we have everything it time to go Home Screen

        if (database && admin_secure && admin_panel) {
            HomeScreen homeScreen = new HomeScreen();
            if (homeScreen.HomeScreenContext(top_context)) {
                User_selection = homeScreen.AccountScreenAccessSelection();
                if (User_selection > 0) {
                    Ready_to_Account_Screen = true;
                } else {
                    methods.stop_code("OPP's Try Again !!");
                }
            }
        }

        //  [FIRST STAGE --> 3] (Check Database don't delete in run time) --> run time database delete.

        if (database && admin_secure && admin_panel) {
            Thread database_checking = new Thread(new CheckDatabaseTimeToTime());
            database_checking.start();
        }


        // Second stage
        /* 1. --> (Checking database or admin are exist)
         *  [SECOND STAGE --> 1] (Log user) --> We log User to Main or there Account Screen
         */
        DatabaseMethods databaseMethods = new DatabaseMethods();

        // These for Register Screen
        if (Ready_to_Account_Screen) {
            if (User_selection == 1) {
                RegisterScreen registerScreen = new RegisterScreen();
                if (registerScreen.RegisterStart()) {
                    if (registerScreen.TakeUserInformationOfRegistration()) {
                        if (registerScreen.ValidateUserByCaptcha()) {
                            RegisterMethods.GenerateAccountNumber();
                            if (registerScreen.VerifyPhoneNumber()) {
                                if (registerScreen.SavedDataOfRegisterUser()) {
                                    if (RegisterMethods.checkUserAllReadyInDatabase()) {
                                        User_selection = 2;
                                        top_context = true;
                                    } else {
                                        methods.clear_screen();
                                        System.out.println("\n\n\nPhone Number Are Already Register !! \n\nAccount Registration Decline :( " + RegisterScreen.Datas);
                                        File file = new File(databaseMethods.Database_path() + "//" + RegisterScreen.AccountNumber);
                                        methods.delete_folder(file);
                                        file.delete();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        ResponseStatus responseStatus = new ResponseStatus();

        // These for Login Screen
        if (Ready_to_Account_Screen) {
            if (User_selection == 2) {
                if (responseStatus.show_login_intro(top_context)) {
                    LoginScreen loginScreen = new LoginScreen();
                    if (loginScreen.TakeInformationFromUser()) {
                        if (loginScreen.CheckUserByCaptcha()) {
                            if (loginScreen.FindUserInDatabase()) {
                                if (loginScreen.CheckUserDetail()) {
                                    if (loginScreen.CheckUserAccountOpenTimeValidate()) {
                                        Ready_to_Home_Screen = true;
                                        User_Login_time = methods.current_time();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // These for Admin Screen
        if (Ready_to_Account_Screen) {
            if (User_selection == 3) {
                System.out.println("Add these feature as soon as possible or improve more :(");
            }
        }

        // These for Error
        if (Ready_to_Account_Screen) {
            if (User_selection == 4) {
                System.out.println("ERROR !! --> IO EXCEPTION TRY AGAIN (INVALID SELECTION).");
            }
        }

        HomeScreen homeScreen = new HomeScreen();

        if (Ready_to_Home_Screen) {
            homeScreen.ShowInput(true);
        }

        while (true) {
            if(Ready_to_Home_Screen){
                if (responseStatus.show_home_screen_context()) {
                    User_Selection_Of_Home_Screen = homeScreen.UserOptionSelectionOnHomeScreen();
                }
            }
            if(Ready_to_Home_Screen){
                if (User_Selection_Of_Home_Screen == 1) {
                    if (Server.Balance_Query()) {
                        methods.clear_screen();
                        System.out.println("\n\nYour Account Balance is :- " + Server.Balance);
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                        methods.clear_screen();
                    } else {
                        System.out.println("Balance query fail \n\n");
                    }
                }
            }
            if(Ready_to_Home_Screen){
                if (User_Selection_Of_Home_Screen == 2) {
                    methods.clear_screen();
                    if(Server.Add_Money()){
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                        methods.clear_screen();
                    } else {
                        System.out.println("Add Money query fail. \n\n");
                    }
                }

            }
            if(Ready_to_Home_Screen){
                if(User_Selection_Of_Home_Screen == 3){
                    methods.clear_screen();
                    if(Server.Withdraw_Money()) {
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                        methods.clear_screen();
                    } else {
                        System.out.println("Withdraw query fail.\n\n");
                    }
                }
            }

            if (Ready_to_Home_Screen){
                if (User_Selection_Of_Home_Screen == 4) {
                    methods.clear_screen();
                    if(Server.Transfer_method()){
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                        methods.clear_screen();
                    } else {
                        System.out.println("Transfer query fail.\n\n");
                    }
                }
            }

            if(Ready_to_Home_Screen){
                if(User_Selection_Of_Home_Screen == 6){
                    if(Server.Money_Transition_history_show()){
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                        methods.clear_screen();
                    } else {
                        System.out.println("Withdraw query fail.\n\n");
                    }
                }
            }
            if(Ready_to_Home_Screen){
                if(User_Selection_Of_Home_Screen == 5){
                    if(Server.Mini_Statement()){
                        if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                            methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                            break;
                        }
                    } else {
                        System.out.println("Mini Statement query fail.\n\n");
                    }
                }
            }
            if(Ready_to_Home_Screen){
                if (User_Selection_Of_Home_Screen == 7) {
                    methods.clear_screen();
                    System.out.println("\nInvalid Input\n");
                    if(!methods.Selection_Input("Invalid Selection","\nPress 1 to GO Home Screen and 2 to stop: ",'1','2')){
                        methods.stop_code("Bye !! " + LoginScreen.R_UserName + " Last Login time :- " + User_Login_time);
                        break;
                    }
                    methods.clear_screen();
                }

            }

        }
    }
}
