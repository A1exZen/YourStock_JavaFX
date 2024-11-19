package client;

import client.TCP.ClientSocket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import client.utils.ClientSocket;
import java.io.IOException;
import java.util.Objects;


public class Main extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        ClientSocket.getInstance().getSocket();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/pages/Login.fxml")));
        Scene scene = new Scene(parent);
        stage.setTitle("Вход в систему");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}