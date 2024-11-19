package client.controllers;

import client.TCP.ClientSocket;
import client.TCP.Request;
import client.TCP.Response;
import client.TCP.enums.RequestType;
import client.TCP.enums.ResponseType;
import client.enums.RoleType;
import client.models.UserDTO;
import client.utils.CustomUtils;
import client.utils.PasswordEncoder;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.io.IOException;

import static client.utils.CustomComponents.showPopupMessage;

public class Register {
    @FXML
    private Label popupLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label labelMessage;
    @FXML
    private Button buttonLogin;



    @FXML
    private ComboBox<RoleType> roleComboBox;

    @FXML
    public void initialize() {
        loadRoles();
    }
    private void loadRoles() {
        roleComboBox.setItems(FXCollections.observableArrayList(RoleType.values()));
    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonLogin.getScene().getWindow();
        CustomUtils.switchScene(stage, "/pages/Login.fxml", "Авторизация");
    }

    @FXML
    public void handleRegister(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) buttonLogin.getScene().getWindow();
        String login = loginField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        RoleType selectedRole = roleComboBox.getValue();

        if (login.isEmpty() || password.isEmpty()) {
            showPopupMessage("Заполните логин и пароль", "warning", stage);
            return;
        }
        if (login.length() < 4 || password.length() < 4) {
            System.out.println("Error---------------");
            showPopupMessage("Длина минимум 4 символа", "warn", stage);
            return;
        }
        if (!password.equals(confirmPassword)) {
            showPopupMessage("Пароли не совпадают", "warning", stage);
            return;
        }
        if (selectedRole == null) {
            showPopupMessage("Выберите роль", "warning", stage);
            return;
        }

        UserDTO userDTO = UserDTO.builder()
                .username(login)
                .password(password)
                .employee(null)
                .role(selectedRole)
                .build();

        Request request = new Request(new Gson().toJson(userDTO), RequestType.REGISTER);
        // Создание JSON-запроса
        String requestJson = new Gson().toJson(request);
        System.out.println(requestJson);
        // Отправка запроса на сервер
        ClientSocket.getInstance().getOut().println(requestJson);
        ClientSocket.getInstance().getOut().flush();

        String responseJson = ClientSocket.getInstance().getIn().readLine();
        Response response = new Gson().fromJson(responseJson, Response.class);

        // Проверка ответа от сервера
        if (response.getResponseType() == ResponseType.OK) {
            showPopupMessage("Регистрация успешна!", "success", stage);

            CustomUtils.delaySceneSwitch(stage, "/pages/Main.fxml", "Главная страница", Duration.millis(3));

        } else {
            showPopupMessage("Error", "error", stage);
        }
    }


}
