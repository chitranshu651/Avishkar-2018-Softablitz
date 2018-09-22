package sample;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testingController {
    TestData data= new TestData();
    String Questions[][];
    String answers[];
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection= Student.getconnection();
    private int count=0;
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


    public void initialize() {
        try {
            String sql = "Select * from `questions` where `Test Id`='" + data.getTestCode() + "'";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                count++;
            }
            Questions = new String[count][5];
            answers = new String[count];
        }
        catch(SQLException e) {
            System.out.println("First Catch");
            System.out.println(e);

        }
        try{
            String sql2 = "Select * from `questions` where `Test Id`='" + data.getTestCode() + "'";
            Statement statement2 = connection.createStatement();
            ResultSet ks = statement2.executeQuery(sql2);
            for (int i = 0; i < count; i++) {
                ks.next();
                Questions[i][0] = ks.getString("A");
                Questions[i][1] = ks.getString("B");
                Questions[i][2] = ks.getString("C");
                Questions[i][3] = ks.getString("D");
            }
            for (int i = 0; i < count; i++) {
                System.out.println(Questions[i]);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    @FXML
    private void next(ActionEvent event){

    }
}
