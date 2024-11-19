package client.utils;

import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CustomUtils {

    // Метод для перехода на любую сцену
    public static void switchScene(Stage stage, String fxmlPath, String title) {
        try {
            // Проверка Stage
            Objects.requireNonNull(stage, "Stage must not be null");

            // Проверка наличия FXML файла
            URL resource = CustomUtils.class.getResource(fxmlPath);
            Objects.requireNonNull(resource, "FXML file not found: " + fxmlPath);

            // Загрузка сцены
            Parent root = FXMLLoader.load(resource);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Ошибка загрузки сцены: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static void delaySceneSwitch(Stage stage, String fxmlPath, String title, Duration time) {
        PauseTransition pause = new PauseTransition(time);
        pause.setOnFinished(event -> switchScene(stage, fxmlPath, title));
        pause.play();
    }


}