package client.controllers;

import client.TCP.ClientSocket;
import client.TCP.Request;
import client.TCP.Response;
import client.TCP.enums.RequestType;
import client.TCP.enums.ResponseType;
import client.models.AddressDTO;
import client.models.PersonalDetailDTO;
import client.models.SupplierDTO;
import client.utils.CustomUtils;
import client.utils.ValidationUtils;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static client.utils.CustomComponents.showPopupMessage;

public class Suppliers {
    public TextField searchField;
    public Label labelMessage;
    @FXML
    private Label title;
    @FXML
    private AnchorPane addSupplierModal;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;

    @FXML
    private TableView<SupplierDTO> supplierTable;
    @FXML
    private TableColumn<SupplierDTO, String> nameColumn;
    @FXML
    private TableColumn<SupplierDTO, String> phoneColumn;
    @FXML
    private TableColumn<SupplierDTO, String> emailColumn;
    @FXML
    private TableColumn<SupplierDTO, String> cityColumn;
    @FXML
    private TableColumn<SupplierDTO, Void> editColumn;
    @FXML
    private TableColumn<SupplierDTO, Void> deleteColumn;
    @FXML
    private AnchorPane confirmDeleteModal;
    @FXML
    private Label modalTitle;

    @FXML
    private Button confirmDeleteButton, cancelDeleteButton;

    private SupplierDTO supplierToDelete;

