package sample.Student;

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
import sample.Session_Id;

import java.io.IOException;

public class NextSection {

    private int i=0;

    private String [] sections = Session_Id.getSections();

    @FXML
    private void Section(ActionEvent event) throws IOException {
        if(Session_Id.getNo_sections()!=0) {
            if (i < Session_Id.getNo_sections()) {
                Stage stage = new Stage();
                Session_Id.setSectionId(sections[i]);
                Parent root = FXMLLoader.load(
                        sample.Student.testingController.class.getResource("testing.fxml"));
                stage.setScene(new Scene(root));
                stage.setTitle("Get Ready !!!");
                stage.sizeToScene();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initStyle(StageStyle.UTILITY);
                stage.initOwner(
                        ((Node) event.getSource()).getScene().getWindow());
                stage.show();
                i++;

            } else {

                Parent home_parent = FXMLLoader.load(getClass().getResource("TestResults.fxml"));
                Scene Home = new Scene(home_parent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(Home);
                window.show();
            }
        }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText("No Test Found");
                alert.setContentText("No Test was found with your code");
                alert.showAndWait();
            }

        }

    }
