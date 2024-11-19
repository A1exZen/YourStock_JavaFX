package client.controllers;

import client.TCP.ClientSocket;
import client.TCP.Request;
import client.TCP.Response;
import client.TCP.enums.RequestType;
import client.TCP.enums.ResponseType;
import client.models.UserDTO;
import client.utils.CustomUtils;
import com.google.gson.Gson;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

import static client.utils.CustomComponents.showPopupMessage;


public class Login {
    @FXML
    private Label labelMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonRegister;



    @FXML
    private void handleRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
        CustomUtils.switchScene(stage, "/pages/Register.fxml", "Регистрация");
    }

    @FXML
    public void handleLogin(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonLogin.getScene().getWindow();
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            showPopupMessage("Заполните все поля", "warning", stage);
            return;
        }
        UserDTO user = UserDTO.builder()
                .username(username.getText())
                .password(password.getText())
                .employee(null)
                .role(null)
                .build();

        try {
            System.out.println("Отправляемый JSON:");
            System.out.println(new Gson().toJson(new Request(new Gson().toJson(user), RequestType.LOGIN)));

            ClientSocket.getInstance().getOut().println(new Gson().toJson(new Request(new Gson().toJson(user), RequestType.LOGIN)));
            ClientSocket.getInstance().getOut().flush();

            String responseJson = ClientSocket.getInstance().getIn().readLine();
            if (responseJson == null || responseJson.isEmpty()) {
                showPopupMessage("Сервер не ответил. Повторите попытку позже.", "error", stage);
                return;
            }

            Response response = new Gson().fromJson(responseJson, Response.class);
            System.out.println(responseJson);
            if (response.getResponseType() == ResponseType.OK) {
                showPopupMessage("Вход выполнен успешно", "success", stage);

                UserDTO currentUser = new Gson().fromJson(response.getMessage(), UserDTO.class);
                ClientSocket.getInstance().setUser(currentUser);

                switch (currentUser.getRole()) {
                    case ADMIN:
                        CustomUtils.delaySceneSwitch(stage, "/pages/Main.fxml", "Admin page", Duration.seconds(1));
                        break;
                    case USER:
                        CustomUtils.delaySceneSwitch(stage, "/pages/UserMain.fxml", "User page", Duration.seconds(2));
                        break;
                    case MATERIAL_MANAGER:
                        CustomUtils.delaySceneSwitch(stage, "/pages/MaterialManMain.fxml", "Material Manager page", Duration.seconds(2));
                        break;
                    case MANUFACTURE_MANAGER:
                        CustomUtils.delaySceneSwitch(stage, "/pages/ManufactureManMain.fxml", "Manufacture Manager page", Duration.seconds(2));
                        break;
                    case STOCK_MANAGER:
                        CustomUtils.delaySceneSwitch(stage, "/pages/StockManMain.fxml", "Stock Manager page", Duration.seconds(2));
                        break;

                }
            } else {
                showPopupMessage(response.getMessage(), "error", stage);
            }
        } catch (IOException e) {
            showPopupMessage("Ошибка соединения с сервером", "error", stage);
            e.printStackTrace();
        }
    }
}
