package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.io.IOException;


public class Controller {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXRadioButton Teacher;

    @FXML
    private JFXRadioButton Student;

    @FXML
    private void initialize(){
        //Initializes all of the required field validators
        RequiredFieldValidator validator= new RequiredFieldValidator();
        validator.setMessage("Input Required");
        username.getValidators().add(validator);
        username.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                username.validate();
        });
    }

    @FXML
    private void Tsignup(ActionEvent click) throws IOException {
        //When the Teacher Signup Button is clicked this function is invoked to change the scene to the Teacher Signup Page
        Parent TeacherSign = FXMLLoader.load(getClass().getResource("Signup/Teacher_SignUp.fxml"));
        Scene TeacherSignupScene = new Scene(TeacherSign);

        Stage window = (Stage) ((Node) click.getSource()).getScene().getWindow();
        window.setTitle("Teacher Signup");
        window.setScene(TeacherSignupScene);
        window.show();


    }
    @FXML
    private void SSignup(ActionEvent click) throws IOException {
        //When the Student Signup Button is clicked this function is invoked to change the scene to the Student Signup Page
        Parent StudentSign = FXMLLoader.load(getClass().getResource("Signup/Student_SignUp.fxml"));
        Scene StudentSignupScene = new Scene(StudentSign);

        Stage window = (Stage) ((Node) click.getSource()).getScene().getWindow();
        window.setTitle("Student Signup");
        window.setScene(StudentSignupScene);
        window.show();
    }

    @FXML
     private void Login(ActionEvent event) throws SQLException, InvalidKeySpecException, IOException {
        //When Login Function is clicked it invokes this function
        Main.user.sendString("Login");
        Session_Id current= new Session_Id();
        if (Student.isSelected()) {
            //  Checks Radio of Student to check if student is logging on and if yes check if the user name and password match
            Main.user.sendString("Student");
            Main.user.sendString(username.getText());
            Main.user.sendString(password.getText());
            Boolean Login= Main.user.recieveBoolean();

            //Above step gets the password and the salt necessary to check if given password and saved password match
            if (Login) {
                current.setUsername(username.getText());
                Parent Dashboard = FXMLLoader.load(getClass().getResource("Student/StudentDashboard.fxml"));
                Scene DashboardScene = new Scene(Dashboard);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Dashboard");
                window.setScene(DashboardScene);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Login and Password");
                alert.setContentText("The login user id and Password you have entered dont match");
                alert.showAndWait();
            }
        }

        else if (Teacher.isSelected()) {
            //Checks Teacher Radio Button and checks the login like for student
            Main.user.sendString("Teacher");
            Main.user.sendString(username.getText());
            Main.user.sendString(password.getText());
            Boolean Login= Main.user.recieveBoolean();
            if(Login){
                current.setUsername(username.getText());
                Parent Dashboard = FXMLLoader.load(getClass().getResource("Teacher/Dashboard.fxml"));
                Scene DashboardScene = new Scene(Dashboard);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Dashboard");
                window.setScene(DashboardScene);
                window.show();
            }
            else{//If not displays a alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERROR");
                alert.setHeaderText("Login and Password");
                alert.setContentText("The login user id and Password you have entered don't match");
                alert.showAndWait();
                }

            }


        }

        @FXML
        private void Close(MouseEvent event){
            //Closes the window
            Main.user.sendString("Exit");
            System.exit(0);
        }
    }
