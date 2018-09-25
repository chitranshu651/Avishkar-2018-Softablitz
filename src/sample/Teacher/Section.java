package sample.Teacher;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;

public class Section {

    @FXML
    private JFXTextField Name;

    @FXML
    private JFXTextField NumberQ;

    @FXML
    private JFXTextField Time;

    private Session_Id current =new Session_Id();

    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private int i=0;


    @FXML
    private void create(ActionEvent event) throws IOException {

        StringBuilder SectionCode = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            //Generates a Random Subject Code
            SectionCode.append(ALPHABET.charAt((int) (62 * Math.random())));
        }

        Main.user.sendString("createSection");
        Main.user.sendString(Name.getText());
        Main.user.sendString(NumberQ.getText());
        Main.user.sendString(Time.getText());
        Main.user.sendString(Session_Id.getTestId());
        Main.user.sendString(SectionCode.toString());
        boolean check=Main.user.recieveBoolean();
        if(check){
            Alert alert=  new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Section Created");
            alert.showAndWait();


            Session_Id.setNo_Question(Integer.parseInt(NumberQ.getText()));
            Session_Id.setSectionId(SectionCode.toString());
            Name.setText("");
            NumberQ.setText("");
            Time.setText("");
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(sample.Teacher.QuestionGeneration.class.getResource("QuestionGeneration.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Questions");
            stage.sizeToScene();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            i++;
            if(i==Session_Id.getNo_sections()){
                Parent home_parent= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                Scene Home= new Scene(home_parent);

                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(Home);
                window.show();
            }
        }

    }
}
