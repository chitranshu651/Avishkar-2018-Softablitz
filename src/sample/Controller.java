package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;



public class Controller {
    public void Tsignup(ActionEvent click) throws IOException {
        Parent TeacherSign= FXMLLoader.load(getClass().getResource("Teacher_SignUp.fxml"));
        Scene TeacherSignupScene= new Scene(TeacherSign);

        Stage window = (Stage)((Node) click.getSource()).getScene().getWindow();
        window.setScene(TeacherSignupScene);
        window.show();


    }

    public void SSignup(ActionEvent click) throws IOException {
        Parent StudentSign= FXMLLoader.load(getClass().getResource("Student_SignUp.fxml"));
        Scene StudentSignupScene= new Scene(StudentSign);

        Stage window = (Stage)((Node) click.getSource()).getScene().getWindow();
        window.setScene(StudentSignupScene);
        window.show();
    }

    public void Login(ActionEvent event){
        ConnectionClass login = new ConnectionClass();
        Connection connection= login.getconnection();


    }


}
