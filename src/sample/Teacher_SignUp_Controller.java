package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Teacher_SignUp_Controller {
    public TextField name;
    public TextField email;
    public TextField username;
    public TextField password;
    public TextField confirm;
    public TextField id;

    public void TsignUp(ActionEvent event) throws SQLException, IOException {
        ConnectionClass Teacher = new ConnectionClass();
        Connection connection= Teacher.getconnection();
        if ((password.getText()).equals(confirm.getText())){
            String sql ="INSERT INTO `teachers`(`Name`, `Email`, `Teacher ID`, `Username`, `Password`) VALUES('"+ name.getText() + "','" + email.getText() +"'," +id.getText() +",'"+ username.getText() +"','"+ password.getText() +"')";
            Statement statement= connection.createStatement();
            statement.execute(sql);
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
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText("Error:");
            alert.setContentText("Passwords Do not match");

            alert.showAndWait();
        }

    }
}
