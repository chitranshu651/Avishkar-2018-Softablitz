package sample.Student;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConnectionClass;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestCode extends Thread{

    @FXML
    private JFXTextField  testCode;

    private ConnectionClass Student = new ConnectionClass();
    private Connection connection= Student.getconnection();
    static Stage stage;
//Add Section Wise testing
    @FXML
    private void Test(ActionEvent event)throws IOException, InterruptedException {
        Main.user.sendString("getSections");
        Main.user.sendString(testCode.getText());
        Session_Id.setTestId(testCode.getText());
        List<String> sections =(ArrayList<String>)(Main.user.recieveObject());
        String [] sectionar = sections.toArray(new String[sections.size()]);
        Session_Id.setSections(sectionar);
        Session_Id.setNo_sections(sectionar.length);
        Session_Id.setMarks(0);
        if(Session_Id.getNo_sections()!= 0){
            Parent home_parent= FXMLLoader.load(getClass().getResource("NextSection.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.show();
        }
        else{

        }
        }


    }


