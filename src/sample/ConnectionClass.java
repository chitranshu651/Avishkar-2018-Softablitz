package sample;


import java.sql.*;

public class ConnectionClass {
    //Connecting to SQL database
    public Connection connection;
    public Connection getconnection(){
        String dbName= "avishkar_softablitz_18"; //Database Name
        String user= "root"; //Username of Database
        String password=$password;//Password
        //Tries conecting to database if doesnt work gives a error
        try{
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName+"?useSSL=false",user,password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
