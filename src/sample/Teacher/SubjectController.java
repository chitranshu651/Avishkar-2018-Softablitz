package sample.Teacher;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ConnectionClass;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SubjectController {

    @FXML
    private JFXTextField Name;

    @FXML
    private JFXTextField SubjectCode;

    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public void Logout(ActionEvent event){
        Main.user.sendString("Exit");
        System.exit(0);
    }
    @FXML
    private void Subject(MouseEvent event) throws IOException{

    }
    @FXML
    private void Home(MouseEvent event) throws IOException{
        //Goes back to Dashboard
        Parent home_parent= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void Test(MouseEvent event) throws IOException{
        //Goes to Test Creation
        Parent home_parent= FXMLLoader.load(getClass().getResource("Test.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void create(ActionEvent event) throws SQLException, IOException{
        //Creates a Subject
        Session_Id teacher= new Session_Id(); // gets the Users Username

        Main.user.sendString("addSubject");
        Main.user.sendString(Name.getText());
        Main.user.sendString(teacher.getUsername());
        //Creates a Random Subject Id
         StringBuilder Scode = new StringBuilder(7);
        if(SubjectCode.getText().equals("")) {
            for (int i = 0; i < 7; i++) {
                //Generates a Random Subject Code
                Scode.append(ALPHABET.charAt((int) (62 * Math.random())));
            }
            Main.user.sendString(Scode.toString());
        }
        else{
            Main.user.sendString(SubjectCode.getText());
            }
        //Puts the Subject into Database
        boolean check= Main.user.recieveBoolean();
        if(check){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Subject Created");
            alert.showAndWait();
            //Loads the Dasboard
            Parent home_parent= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.show();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setHeaderText("Failure");
            alert.setContentText("Subject Creation Failed");
            alert.showAndWait();
        }

    }
}