    private final ObservableList<SupplierDTO> suppliersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        // Настройка колонок таблицы
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        phoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalDetails().getPhoneNumber()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalDetails().getEmail()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalDetails().getAddress().getCity()));
        addEditButtonToTable();
        addDeleteButtonToTable();
        // Загрузка данных из сервера при инициализации
        loadSuppliersFromServer();
    }

    private void showSupplierDetails(SupplierDTO supplier) {
        VBox detailsBox = new VBox();
        detailsBox.getChildren().add(new Label("Extra information about " + supplier.getName()));
        // Add more information here as needed
    }

    @FXML
    private void handleAddSupplier() {
        modalTitle.setText("Добавить Поставщика");
        clearForm();
        addSupplierModal.setVisible(true);
    }

    @FXML
    private void handleCloseModal() {
        // Закрываем модальное окно
        addSupplierModal.setVisible(false);
    }

    @FXML
    private void handleSaveSupplier() throws IOException {
        Stage stage = (Stage) title.getScene().getWindow();
        resetFieldsStyle();
        // Проверка, что все поля заполнены
        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
                addressField.getText().isEmpty() || cityField.getText().isEmpty()) {
            showPopupMessage("Все поля должны быть заполнены!", "error", stage);
            highlightInvalidFields();
            return;
        }
        if (!ValidationUtils.isNotEmpty(nameField.getText())) {
            showPopupMessage("Имя не может быть пустым!", "error", stage);
            nameField.getStyleClass().add("invalid");
            return;
        }
        if (!ValidationUtils.isValidPhoneNumber(phoneField.getText())) {
            showPopupMessage("Некорректный номер телефона!", "error", stage);
            phoneField.getStyleClass().add("invalid");
            return;
        }
        if (!ValidationUtils.isValidEmail(emailField.getText())) {
            showPopupMessage("Некорректный email!", "error", stage);
            emailField.getStyleClass().add("invalid");
            return;
        }
        if (!ValidationUtils.isNotEmpty(addressField.getText())) {
            showPopupMessage("Адрес не может быть пустым!", "error", stage);
            addressField.getStyleClass().add("invalid");
            return;
        }
        if (!ValidationUtils.isValidCity(cityField.getText())) {
            showPopupMessage("Некорректное название города!", "error", stage);
            cityField.getStyleClass().add("invalid");
            return;
        }
        if (supplierTable.getSelectionModel().getSelectedItem() != null) {
            SupplierDTO selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
            selectedSupplier.setName(nameField.getText());
            selectedSupplier.getPersonalDetails().setPhoneNumber(phoneField.getText());
            selectedSupplier.getPersonalDetails().setEmail(emailField.getText());
            selectedSupplier.getPersonalDetails().getAddress().setAddress(addressField.getText());
            selectedSupplier.getPersonalDetails().getAddress().setCity(cityField.getText());

            sendSupplierData(selectedSupplier, RequestType.UPDATE_SUPPLIER);
        } else {
            // Если поставщик новый, добавить его
            String lastName = "", firstName = "";
            String fullName = nameField.getText();
            String[] nameParts = fullName.split(" ");
            if (nameParts.length > 1) {
                firstName = nameParts[1];
            }
            lastName = nameParts[0];

            // Собираем данные из формы
            SupplierDTO supplierDTO = SupplierDTO.builder()
                    .name(nameField.getText())
                    .personalDetails(PersonalDetailDTO.builder()
                            .phoneNumber(phoneField.getText())
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(emailField.getText())
                            .address(AddressDTO.builder()
                                    .address(addressField.getText())
                                    .city(cityField.getText())
                                    .build())
                            .build())
                    .build();

            // Отправляем данные на сервер в отдельном потоке
            new Thread(() -> {
                try {
                    sendSupplierData(supplierDTO, RequestType.CREATE_SUPPLIER);
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        showPopupMessage("Ошибка соединения с сервером!", "error", stage);
                    });
                }
            }).start();
        }
    }

    private void resetFieldsStyle() {
        // Убираем класс "invalid" из всех полей
        nameField.getStyleClass().remove("invalid");
        phoneField.getStyleClass().remove("invalid");
        emailField.getStyleClass().remove("invalid");
        addressField.getStyleClass().remove("invalid");
        cityField.getStyleClass().remove("invalid");
    }

    private void highlightInvalidFields() {
        // Этот метод можно вызывать, чтобы подсветить поля при ошибке
        if (nameField.getText().isEmpty()) {
            nameField.getStyleClass().add("invalid");
        }
        if (phoneField.getText().isEmpty() || !ValidationUtils.isValidPhoneNumber(phoneField.getText())) {
            phoneField.getStyleClass().add("invalid");
        }
        if (emailField.getText().isEmpty() || !ValidationUtils.isValidEmail(emailField.getText())) {
            emailField.getStyleClass().add("invalid");
        }
        if (addressField.getText().isEmpty()) {
            addressField.getStyleClass().add("invalid");
        }
        if (cityField.getText().isEmpty() || !ValidationUtils.isValidCity(cityField.getText())) {
            cityField.getStyleClass().add("invalid");
        }
    }

    private void sendSupplierData(SupplierDTO supplierDTO, RequestType message) throws IOException {
        Stage stage = (Stage) title.getScene().getWindow();
        Request request = new Request(new Gson().toJson(supplierDTO), message);

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
    }

    private void loadSuppliersFromServer() {
        Platform.runLater(() -> {
            Stage stage = (Stage) title.getScene().getWindow();
            if (stage == null) {
                System.out.println("Stage is null");
                return;
            }

            // Создаем новый поток для загрузки данных с сервера
            new Thread(() -> {
                try {
                    Request request = new Request(null, RequestType.GET_ALL_SUPPLIERS);
                    ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                    ClientSocket.getInstance().getOut().flush();

                    String responseJson = ClientSocket.getInstance().getIn().readLine();
                    Response response = new Gson().fromJson(responseJson, Response.class);

                    // Проверка типа ответа
                    if (response.getResponseType() == ResponseType.OK) {
                        SupplierDTO[] suppliersArray = new Gson().fromJson(response.getMessage(), SupplierDTO[].class);

                        // Обновление UI на главном потоке
                        Platform.runLater(() -> {
                            suppliersList.setAll(suppliersArray); // Обновление ObservableList
                            System.out.println("Поставщики: " + Arrays.toString(suppliersArray));
                            supplierTable.setItems(suppliersList); // Обновление таблицы
                        });
                    } else {
                        Platform.runLater(() -> {
                            showPopupMessage("Ошибка при загрузке поставщиков", "error", stage);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        showPopupMessage("Ошибка соединения с сервером", "error", stage);
                    });
                }
            }).start(); // Запуск потока
        });
    }

    private void clearForm() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        addressField.clear();
        cityField.clear();
    }

    @FXML
    private void handleSearch(KeyEvent event) {
        String query = searchField.getText().toLowerCase();
        filterTable(query);
    }

    private void filterTable(String query) {
        ObservableList<SupplierDTO> filteredList = FXCollections.observableArrayList();

        for (SupplierDTO supplier : suppliersList) {
            if (supplier.getName().toLowerCase().contains(query)) {
                filteredList.add(supplier);
            }
        }

        supplierTable.setItems(filteredList); // Обновляем таблицу с отфильтрованными данными
    }

    private void addEditButtonToTable() {
        editColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Изменить");

            {
                editButton.getStyleClass().add("button-edit");
                editButton.setOnAction(event -> {
                    SupplierDTO supplier = getTableView().getItems().get(getIndex());
                    handleEditSupplier(supplier);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(column -> new TableCell<>() {
            private final Button deleteButton = new Button("Удалить");

            {
                deleteButton.getStyleClass().add("button-delete");
                deleteButton.setOnAction(event -> {
                    SupplierDTO supplier = getTableView().getItems().get(getIndex());
                    System.out.println("Click");
                    handleDeleteSupplier(supplier);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void handleEditSupplier(SupplierDTO supplier) {
        // Заполняем модальное окно данными поставщика для редактирования
        modalTitle.setText("Изменить Поставщика");
        nameField.setText(supplier.getName());
        phoneField.setText(supplier.getPersonalDetails().getPhoneNumber());
        emailField.setText(supplier.getPersonalDetails().getEmail());
        addressField.setText(supplier.getPersonalDetails().getAddress().getAddress());
        cityField.setText(supplier.getPersonalDetails().getAddress().getCity());
        // Открываем модальное окно
        addSupplierModal.setVisible(true);

        // Сохраняем изменения при нажатии кнопки "Сохранить"
        addSupplierModal.setOnMouseClicked(event -> {
            try {
                supplier.setName(nameField.getText());
                supplier.getPersonalDetails().setPhoneNumber(phoneField.getText());
                supplier.getPersonalDetails().setEmail(emailField.getText());
                supplier.getPersonalDetails().getAddress().setAddress(addressField.getText());
                supplier.getPersonalDetails().getAddress().setCity(cityField.getText());

                sendSupplierData(supplier, RequestType.UPDATE_SUPPLIER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleDeleteSupplier(SupplierDTO supplier) {
        // Сохраняем текущего поставщика, которого хотим удалить
        supplierToDelete = supplier;
        // Показываем окно подтверждения
        confirmDeleteModal.setVisible(true);
    }

    @FXML
    private void handleConfirmDelete() {
        Stage stage = (Stage) title.getScene().getWindow();
        new Thread(() -> {
            try {
                Request request = new Request(new Gson().toJson(supplierToDelete), RequestType.DELETE_SUPPLIER);
                ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
                ClientSocket.getInstance().getOut().flush();

                String responseJson = ClientSocket.getInstance().getIn().readLine();
                Response response = new Gson().fromJson(responseJson, Response.class);
                if (response.getResponseType() == ResponseType.OK) {
                    Platform.runLater(() -> {
                        showPopupMessage("Поставщик успешно удалён!", "success", stage);
                        loadSuppliersFromServer();
                    });
                } else {
                    Platform.runLater(() -> {
                        showPopupMessage("Ошибка удаления поставщика", "error", stage);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Platform.runLater(() -> confirmDeleteModal.setVisible(false));
            }
        }).start();
    }

    @FXML
    private void handleCancelDelete() {
        confirmDeleteModal.setVisible(false);
    }
}
