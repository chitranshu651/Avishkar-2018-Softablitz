package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class Student_SignUp_Controller {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField Reg_No;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirm;

    @FXML
    private void initialize(){

        //All of the Require field validators for the TextFields
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Input Required");
        name.getValidators().add(validator);
        name.focusedProperty().addListener((o, oldVal, newVal) ->{
            if(!newVal)
                name.validate();
        });
        email.getValidators().add(validator);
        email.focusedProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal)
                email.validate();
        });
        Reg_No.getValidators().add(validator);
        Reg_No.focusedProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal)
                Reg_No.validate();
        });
        password.getValidators().add(validator);
        password.focusedProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal)
                password.validate();
        });
        confirm.getValidators().add(validator);
        confirm.focusedProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal)
                confirm.validate();
        });
    }

    @FXML
    void SSignUp(ActionEvent event) throws SQLException, IOException, InvalidKeySpecException {
        //Action of the Button Signup to Register the User
        if ((password.getText()).equals(confirm.getText())){// IF passwod and confirm password fields match
            PasswordUtils pass= new PasswordUtils();
            //Gets the salt required for hashing
            String salt= pass.getSalt(30);
            String securePass= pass.generateSecurePassword(password.getText(), salt);// Hashes the password using the salt generated
            Main.user.sendString("Signup");
            Main.user.sendString("Student");
            Main.user.sendString(name.getText());
            Main.user.sendString(email.getText());
            Main.user.sendString(Reg_No.getText());
            Main.user.sendString(securePass);
            Main.user.sendString(salt);
            Boolean SignUp=Main.user.recieveBoolean();
            if(SignUp){
                //creates dialog box to show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("You have been registered");
                alert.showAndWait();
                //Goes back to login page
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Mismatch");
        alert.setHeaderText("Error:");
        alert.setContentText("Passwords Do not match");

        alert.showAndWait();
    }

}

    @FXML
    private void back(MouseEvent event) throws IOException{
        //To return back to home
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
