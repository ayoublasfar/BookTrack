package com.mycompany.booktrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;
        App.primaryStage.setTitle("BookTrack - Gestion de bibliothèque");

        // Load the main view (this is where the MainController is used)
        initMainView();
    }

    public void initMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("booktrack.fxml"));
            BorderPane mainLayout = loader.load();

            // Set the scene and show it
            Scene scene = new Scene(mainLayout, 880, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}