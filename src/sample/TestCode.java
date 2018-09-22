package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestCode {

    @FXML
    private JFXTextField  testCode;

    private ConnectionClass Student = new ConnectionClass();
    private Connection connection= Student.getconnection();

    @FXML
    private void Test(ActionEvent event)throws IOException {
        int time=0;
        try {
            String sql = "SELECT `time` from `Test` where `Test Id`='" + testCode.getText() + "'";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                time = Integer.parseInt(rs.getString("Time"));
            }
            TestData test = new TestData(testCode.getText(), time);
            Parent home_parent= FXMLLoader.load(getClass().getResource("testing.fxml"));
            Scene Home= new Scene(home_parent);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
            window.setScene(Home);
            window.setFullScreen(true);
            window.show();

        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("Error here");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Test id found");
            alert.setContentText("Sorry No Test was Found");
            alert.showAndWait();
        }
    }
}
