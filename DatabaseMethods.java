package com.bkbank;

class CheckDatabaseTimeToTime implements Runnable{

    DatabaseManagement databaseManagement = new DatabaseManagement();

    public void run(){
        methods.delay(100);
        if(!databaseManagement.CheckDataBase()){
            for(int i = 0; i<100; i++){
                System.out.println("\"\n \n :( ERROR FOUND !! :- DATABASE DELETE ON RUN TIME !! RUN AGAIN TRY AGAIN ");
                methods.delay(500);
            }
            methods.stop_code("\"\n \n :( ERROR FOUND !! :- DATABASE DELETE ON RUN TIME !! RUN AGAIN \"");
         }

        if(!databaseManagement.CheckAdminPanel()){
            for(int i = 0; i<100; i++) {
                System.out.println("\"\n \n :( ERROR FOUND !! :- ADMIN PANEL DELETE ON RUN TIME !! RUN AGAIN TRY AGAIN ");
                methods.delay(500);
            }
            methods.stop_code("\"\n \n :( ERROR FOUND !! :- ADMIN PANEL DELETE ON RUN TIME !! RUN AGAIN \"");
        }
    }
}

public class DatabaseMethods {

    /* [DATABASE METHODS]
    *  IN THESE FILE ALL METHOD THAT ARE NEED TO PROPER WORK OF [DATABASE] AVAILABLE
    *  AND ALSO IT HAVE THOSE THING THAT ARE USE TO MUCH IN [DATABASE CLASS]
    *  IT WAS NOT A PUBIC BUT IF SOMEONE NEED THEN THEY WAS USE THIS
    * */

    // Get The path of Current running File
    public java.lang.String base_path(){
        return System.getProperty("user.dir");
    }

    // Get correct database path
    public java.lang.String Database_path() {
        return base_path() +  "\\src\\com\\Database";
    }






}
