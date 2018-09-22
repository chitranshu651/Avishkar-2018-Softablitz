package sample;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDashboard_Controller {
    @FXML
    private Label User_ID;

    @FXML
    private Label lblSubject;

    @FXML
    private Label lblTest;

    private Session_Id current = new Session_Id();
    private ConnectionClass Student = new ConnectionClass();
    private Connection connection= Student.getconnection();
    public void initialize() throws SQLException {
        String name="";
        String sql="Select `name` from students where Registration_No=" + Integer.parseInt(current.getUsername());
        Statement statement = connection.createStatement();
        ResultSet rs= statement.executeQuery(sql);
        while(rs.next()){
            name= rs.getString("Name");
        }
        User_ID.setText("Welcome, " + name);
    }


    public void Logout(ActionEvent event) throws IOException{
        System.exit(0);
    }

    @FXML
    private void Results(MouseEvent event) throws IOException {
        //Loads Results Page
        Parent home_parent= FXMLLoader.load(getClass().getResource("Result.fxml"));
        Scene Home= new Scene(home_parent);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(Home);
        window.show();
    }

    @FXML
    private void Test(MouseEvent event) throws IOException{
        //Loads Test Code Page
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                TestCode.class.getResource("TestCode.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Get Ready !!!");
        stage.sizeToScene();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }


    public void Home(MouseEvent event){}
}
