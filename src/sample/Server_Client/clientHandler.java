package sample.Server_Client;

import sample.ConnectionClass;
import sample.PasswordUtils;

import java.io.*;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class clientHandler implements Runnable{

    private Socket client;

    private ObjectInputStream ObjectInput;
    private ObjectOutputStream ObjectOutput;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String name;
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection= Student.getconnection();
    private PasswordUtils check = new PasswordUtils();

    public clientHandler(Socket client, String name,  ObjectOutputStream ObjectOutput,ObjectInputStream ObjectInput, DataOutputStream dataOutput, DataInputStream dataInput){
        this.name= name;
        this.client=client;
        this.ObjectOutput= ObjectOutput;
        this.ObjectInput=ObjectInput;
        this.dataInput= dataInput;
        this.dataOutput= dataOutput;
    }

    @Override
    public void run(){
        while(true){
            try{
                String recieved= dataInput.readUTF();
                if(recieved.equalsIgnoreCase("Exit")){
                    System.out.println("Client Connection Terminating");
                    this.client.close();
                    break;
                }
                switch(recieved){
                    case "Login":
                        System.out.println("Login Was entered");
                        String type= dataInput.readUTF();
                        if(type.equalsIgnoreCase("Student")){
                            this.dataOutput.writeBoolean(StudentSignin());
                            break;
                        }
                        else{
                            this.dataOutput.writeBoolean(TeacherSignin());
                            break;
                        }
                    case "Signup":
                        System.out.println("Signup was entered");
                        type= dataInput.readUTF();
                        if(type.equalsIgnoreCase("Teacher")){
                            this.dataOutput.writeBoolean(TeacherSignUp());
                            break;
                        }
                        else{
                            this.dataOutput.writeBoolean(StudentSignUp());
                            break;
                        }
                    case "Name":
                        System.out.println("Name was Entered");
                        type=dataInput.readUTF();
                        this.dataOutput.writeUTF(getName(type));
                        break;
                    case "getSubjects":
                        System.out.println("Get Subjects was Entered");
                        this.ObjectOutput.writeObject(getSubject());

                }
            }
            catch(IOException e){
                System.out.println(e);
            }

        }
        try{
            ObjectOutput.close();
            ObjectInput.close();
            dataOutput.close();
            dataInput.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    private boolean StudentSignin(){
        try{
            String username = dataInput.readUTF();
            String password = dataInput.readUTF();
            String sql = "SELECT `Password`, `Salt` FROM `students` WHERE `Registration_NO`=" + username + ";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword="";
            String Salt="";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if(check.verifyUserPassword(password, Spassword, Salt)){
                return true;
            }
            else return false;
        }
        catch(IOException| SQLException | InvalidKeySpecException e){
            System.out.println(e);
        }
        return false;
    }

    private boolean TeacherSignin(){
        try{
            String username = dataInput.readUTF();
            String password = dataInput.readUTF();
            String sql = "SELECT `Password`, `Salt` FROM `teachers` WHERE `Teacher_ID`=" + username + ";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword="";
            String Salt="A2B2";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if(check.verifyUserPassword(password, Spassword, Salt)){
                return true;
            }
            else return false;
        }
        catch(IOException| SQLException | InvalidKeySpecException |IllegalArgumentException e){
            System.out.println(e);
        }
        return false;
    }

    private boolean TeacherSignUp(){
        try{
            String name= this.dataInput.readUTF();
            String email= this.dataInput.readUTF();
            String id= this.dataInput.readUTF();
            String pass= this.dataInput.readUTF();
            String salt= this.dataInput.readUTF();
            String sql ="INSERT INTO `teachers`(`Name`, `Email`, `Teacher_ID`,`Password`, `Salt`) VALUES('"+ name + "','" + email +"'," +id +",'" + pass +"', '" + salt + "');";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            return true;
        }
        catch(IOException | SQLException e){
            System.out.println(e);
        }
        return false;
    }

    private boolean StudentSignUp(){
        try{
            String name= this.dataInput.readUTF();
            String email= this.dataInput.readUTF();
            String Reg_no= this.dataInput.readUTF();
            String pass= this.dataInput.readUTF();
            String salt= this.dataInput.readUTF();
            String sql ="INSERT INTO `students`(`Name`, `Email`, `Registration_No`, `Password`, `Salt`) VALUES('"+ name + "','" + email +"'," +Reg_no +",'"+
                    pass +"','" + salt + "')";
            Statement statement= connection.createStatement();
            statement.execute(sql);
            return true;
        }
        catch(IOException | SQLException e){
            System.out.println(e);
        }
        return false;
    }

    private String getName(String type){
        if(type.equalsIgnoreCase("Teacher")){
            try{
                String name="";
                String sql="Select `name` from `teachers` where `Teacher_Id`=" + dataInput.readUTF();
                Statement statement = connection.createStatement();
                ResultSet rs= statement.executeQuery(sql);
                while(rs.next()){
                    name= rs.getString("Name");
                }
                return name;
            }
            catch(SQLException | IOException e){
                System.out.println(e);
                return null;
            }
        }
        else{
            try{
                String name="";
                String sql="Select `name` from `students` where `Registration_No`=" + dataInput.readUTF();
                Statement statement = connection.createStatement();
                ResultSet rs= statement.executeQuery(sql);
                while(rs.next()){
                    name= rs.getString("Name");
                }
                return name;
            }
            catch(SQLException | IOException e){
                System.out.println(e);
                return null;
            }
        }

    }

    private Object getSubject(){
        try {
            List<String> subjects = new ArrayList<>();
            String id = dataInput.readUTF();
            String sql = "Select `name` from `subjects` where `Teacher Id`=" + id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                subjects.add(rs.getString("Name"));
            }
            return subjects;
        }
        catch(SQLException | IOException e){
            System.out.println(e);
            return null;
        }

    }
}
