package com.bkbank;
import java.io.*;

public class DatabaseManagement {

    /* [DATABASE]
    *  IN THESE ALL DATABASE FUNCTION ARE TOGETHER
    *  ALL TYPE OF OPERATION PERFORM ON DATABASE ARE HAPPEN HERE
    *  IT WAS A PUBLIC CLASS ANY ONE CAN USE THEM
    *  AND IT PERFORM ALL ACTION ON DATABASE
    * */

    // Calling DatabaseMethods --> (To use all Database methods)
    DatabaseMethods databaseMethods = new DatabaseMethods();
    // Calling ResponseStatus --> (To see what happen on screen to user)
    ResponseStatus responseStatus = new ResponseStatus();

    // This method check your database is exit or not
    public boolean CheckDataBase() {
        File folder = new File(databaseMethods.Database_path());
        return folder.isDirectory();
    }

    // This method create a database folder --> (to store bank data);
    public boolean CreateDataBase() {
        try {
            File theDir = new File(databaseMethods.Database_path());
            responseStatus.show_status_of_database_creating(databaseMethods.Database_path());
            if (!theDir.exists()) {
                theDir.mkdirs();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            methods.stop_code("YOUR ERROR !! -> CREATING DATABASE :( \nDATABASE MIGHT BE BUSY..");
        }
        return false;

    }

    // This method check your admin panel is exit in database or not
    public boolean CheckAdminPanel() {
        File file = new File(databaseMethods.Database_path() + "\\ADMIN_(DON'TDELETEIT).txt");
        return file.exists();
    }

    // This method create a Admin database file in database folder --> (to control bank data);
    public boolean CreateAdminPanel() {
        try {
            File thefile = new File(databaseMethods.Database_path() + "\\ADMIN_(DON'TDELETEIT).txt");
            responseStatus.show_status_of_adminPanel_creating(databaseMethods.Database_path());
            if (!thefile.exists()) {
                thefile.createNewFile();
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            methods.stop_code("YOUR ERROR !! -> CREATING ADMIN PANEL :( \nDATABASE MIGHT BE BUSY..");
        }
        return false;
    }

    // This method was set Password in Admin Panel
    public boolean SetAdminUsernameOrPassword() {
        String key = methods.string_key_gen(22);
        System.out.println("(IMPORTANT !!! ) --> You need to set Password to admin panel \nPress (Y) to set Password \n(N) to default Password And UserName. Username --> admin / Password --> 1234");
        if (methods.Selection_Input("Invalid Input", "\nPress (Y) to continue (N) to default: ", 'Y', 'N')) {
            String user_name = methods.input("Invalid Username","Enter your User Name: ");
            if(methods.length_check_string(user_name,4,15,"Invalid Username Min length ==> 4 Max length ==> 15")){
                String password = methods.input("Invalid Password","(KEEP REMEMBER, DON'T FORGET)\nEnter your password: ");
                if(methods.length_check_string(password,10,20,"Invalid Password Min length ==> 10 Max length ==> 20")){
                    responseStatus.show_status_admin_account_created(key);
                    String UserName = SecurityEncryptionDecryptionAES.encrypt(user_name, key);
                    String Password = SecurityEncryptionDecryptionAES.encrypt(password, key);
                    if(methods.file_create_and_write(databaseMethods.Database_path(),UserName + "\n" + Password,"ADMIN_(DON'TDELETEIT).txt")){
                        if(methods.file_create_and_write(databaseMethods.Database_path(), key,"ADMIN_key_(DON'TDELETEIT).txt")){
                            System.out.println("Admin Account Created :)");
                            return true;
                        }
                    }
                }
            }
        } else {
            String UserName = SecurityEncryptionDecryptionAES.encrypt("admin", key);
            String Password = SecurityEncryptionDecryptionAES.encrypt("1234", key);
            if(methods.file_create_and_write(databaseMethods.Database_path(),UserName + "\n" + Password,"ADMIN_(DON'TDELETEIT).txt")){
                if(methods.file_create_and_write(databaseMethods.Database_path(), key,"ADMIN_key_(DON'TDELETEIT).txt")){
                    System.out.println("Admin Account Created :)");
                    return true;
                }
            }
        }
        return false;
    }

    // This method check UserHaveAdmin panel --> (data or not)
    public boolean CheckAdminDataInDataBase(){
        File file_check = new File(databaseMethods.Database_path() + "\\ADMIN_(DON'TDELETEIT).txt");
        return file_check.length() == 0;
    }

}