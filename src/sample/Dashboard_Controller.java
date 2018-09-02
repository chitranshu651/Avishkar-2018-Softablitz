package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Dashboard_Controller {
    @FXML
    private Label User_ID;

    private Session_Id current = new Session_Id();

    public void initialize() {
        User_ID.setText("Welcome, " + current.getUsername());
    }


        //SQL PULL OF DATA
    public void Logout(MouseEvent event) throws IOException{
        System.exit(0);
    }
    public void P_Edit(ActionEvent event) throws IOException {
        Parent Sign= FXMLLoader.load(getClass().getResource("/P_Edit.fxml"));
        Scene P_Edit = new Scene(Sign);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(P_Edit);//Profile Edit link
        window.show();
    }
    public void Home(ActionEvent event){}
    public void S_List(ActionEvent event){}
    public void T_List(ActionEvent event){}
}
