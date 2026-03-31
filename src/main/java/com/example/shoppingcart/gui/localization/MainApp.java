package com.example.shoppingcart.gui.localization;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Set default locale
        Locale locale = Locale.ENGLISH;

        // Load ResourceBundle with UTF-8 support for non-Latin languages
        ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale, new UTF8Control());

        // Load FXML with ResourceBundle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/shopping_cart.fxml"), bundle);
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle(bundle.getString("app.title"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}