package sample;

import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import sample.ConnectionClass;
import sample.PasswordUtils;

public class Teacher_SignUp_Controller {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField password;

    @FXML
    private JFXTextField confirm;

    @FXML
    private void initialize() {
        //Validators for all the Fields
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        name.getValidators().add(validator);
        name.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                name.validate();
        });
        email.getValidators().add(validator);
        email.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                email.validate();
        });
        id.getValidators().add(validator);
        id.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                id.validate();
        });
        password.getValidators().add(validator);
        password.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                password.validate();
        });
        confirm.getValidators().add(validator);
        confirm.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                confirm.validate();
        });
    }
    @FXML
    public void TsignUp(ActionEvent event) throws SQLException, IOException, InvalidKeySpecException {
        //Signs Up new Teachers
        ConnectionClass Teacher = new ConnectionClass();
        Connection connection= Teacher.getconnection();
        if ((password.getText()).equals(confirm.getText())){
            //If both the Confirm Pasasword And Password Match
            //Creates a Object of Password Util and then generates Encrypted Password
            PasswordUtils pass= new PasswordUtils();
            String salt= pass.getSalt(30);
            String securePass= pass.generateSecurePassword(password.getText(), salt);
            //Puts the Enter Information into Database
            Main.user.sendString("Signup");
            Main.user.sendString("Teacher");
            Main.user.sendString(name.getText());
            Main.user.sendString(email.getText());
            Main.user.sendString(id.getText());
            Main.user.sendString(securePass);
            Main.user.sendString(salt);
            Boolean SignUp=Main.user.recieveBoolean();
            if(SignUp){
                //Displays Alert for Sucess of Registeration
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("You have been registered");
                alert.showAndWait();
                Parent home_parent= FXMLLoader.load(getClass().getResource("sample.fxml"));
                Scene Home= new Scene(home_parent);

                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(Home);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Failure");
                alert.setHeaderText("Failure");
                alert.setContentText("Registration Failed");
                alert.showAndWait();
            }


        }
        else{
            //Shows Alert of Password Mismatch
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText("Error:");
            alert.setContentText("Passwords Do not match");

            alert.showAndWait();
        }

    }

    @FXML
    private void back(MouseEvent event) throws IOException{
        //Goes Back to Login Page
        Parent home_parent= FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void Close(MouseEvent event){
        Main.user.sendString("Exit");
        System.exit(0);
    }
}
