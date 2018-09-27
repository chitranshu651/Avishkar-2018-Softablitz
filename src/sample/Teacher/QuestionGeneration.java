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
import javafx.stage.Stage;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;

public class QuestionGeneration {

    private int TestQuestion;

    private String TestId;
    private int count =0;

    public void initialize(){
        TestQuestion= Session_Id.getNo_Question();
        TestId=Session_Id.getTestId();
        Answer.getItems().addAll("A", "B", "C", "D");
    }
    @FXML
    private JFXTextArea Question;

    @FXML
    private JFXTextArea OptionA;

    @FXML
    private JFXTextArea OptionB;

    @FXML
    private JFXTextArea OptionC;

    @FXML
    private JFXTextArea OptionD;

    @FXML
    private JFXComboBox<String> Answer ;


    private Session_Id current =new Session_Id();


    @FXML
    private void submit(ActionEvent event) throws IOException {
        if(count!=(this.TestQuestion-1)){
            Main.user.sendString("addQuestion");
            Main.user.sendString(Session_Id.getSectionId());
            Main.user.sendString(Question.getText());
            Main.user.sendString(OptionA.getText());
            Main.user.sendString(OptionB.getText());
            Main.user.sendString(OptionC.getText());
            Main.user.sendString(OptionD.getText());
            Main.user.sendString(Answer.getSelectionModel().getSelectedItem());
            Main.user.sendString(""+count);
            Main.user.sendString(Session_Id.getTestId());
            boolean check = Main.user.recieveBoolean();
            if(check){
                count++;
                Question.setText("");
                OptionA.setText("");
                OptionB.setText("");
                OptionC.setText("");
                OptionD.setText("");

            }

        }
        else{
            Main.user.sendString("addQuestion");
            Main.user.sendString(Session_Id.getSectionId());
            Main.user.sendString(Question.getText());
            Main.user.sendString(OptionA.getText());
            Main.user.sendString(OptionB.getText());
            Main.user.sendString(OptionC.getText());
            Main.user.sendString(OptionD.getText());
            Main.user.sendString(Answer.getSelectionModel().getSelectedItem());
            Main.user.sendString(""+ count);
            Main.user.sendString(Session_Id.getTestId());
            boolean check = Main.user.recieveBoolean();
            if(check){
                count++;
                Question.setText("");
                OptionA.setText("");
                OptionB.setText("");
                OptionC.setText("");
                OptionD.setText("");

            }


            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.close();
        }


    }


}
