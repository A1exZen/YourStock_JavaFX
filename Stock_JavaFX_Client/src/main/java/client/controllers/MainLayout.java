//package client.controllers;
//
//import client.TCP.ClientSocket;
//import client.utils.CustomUtils;
//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.IOException;
//
//public class MainLayout {
//    @FXML
//    private VBox navbar;
//    @FXML
//    private VBox menuItems;
//    @FXML
//    private Button toggleButton;
//    @FXML
//    private Button exitButton;
//    @FXML
//    private ImageView toggleIcon;
//    @FXML
//    private StackPane centerPane;
//
//    private boolean isCollapsed = false;
//    private final double expandedWidth = 200;
//    private final double collapsedWidth = 60;
//    private final double animationDuration = 0.3;
//
//    @FXML
//    public void initialize() {
//        navbar.setPrefWidth(expandedWidth);
//        showMenuText(true);
//        toggleButton.setOnAction(event -> toggleNavbar());
//    }
//
//    @FXML
//    public void handleExitButton(ActionEvent event) {
//        // Закрытие приложения
//        Stage stage = (Stage) exitButton.getScene().getWindow();
//        stage.close();
//    }
//
//    private void toggleNavbar() {
//        if (isCollapsed) {
//            expandNavbar();
//        } else {
//            collapseNavbar();
//        }
//    }
//
//
//    private void expandNavbar() {
//        animateNavbar(collapsedWidth, expandedWidth);
//        showMenuText(true);
//        isCollapsed = false;
//    }
//
//    private void collapseNavbar() {
//        animateNavbar(expandedWidth, collapsedWidth);
//        showMenuText(false);
//        isCollapsed = true;
//    }
//
//    private void animateNavbar(double fromWidth, double toWidth) {
//        Timeline timeline = new Timeline();
//        KeyValue keyValue = new KeyValue(navbar.prefWidthProperty(), toWidth);
//        KeyFrame keyFrame = new KeyFrame(Duration.seconds(animationDuration), keyValue);
//        timeline.getKeyFrames().add(keyFrame);
//        timeline.play();
//    }
//
//    private void showMenuText(boolean isVisible) {
//        menuItems.getChildren().forEach(node -> {
//            HBox menuItem = (HBox) node;
//            Label label = (Label) menuItem.getChildren().get(1);
//            label.setVisible(isVisible);
//        });
//    }
//
//    public void navigateToMaterials(MouseEvent mouseEvent) {
//    }
//
//    public void navigateToProducts(MouseEvent mouseEvent) {
//    }
//
//    public void navigateToSuppliers(MouseEvent mouseEvent) {
//        StackPane pane = new StackPane();
//        pane.getChildren().add(new Label("Suppliers Page"));
//        switchContent(pane);
//    }
//
//    public void navigateToCustomers(MouseEvent mouseEvent) {
//
//    }
//
//    public void navigateToUsers(MouseEvent mouseEvent) {
//    }
//
//    public void navigateToAccount(MouseEvent mouseEvent) {
//    }
//    public void switchContent(Node content) {
//        centerPane.getChildren().setAll(content);
//    }
//
//    // Метод для загрузки FXML файла
//    private Node loadFXML(String fxmlPath) {
//        try {
//            return FXMLLoader.load(getClass().getResource(fxmlPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new Label("Не удалось загрузить страницу");
//        }
//    }
//
//    // Метод для навигации на страницу Поставщики
//    @FXML
//    public void navigateToSuppliers() {
//        Node suppliersPage = loadFXML("/client/views/Suppliers.fxml");
//        switchContent(suppliersPage);
//    }
//
//
//}
