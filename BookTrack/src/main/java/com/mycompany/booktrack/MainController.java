package com.mycompany.booktrack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainController {

    @FXML
    private Button manageBooksButton;
    @FXML
    private Button manageUsersButton;
    @FXML
    private Button manageLoansButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private StackPane mainContent;

    // Initialize method to load dashboard.fxml by default when the application starts
    @FXML
    public void initialize() {
        loadFXMLPage("dashboard.fxml");  // Load dashboard page by default
    }

    // Handles "Manage Books" button click
    @FXML
    private void handleManageBooks() {
        loadFXMLPage("manage_books.fxml");
    }

    // Handles "Manage Users" button click
    @FXML
    private void handleManageUsers() {
        loadFXMLPage("manage_users.fxml");
    }

    // Handles "Manage Loans" button click
    @FXML
    private void handleManageLoans() {
        loadFXMLPage("manage_loans.fxml");
    }

    // Handles "Dashboard" button click
    @FXML
    private void handleDashboard() {
        loadFXMLPage("dashboard.fxml");
    }

    // Generic method to load an FXML file
    private void loadFXMLPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent page = loader.load();

            // Clear existing content and add the new content
            mainContent.getChildren().clear();
            mainContent.getChildren().add(page);

        } catch (IOException e) {
            showAlert("Error", "Failed to load the page: " + fxmlFile);
            e.printStackTrace();
        }
    }

    // Displays an alert dialog for error messages
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}