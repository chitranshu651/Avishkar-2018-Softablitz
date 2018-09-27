package sample.Server_Client;

import sample.ConnectionClass;
import sample.PasswordUtils;
import sample.Session_Id;

import javax.security.auth.Subject;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.Socket;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class clientHandler implements Runnable {

    private Socket client;

    private ObjectInputStream ObjectInput;
    private ObjectOutputStream ObjectOutput;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String name;
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection = Student.getconnection();
    private PasswordUtils check = new PasswordUtils();


    public clientHandler(Socket client, String name, ObjectOutputStream ObjectOutput, ObjectInputStream ObjectInput, DataOutputStream dataOutput, DataInputStream dataInput) {
        this.name = name;
        this.client = client;
        this.ObjectOutput = ObjectOutput;
        this.ObjectInput = ObjectInput;
        this.dataInput = dataInput;
        this.dataOutput = dataOutput;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String recieved = dataInput.readUTF();
                if (recieved.equalsIgnoreCase("Exit")) {
                    System.out.println("Client Connection Terminating");
                    this.client.close();
                    break;
                }
                switch (recieved) {
                    case "Login":
                        System.out.println("Login Was entered");
                        String type = dataInput.readUTF();
                        if (type.equalsIgnoreCase("Student")) {
                            this.dataOutput.writeBoolean(StudentSignin());
                            break;
                        } else {
                            this.dataOutput.writeBoolean(TeacherSignin());
                            break;
                        }
                    case "Signup":
                        System.out.println("Signup was entered");
                        type = dataInput.readUTF();
                        if (type.equalsIgnoreCase("Teacher")) {
                            this.dataOutput.writeBoolean(TeacherSignUp());
                            break;
                        } else {
                            this.dataOutput.writeBoolean(StudentSignUp());
                            break;
                        }
                    case "Name":
                        System.out.println("Name was Entered");
                        type = dataInput.readUTF();
                        this.dataOutput.writeUTF(getName(type));
                        break;
                    case "getSubjects":
                        System.out.println("Get Subjects was Entered");
                        this.ObjectOutput.writeObject(getSubject());
                        break;
                    case "addSubject":
                        System.out.println("Add Subject was Entered");
                        this.dataOutput.writeBoolean(addSubject());
                        break;
                    case "addQuestion":
                        System.out.println("Add Question was Entered");
                        this.dataOutput.writeBoolean(addQuestion());
                        break;
                    case "createTest":
                        System.out.println("Create Test was Entered");
                        this.dataOutput.writeBoolean(createTest());
                        break;
                    case "getSubjectId":
                        System.out.println("Get Subject was Entered");
                        this.dataOutput.writeUTF(getSubjectId());
                        break;
                    case "createSection":
                        System.out.println("Create Section was Entered");
                        this.dataOutput.writeBoolean(createSection());
                        break;
                    case "getSections":
                        System.out.println("Get sections was entered");
                        this.ObjectOutput.writeObject(getSections());
                        break;
                    case "getQuestions":
                        System.out.println("Get Questions was Entered");
                        this.ObjectOutput.writeObject(getQuestions());
                        break;
                    case "getTime":
                        System.out.println("Get time was entered");
                        this.dataOutput.writeUTF("" + getTime());
                        break;
                    case "addMarks":
                        System.out.println("Add marks was entered");
                        addMarks();
                        break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        try {
            ObjectOutput.close();
            ObjectInput.close();
            dataOutput.close();
            dataInput.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean StudentSignin() {
        try {
            String username = dataInput.readUTF();
            String password = dataInput.readUTF();
            String sql = "SELECT `Password`, `Salt` FROM `students` WHERE `Registration_NO`=" + username + ";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword = "";
            String Salt = "ABC2";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if (check.verifyUserPassword(password, Spassword, Salt)) {
                return true;
            } else return false;
        } catch (IOException | SQLException | InvalidKeySpecException e) {
            System.out.println(e);
            return false;
        }

    }

    private boolean TeacherSignin() {
        try {
            String username = dataInput.readUTF();
            String password = dataInput.readUTF();
            String sql = "SELECT `Password`, `Salt` FROM `teachers` WHERE `Teacher_ID`=" + username + ";";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Spassword = "";
            String Salt = "A2B2";

            while (rs.next()) {
                Spassword = rs.getString("Password");
                Salt = rs.getString("Salt");
            }
            if (check.verifyUserPassword(password, Spassword, Salt)) {
                return true;
            } else return false;
        } catch (IOException | SQLException | InvalidKeySpecException | IllegalArgumentException e) {
            System.out.println(e);
        }
        return false;
    }

    private boolean TeacherSignUp() {
        try {
            String name = this.dataInput.readUTF();
            String email = this.dataInput.readUTF();
            String id = this.dataInput.readUTF();
            String pass = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String sql = "INSERT INTO `teachers`(`Name`, `Email`, `Teacher_ID`,`Password`, `Salt`) VALUES('" + name + "','" + email + "'," + id + ",'" + pass + "', '" + salt + "');";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private boolean StudentSignUp() {
        try {
            String name = this.dataInput.readUTF();
            String email = this.dataInput.readUTF();
            String Reg_no = this.dataInput.readUTF();
            String pass = this.dataInput.readUTF();
            String salt = this.dataInput.readUTF();
            String sql = "INSERT INTO `students`(`Name`, `Email`, `Registration_No`, `Password`, `Salt`) VALUES('" + name + "','" + email + "'," + Reg_no + ",'" +
                    pass + "','" + salt + "')";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    private String getName(String type) {
        if (type.equalsIgnoreCase("Teacher")) {
            try {
                String name = "";
                String sql = "Select `name` from `teachers` where `Teacher_Id`=" + dataInput.readUTF();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    name = rs.getString("Name");
                }
                return name;
            } catch (SQLException | IOException e) {
                System.out.println(e);
                return null;
            }
        } else {
            try {
                String name = "";
                String sql = "Select `name` from `students` where `Registration_No`=" + dataInput.readUTF();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    name = rs.getString("Name");
                }
                return name;
            } catch (SQLException | IOException e) {
                System.out.println(e);
                return null;
            }
        }

    }

    private Object getSubject() {
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
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return null;
        }

    }

    private boolean addSubject() {
        try {
            String name = this.dataInput.readUTF();
            String SessionId = this.dataInput.readUTF();
            String SubjectID = this.dataInput.readUTF();
            String sql = "INSERT INTO `subjects` (`Name`, `Teacher Id`, `Subject Id`) VALUES ('" + name + "'," + SessionId + ",'" + SubjectID + "');";
            Statement statement = connection.createStatement();
            // Executes Sql statement to put subject information into Database
            statement.execute(sql);
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean addQuestion() {
        try {
            String SectionId = this.dataInput.readUTF();
            System.out.println(SectionId);
            String Question = this.dataInput.readUTF();
            System.out.println(Question);
            String OptionA = this.dataInput.readUTF();
            System.out.println(OptionA);
            String OptionB = this.dataInput.readUTF();
            String OptionC = this.dataInput.readUTF();
            String OptionD = this.dataInput.readUTF();
            String Answer = this.dataInput.readUTF();
            String Question_No = this.dataInput.readUTF();
            String Test = this.dataInput.readUTF();
            String sql = "INSERT INTO `questions` (`Test Id`, `Section Id`,`Number`, `Question`, `A`, `B`, `C`, `D`, `Correct Answer`) VALUES ('" + Test + "','" + SectionId + "'," + Question_No + ",'" + Question + "','" + OptionA +
                    "','" + OptionB + "','" + OptionC + "','" + OptionD + "','" + Answer + "');";
            Statement statement = connection.createStatement();
            System.out.println("Statement Created");
            statement.execute(sql);
            System.out.println("executed");
            return true;
        } catch (IOException | SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    private boolean createTest() {
        try {
            String name = dataInput.readUTF();
            String TestCode = dataInput.readUTF();
            int Sections = dataInput.readInt();
            String SubjectId = dataInput.readUTF();
            String Teacher = dataInput.readUTF();

            String sql = "INSERT INTO `test` (`Name`, `Subject ID`, `Test Id`, `NSections`, `Teacher Id`) VALUES ('" + name + "','" + SubjectId + "','" +
                    TestCode + "'," + Sections + "," + Teacher+ ");";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return false;
        }
    }

    private String getSubjectId() {
        try {
            String Subject = dataInput.readUTF();
            String Id = dataInput.readUTF();
            System.out.println(Subject + "     " + Id);
            String sql = "SELECT `Subject Id` FROM `subjects` WHERE `Teacher Id` =" + Id + " AND `Name` ='" + Subject + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String Sid = "";
            while (rs.next()) {
                Sid = Sid + rs.getString("Subject Id");
            }
            return Sid;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private boolean createSection() {
        try {
            String name = dataInput.readUTF();
            String NumberQ = dataInput.readUTF();
            String Time = dataInput.readUTF();
            String TestId = dataInput.readUTF();
            String SectionId = dataInput.readUTF();

            String sql = "INSERT INTO `sections`(`Name`, `Test Id`, `Section Id`, `Questions` ,`Time`) VALUES ('" + name + "','" + TestId + "','" + SectionId +
                    "'," + NumberQ + "," + Time + ");";
            Statement statement = connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return false;
        }
    }

    private Object getSections() {
        try {
            String Test_Id = this.dataInput.readUTF();
            List<String> sections = new ArrayList<>();
            String sql = "SELECT `Section Id` FROM `sections` WHERE `Test Id` ='" + Test_Id + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                sections.add(rs.getString("Section Id"));
            }
            return sections;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private int getNQuestions(String Section, String Test) {
        try {
            System.out.println("In N questions");
            int no = 0;
            String sql = "SELECT `Questions` FROM `sections` WHERE `Section Id` ='" + Section + "' AND `Test Id` ='" + Test + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                no = rs.getInt("Questions");
            }
            System.out.println("End of N Questions");
            return no;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        }
    }

    private Object getQuestions() {
        try {


            String Section = this.dataInput.readUTF();
            String Test = this.dataInput.readUTF();
            int number = getNQuestions(Section, Test);
            String[][] questions = new String[number][6];
            String sql = "SELECT * FROM `questions` WHERE `Section Id` ='" + Section + "' AND `Test Id` ='" + Test + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            for (int i = 0; i < number; i++) {
                questions[i][0] = rs.getString("Question");
                questions[i][1] = rs.getString("A");
                questions[i][2] = rs.getString("B");
                questions[i][3] = rs.getString("C");
                questions[i][4] = rs.getString("D");
                questions[i][5] = rs.getString("Correct Answer");
                System.out.println(questions[i][5]);
                rs.next();
            }
            return questions;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return null;
        }

    }

    private int getTime() {
        try {
            System.out.println("First");
            int time = 0;
            String section = dataInput.readUTF();
            System.out.println(section);
            String Test = dataInput.readUTF();
            System.out.println(Test);
            String sql = "Select `Time` from `sections` where `Section Id` ='" + section + "' AND `Test Id` ='" + Test + "';";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("Second");
            while (rs.next()) {
                time = rs.getInt("Time");
            }
            return time;
        } catch (SQLException | IOException e) {
            System.out.println(e);
            return 0;
        }
    }

    private void addMarks() {
        try {
            String Test = dataInput.readUTF();
            String User = dataInput.readUTF();
            String Marks = dataInput.readUTF();
            String Teacher = getTeacherId(Test);
            String sql = "INSERT INTO `results`(`Test Id`, `Student Id`, `Marks`, `Teacher Id`) VALUES ('"+ Test +"'," + User + "," + Marks
                    +"," + Teacher + ");";
            Statement statement =connection.createStatement();
            statement.execute(sql);
        } catch (SQLException | IOException e) {
            System.out.println(e);
        }
    }

    private String getTeacherId(String Test){
        String Teacher = "";
        try{

        String sql = "SELECT `Teacher Id` FROM `test` WHERE `Test Id` ='" + Test + "';";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            Teacher = rs.getString("Teacher Id");
        }
        return Teacher;
    }
    catch (SQLException e){
        System.out.println(e);
        return Teacher;
    }
    }

}
