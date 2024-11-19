package client.controllers;

import client.TCP.ClientSocket;
import client.TCP.Request;
import client.TCP.Response;
import client.TCP.enums.ResponseType;
import client.models.AddressDTO;
import client.models.EmployeeDTO;
import client.models.UserDTO;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static client.utils.CustomComponents.showPopupMessage;

public class Account {
    @FXML
    private AnchorPane detailsForm;
    @FXML
    private Label modalTitle;
    @FXML
    private Label role_label;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField addressField;
    @FXML
    private Button buttonEdit;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;

    private final UserDTO user = ClientSocket.getInstance().getUser();

    @FXML
    public void initialize() {
        // Заполняем поля данными пользователя
        if (user != null && user.getEmployee() != null && user.getEmployee().getPersonalDetails() != null) {
            nameField.setText(user.getUsername());
            passwordField.setText(user.getPassword() != null ? user.getPassword() : "*******");
            phoneField.setText(user.getEmployee().getPersonalDetails().getPhoneNumber());
            emailField.setText(user.getEmployee().getPersonalDetails().getEmail());
            cityField.setText(user.getEmployee().getPersonalDetails().getAddress() != null
                    ? user.getEmployee().getPersonalDetails().getAddress().getCity() : "");
            addressField.setText(user.getEmployee().getPersonalDetails().getAddress() != null
                    ? user.getEmployee().getPersonalDetails().getAddress().getAddress() : "");

            // Отображаем роль
            role_label.setText(user.getEmployee().getPosition() != null ? user.getEmployee().getPosition() : "Пользователь");

            // Обновляем прогресс заполнения
            updateProgressBar();
        }
    }

    public void handleChangeData(ActionEvent actionEvent) {
        Stage stage = (Stage) detailsForm.getScene().getWindow();
        // Обновляем данные пользователя из полей ввода
        user.setUsername(nameField.getText());
        user.setPassword(passwordField.getText().equals("*******") ? user.getPassword() : passwordField.getText());
        if (user.getEmployee() == null) {
            user.setEmployee(new EmployeeDTO());
        }
        user.getEmployee().getPersonalDetails().setPhoneNumber(phoneField.getText());
        user.getEmployee().getPersonalDetails().setEmail(emailField.getText());

        if (user.getEmployee().getPersonalDetails().getAddress() == null) {
            user.getEmployee().getPersonalDetails().setAddress(new AddressDTO());
        }
        user.getEmployee().getPersonalDetails().getAddress().setCity(cityField.getText());
        user.getEmployee().getPersonalDetails().getAddress().setAddress(addressField.getText());

        try {
            Request request = new Request(new Gson().toJson(userDTO), message);

            // Создание JSON-запроса
            String requestJson = new Gson().toJson(request);
            System.out.println("Отправка запроса на сервер: " + requestJson);

            // Отправка запроса на сервер
            ClientSocket.getInstance().getOut().println(requestJson);
            ClientSocket.getInstance().getOut().flush();
            // Чтение ответа от сервера
            String responseJson = ClientSocket.getInstance().getIn().readLine();
            Response response = new Gson().fromJson(responseJson, Response.class);
            Platform.runLater(() -> {
                if (response.getResponseType() == ResponseType.OK) {
                    showPopupMessage("Поставщик успешно добавлен!", "success", stage);
                    addSupplierModal.setVisible(false);
                    loadSuppliersFromServer();
                    clearForm();
                } else {
                    showPopupMessage("Ошибка добавления поставщика", "error", stage);
                }
            });
            if (isUpdated) {
                showPopupMessage("Данные успешно обновлены!", "info", stage);
                updateProgressBar();
            } else {
                showPopupMessage("Ошибка при обновлении данных.", "error", stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showPopupMessage("Ошибка при сохранении данных: " + e.getMessage(), "error", stage);
        }
    }

    private void updateProgressBar() {
        double filledFields = 0;
        double totalFields = 6; // Количество полей для проверки

        if (!nameField.getText().isEmpty()) filledFields++;
        if (!passwordField.getText().isEmpty() && !passwordField.getText().equals("*******")) filledFields++;
        if (!phoneField.getText().isEmpty()) filledFields++;
        if (!emailField.getText().isEmpty()) filledFields++;
        if (!cityField.getText().isEmpty()) filledFields++;
        if (!addressField.getText().isEmpty()) filledFields++;

        double progress = filledFields / totalFields;
        progressBar.setProgress(progress);
        progressIndicator.setProgress(progress);
    }

    public void handleExit(ActionEvent actionEvent) {
        System.out.println(user);
    }
}
