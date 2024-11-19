package client.utils;

import client.controllers.PopupMessageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomComponents {
    /**
     * Метод для отображения popup-сообщений.
     *
     * @param stage   Текущий Stage, в котором отображается popup.
     * @param message Текст сообщения.
     * @param type    Тип сообщения (success, error, warning, info).
     */
    public static void showPopupMessage(String message, String type, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(CustomComponents.class.getResource("/components/PopupMessage.fxml"));
            Parent popupRoot = loader.load();

            // Получаем контроллер для настройки сообщения
            PopupMessageController controller = loader.getController();
            controller.setMessage(message, type);

            // Создаем и настраиваем popup
            Popup popup = new Popup();
            popup.getContent().add(popupRoot);
            popup.setAutoHide(true);

            // Устанавливаем позицию popup в левый верхний угол
            double offsetX = 10; // Отступ от левого края
            double offsetY = 10; // Отступ от верхнего края
            popup.show(stage, stage.getX() + offsetX, stage.getY() + offsetY);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
