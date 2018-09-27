package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ConnectionClass;
import sample.Main;
import sample.Session_Id;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SeeTest {
    private ConnectionClass see =new ConnectionClass();
    private Connection connection = see.getconnection();

    @FXML
    private TableView<String> Subjects;
    private Session_Id current = new Session_Id();


    public void initialize() throws SQLException {
        String sql = "SELECT `Name`, `Test Id` FROM `test` WHERE `Teacher Id` =" + current.getUsername() +";";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            Subjects.getItems().addAll(rs.getString("Name"), rs.getString("Test Id"));
        }

    }
    public void Logout(ActionEvent event) throws IOException {
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


