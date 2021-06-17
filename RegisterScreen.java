package com.bkbank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import java.lang.*;

public class RegisterScreen {

    static String User_Name,Password,Gender,Date;
    static long Phone_Number;
    static int Age,Pin;
    static long AccountNumber = 200;
    static java.util.List<java.lang.String> Datas;

    // Calling ResponseStatus --> (To see what happen on screen to user)
    ResponseStatus responseStatus = new ResponseStatus();

    // Calling RegisterMethods --> (To use all  Register methods)
    RegisterMethods registerMethods = new RegisterMethods();

    DatabaseMethods databaseMethods = new DatabaseMethods();

    public boolean RegisterStart() {
        return responseStatus.show_register_start_status();
    }

    public boolean TakeUserInformationOfRegistration() {
        User_Name = registerMethods.String_input("Username Was Incorrect", "\nEnter A UserName: ", true, true);
        if(methods.length_check_string(User_Name,5,15,"Invalid Username Min length ==> 5 Max length ==> 15")){
            Password = registerMethods.String_input("Password Input Was Incorrect", "\n(Keep Remember DON'T FORGET)\nMake a Password For Your Account: ", false, true);
            if(methods.length_check_string(Password,8,20,"Invalid Password Min length ==> 8 Max length ==> 20")){
                Phone_Number = registerMethods.long_input("Incorrect Phone Number", "\nEnter Your Phone Number: ", true, true);
                if(methods.length_check_long(Phone_Number,10,10,"Invalid PHONE NO.." + Phone_Number)){
                    Age = registerMethods.integer_input("Incorrect Age", "\nEnter your Age: ", true, true);
                    if(Age >= 18){
                        int temp = registerMethods.integer_input("Incorrect Pin", "\n(Keep Remember DON'T FORGET)\nMake A 6 Digit Pin Pin: ", true, true);
                        if(String.valueOf(temp).length() >= 6){
                            Pin = temp;
                            Gender = registerMethods.String_input("Gender Input Was Incorrect", "\nSelect Your Gender: \n(M) -> Male\n(F) -> Female\n(Any other key to Transgender) -> *\nEnter: ", true, true);
                            Gender = Gender.toLowerCase();
                            if(Gender.equals("m")){
                                Gender = "Male";
                            } else if (Gender.equals("f")){
                                Gender = "Female";
                            } else {
                                Gender = "Transgender";
                            }

                            if(responseStatus.show_register_all_input_complete_message()){
//                                 Date = "06/11/2017 12:26:18"; 
//                                 Use when you want instant make your account.
                                Date = methods.current_time();
                                return true;
                            }
                        } else {
                            System.out.println("PIN ARE TO SMALL MIN LENGTH ==> 6 TRY AGAIN.");
                        }
                    } else {
                        System.out.println("You are not able to Open a Bank Account");
                    }
                }
            }
        }

        return false;
    }

    public boolean ValidateUserByCaptcha(){
        String captcha = methods.string_key_gen(6);
        System.out.println("Captcha : " + captcha);
        String input = methods.input("Invalid captcha","Enter the captcha you see above: ");
        if(input.equals(captcha)){
            return true;
        }
        System.out.println("Invalid Captcha !!");
        return false;

    }

    public boolean VerifyPhoneNumber(){
        if(responseStatus.show_register_Phone_number_verification_check()){
            int Otp = Integer.parseInt(String.valueOf(methods.generate_random_number(6)));
            String meesage = "Hi " + RegisterScreen.User_Name + " ! Thank's for Choosing Bk Bank !! \nYour Account Request on " + Date +  ". Are send Properly To Bank. \nAccount open OTP :- " + Otp;
            Server.Otp = Otp;
            System.out.println(Otp);
//            Server.sendOtp(meesage,Long.toString(Phone_Number));
            if(true){
                System.out.println("OTP was send !! ");
                if(Server.VerifyOtp(methods.input("Invalid Input","Enter Otp: "))){
                    if(responseStatus.show_registration_complete_message()){
                        return true;
                    }
                } else {
                    System.out.println("Otp was in correct !! TRY AGAIN ");
                }

            }
        }
        return false;
    }

    public boolean SavedDataOfRegisterUser(){
        boolean admin_number_list_is_created = false;
        boolean folder_create_it_time_to_file = false;
        File file = new File(databaseMethods.Database_path() + "//" + "UserPhoneNumberList.txt");
            if (!file.exists()){
                try {
                    file.createNewFile();
                } catch (Exception e){
                    System.out.println(e);
                }
                admin_number_list_is_created = true;
            }
            if(file.exists()){
                admin_number_list_is_created = true;
                if(admin_number_list_is_created){
                    String key = methods.open_file_return_string(databaseMethods.Database_path() + "//" + "ADMIN_key_(DON'TDELETEIT).txt");
                    String Phone_Number_en = SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Phone_Number), key);
                    methods.file_create_and_write(databaseMethods.Database_path(), Phone_Number_en + "\n","UserPhoneNumberList.txt");
                }
            }
        if(!registerMethods.checkUserAccountInDatabase(String.valueOf(AccountNumber))){
            File Dir = new File(databaseMethods.Database_path() + "//" + AccountNumber);
            if (!Dir.exists()){
                Dir.mkdirs();
                folder_create_it_time_to_file = true;
            }
            if(folder_create_it_time_to_file){
                String key = methods.string_key_gen(22);
                String User_Name_en = SecurityEncryptionDecryptionAES.encrypt(User_Name, key);
                String Gender_en = SecurityEncryptionDecryptionAES.encrypt(Gender, key);
                String Date_en = SecurityEncryptionDecryptionAES.encrypt(Date, key);
                String Phone_Number_en = SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Phone_Number), key);
                String Age_en = SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Age), key);
                String Pin_en = SecurityEncryptionDecryptionAES.encrypt(String.valueOf(Pin), key);
                String data = User_Name_en+ "\n" + Password + "\n" + Gender_en + "\n" + Date_en + "\n" + Phone_Number_en + "\n" + Age_en + "\n" + Pin_en + "\n" + AccountNumber;

                if(methods.file_create_and_write(databaseMethods.Database_path() + "//" + AccountNumber, key,"User_key.txt")){
                    if(methods.file_create_and_write(databaseMethods.Database_path() + "//" + AccountNumber, data,"User_detail.txt"))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean deleteWrongUserRegisterData(){
        File file = new File(databaseMethods.Database_path() + "//" + RegisterScreen.AccountNumber);
        methods.delete_folder(file);
        file.delete();
        return false;
    }
}

