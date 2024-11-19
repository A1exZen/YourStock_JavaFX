module client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires static lombok;
    requires com.google.gson;
    requires java.management;
    requires jbcrypt;

    opens client.models to com.google.gson, javafx.base;
    opens client.TCP to com.google.gson;
    opens client.controllers to javafx.fxml;
    opens client.TCP.enums to com.google.gson;

    exports client.enums to com.google.gson;
    exports client.TCP.enums to com.google.gson;
    exports client.controllers;
    exports client;
    opens client to javafx.fxml;
}