package sample;

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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SubjectController {

    @FXML
    private JFXTextField Name;


    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public void Logout(ActionEvent event){

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
        ConnectionClass Teacher = new ConnectionClass();
        Connection connection= Teacher.getconnection();
        Session_Id teacher= new Session_Id();

        //Creates a Random Subject Id
         StringBuilder Scode = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            Scode.append(ALPHABET.charAt((int)(62*Math.random())));
        }
        //Puts the Subject into Database
        String sql ="INSERT INTO `subjects` (`Name`, `Teacher Id`, `Subject Id`) VALUES ('" +Name.getText() +"'," + teacher.getUsername()+ ",'" + Scode+"');";
        Statement statement= connection.createStatement();
        statement.execute(sql);
        //Presents a Alert for Success of Subject Creation
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
}



