package sample.Student;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnectionClass;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;
import java.sql.Connection;

public class testingController {
    private String Questions[][];
    private String answers[];
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection = Student.getconnection();
    private int count = 0;
    @FXML
    private Label Question;

    @FXML
    private JFXRadioButton oA;

    @FXML
    private JFXRadioButton oB;

    @FXML
    private JFXRadioButton oC;

    @FXML
    private JFXRadioButton oD;

    @FXML
    private ToggleGroup Answers;

    @FXML
    private JFXButton nextbtn;

    @FXML
    private JFXButton backbtn;

    @FXML
    private JFXButton submitbtn;

    @FXML
    private Label Time;

    private Clock timer;

    private int number;

    private int marks = 0;




    public void initialize() {
        Main.user.sendString("getTime");
        Main.user.sendString(Session_Id.getSectionId());
        Main.user.sendString(Session_Id.getTestId());
        int time = Integer.parseInt(Main.user.recieveString());
        Main.user.sendString("getQuestions");
        Main.user.sendString(Session_Id.getSectionId());
        Main.user.sendString(Session_Id.getTestId());
        Questions = (String[][]) Main.user.recieveObject();
        answers = new String[Questions.length];
        number = Questions.length;
        timer = new Clock(time);
        Time.setText("" + (timer.getTime() / 60) + " : " + (timer.getTime() % 60));
        Question.setText(Questions[0][0]);
        oA.setText(Questions[0][1]);
        oB.setText(Questions[0][2]);
        oC.setText(Questions[0][3]);
        oD.setText(Questions[0][4]);
        backbtn.setDisable(true);
        for(int i=0; i<number; i++){
            answers[i] ="";
        }

    }

    @FXML
    private void next(ActionEvent event) {

        if (oA.isSelected()) {
            answers[count] = "A";
        } else if (oB.isSelected()) {
            answers[count] = "B";
        } else if (oC.isSelected()) {
            answers[count] = "C";
        } else if (oD.isSelected()) {
            answers[count] = "D";
        } else {
            answers[count] = "";
        }
        Answers.selectToggle(null);
        count++;
        if (count == number - 1) {
            nextbtn.setDisable(true);
        } else {
            nextbtn.setDisable(false);
        }
        backbtn.setDisable(false);
        if (answers[count] != null) {
            switch (answers[count]) {
                case "A":
                    Answers.selectToggle(oA);
                    break;
                case "B":
                    Answers.selectToggle((oB));
                    break;
                case "C":
                    Answers.selectToggle(oC);
                    break;
                case "D":
                    Answers.selectToggle(oD);
                    break;
                default:
                    Answers.selectToggle(null);
            }
        }
        Question.setText(Questions[count][0]);
        oA.setText(Questions[count][1]);
        oB.setText(Questions[count][2]);
        oC.setText(Questions[count][3]);
        oD.setText(Questions[count][4]);
    }

    @FXML
    private void back(ActionEvent event) {

        if (oA.isSelected()) {
            answers[count] = "A";
        } else if (oB.isSelected()) {
            answers[count] = "B";
        } else if (oC.isSelected()) {
            answers[count] = "C";
        } else if (oD.isSelected()) {
            answers[count] = "D";
        } else {
            answers[count] = "";
        }
        Answers.selectToggle(null);
        count--;
        if (count == 0) {
            backbtn.setDisable(true);
        } else {
            backbtn.setDisable(false);
        }
        nextbtn.setDisable(false);
        if (answers[count] != null) {
            switch (answers[count]) {
                case "A":
                    Answers.selectToggle(oA);
                    break;
                case "B":
                    Answers.selectToggle((oB));
                    break;
                case "C":
                    Answers.selectToggle(oC);
                    break;
                case "D":
                    Answers.selectToggle(oD);
                    break;
                default:
                    Answers.selectToggle(null);
            }
        }
        Question.setText(Questions[count][0]);
        oA.setText(Questions[count][1]);
        oB.setText(Questions[count][2]);
        oC.setText(Questions[count][3]);
        oD.setText(Questions[count][4]);

    }

    @FXML
    private void submit(ActionEvent event) throws IOException {
        if (oA.isSelected()) {
            answers[count] = "A";
        } else if (oB.isSelected()) {
            answers[count] = "B";
        } else if (oC.isSelected()) {
            answers[count] = "C";
        } else if (oD.isSelected()) {
            answers[count] = "D";
        } else {
            answers[count] = "";
        }
            for (int i = 0; i < number; i++) {
                if(answers[i].equalsIgnoreCase(Questions[i][5])){
                    marks++;
                };
            }

            Session_Id.addMarks(marks);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        }


    }

