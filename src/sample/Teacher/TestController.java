package sample.Teacher;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ConnectionClass;
import sample.Main;
import sample.Session_Id;

import javax.security.auth.Subject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestController {

    @FXML
    private JFXComboBox<String> Subject;

    @FXML
    private JFXComboBox<String> NumberQ;
    private Session_Id current= new Session_Id();

    public void initialize() {
        //Initializes the subject dropdown with the teachers subject
        Subject.getItems().clear();
        Main.user.sendString("getSubjects");
        Main.user.sendString(current.getUsername());
        List subjectList=  (ArrayList)(Main.user.recieveObject());
        String[] subjects= (String[]) subjectList.toArray();
        for(String x: subjects){
            Subject.getItems().add(x);
        }
        NumberQ.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11","12","13","14");
    }

    public void Logout(ActionEvent event){
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    private void Subject(MouseEvent event) throws IOException {
        Parent home_parent= FXMLLoader.load(getClass().getResource("Subject.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }
    @FXML
    private void Home(MouseEvent event) throws IOException{
        Parent home_parent= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void Test(MouseEvent event) throws IOException{


    }

    @FXML
    private void create(ActionEvent event){

    }


}
