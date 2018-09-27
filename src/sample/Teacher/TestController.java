package sample.Teacher;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import javax.security.auth.Subject;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TestController {

    @FXML
    private JFXComboBox<String> Subject;

    @FXML
    private JFXTextField Name;


    @FXML
    private JFXTextField Sections;


    private Session_Id current= new Session_Id();

    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public void initialize() {
        //Initializes the subject dropdown with the teachers subject
        Subject.getItems().clear();
        Main.user.sendString("getSubjects");
        Main.user.sendString(current.getUsername());
        List<String> subjectList=  (ArrayList<String>)(Main.user.recieveObject());
        String[] subjects= subjectList.toArray(new String[subjectList.size()]);
        for(String x: subjects){
            Subject.getItems().add(x);
        }

    }

    public void Logout(ActionEvent event){
        Main.user.sendString("Exit");
        System.exit(0);
    }

    @FXML
    private void Subject(MouseEvent event) throws IOException
    {
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
    private void create(ActionEvent event)throws IOException{
        StringBuilder Tcode = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            //Generates a Random Subject Code
            Tcode.append(ALPHABET.charAt((int) (62 * Math.random())));
        }
        Main.user.sendString("getSubjectId");
        Main.user.sendString(Subject.getSelectionModel().getSelectedItem());
        Main.user.sendString(current.getUsername());
        String Scode= Main.user.recieveString();

        Session_Id.setTestId(Tcode.toString());
        Session_Id.setNo_sections(Integer.parseInt(Sections.getText()));
        Main.user.sendString("createTest");
        Main.user.sendString(Name.getText());
        Main.user.sendString(Tcode.toString());
        Main.user.sendInt(Integer.parseInt(Sections.getText()));
        Main.user.sendString(Scode);
        Main.user.sendString(current.getUsername());
        boolean check=Main.user.recieveBoolean();
        if(check){
            Alert alert=  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Test Created");
            alert.showAndWait();

            Session_Id.setNo_sections(Integer.parseInt(Sections.getText()));
            Session_Id.setTestId(Tcode.toString());
            Parent home_parent= FXMLLoader.load(getClass().getResource("Section.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.show();
        }
        else{
            Alert alert=  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failure");
            alert.setHeaderText("Failure");
            alert.setContentText("Test Creation Failed");
            alert.showAndWait();
        }

    }


}
