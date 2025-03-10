package application.inventroy2;

import java.sql.Connection;
import java.sql.DriverManager;


public class database_inventory {

    public static Connection connectDb(){

        try{

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/hungers_restaurant", "root", "2742003");
            System.out.println("Connection established");


            return connect;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }




}

