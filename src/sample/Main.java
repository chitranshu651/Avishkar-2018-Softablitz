package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Server_Client.Client;

public class Main extends Application {

    public static Client user= new Client("127.0.0.1", 5005);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("SQuiz");
        primaryStage.setScene(new Scene(root, 620, 420));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
