package sample.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;



public class TestResults {

    @FXML
    private Label Marks;

    private Session_Id currrent =new Session_Id();

    public void initialize(){
        Marks.setText("You Scored " + Session_Id.getMarks());
        Main.user.sendString("addMarks");
        Main.user.sendString(Session_Id.getTestId());
        Main.user.sendString(currrent.getUsername());
        Main.user.sendString(""+Session_Id.getMarks());

    }

    @FXML
    private void Home(ActionEvent event){
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.close();

    }
}
