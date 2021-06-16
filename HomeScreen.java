package com.bkbank;

public class HomeScreen {

    static String User_Selection;

    // Calling ResponseStatus --> (To see what happen on screen to user)
    ResponseStatus responseStatus = new ResponseStatus();

    // These method show --> (Start screen context of bank)
    public boolean HomeScreenContext(boolean Top_context){
        return responseStatus.show_status_of_bank_start_Screen(Top_context);
    }

    // These method show --> (What option user want login,register,e.t.c)
    public int AccountScreenAccessSelection(){
        String input = methods.input("Invalid Input","Enter your input: ");
        input = input.toLowerCase();
        return switch (input) {
            case "r" -> 1; // Mean Login
            case "l" -> 2; // Mean Register
            case "a" -> 3; // Mean Admin
            default -> 4; // Mean error --> [Invalid input] & [null Value]
        };

    }

    public boolean ShowInput(boolean yes_or_not){
        return responseStatus.show_home_screen(yes_or_not);
    }

    public int UserOptionSelectionOnHomeScreen(){
        return switch (User_Selection) {
            case "1" -> 1;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            default -> 7;
        };


    }

}
