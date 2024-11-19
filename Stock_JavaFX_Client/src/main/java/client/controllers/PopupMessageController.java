package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.Objects;

public class PopupMessageController {

    @FXML
    private HBox popupContainer;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView closeButton;

    public void setMessage(String message, String type) {
        messageLabel.setText(message);

        switch (type) {
            case "success":
                popupContainer.getStyleClass().add("success");
                System.out.println("Success");
                break;
            case "error":
                popupContainer.getStyleClass().add("error");
                System.out.println("Error");
                break;
            case "warning":
                popupContainer.getStyleClass().add("warning");
                System.out.println("Warring");
                break;
            default:
                popupContainer.getStyleClass().add("info");
                break;
        }

        // Устанавливаем иконку закрытия
        closeButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/close.png"))));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), popupContainer);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(3));
        fadeOut.setOnFinished(event -> popupContainer.setVisible(false));
        fadeOut.play();
    }

    @FXML
    private void handleClose() {
        popupContainer.setVisible(false);
    }
}
