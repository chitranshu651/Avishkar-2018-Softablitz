package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;

public class Dashboard_Controller {
    @FXML
    private Label User_ID;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblTest;

    private Session_Id current = new Session_Id();

    public void initialize() {
        Main.user.sendString("Name");
        Main.user.sendString("Teacher");
        Main.user.sendString(current.getUsername());
        String name =Main.user.recieveString();
        User_ID.setText("Welcome, " + name);
    }


    public void Logout(ActionEvent event) throws IOException{
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    private void Subject(MouseEvent event) throws IOException{
        //Loads Subject Creation Page
        Parent home_parent= FXMLLoader.load(getClass().getResource("Subject.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void Test(MouseEvent event) throws IOException{
        //Loads Test Creation Page
        Parent home_parent= FXMLLoader.load(getClass().getResource("Test.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }


    public void Home(MouseEvent event){}
}
