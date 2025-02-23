package com.mycompany.booktrack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class ManageLoansController {

    @FXML private TableView<Loan> loansTable;
    @FXML private TableColumn<Loan, Integer> idColumn;
    @FXML private TableColumn<Loan, Integer> bookIdColumn;
    @FXML private TableColumn<Loan, Integer> userIdColumn;
    @FXML private TableColumn<Loan, LocalDate> loanDateColumn;
    @FXML private TableColumn<Loan, LocalDate> returnDateColumn;
    @FXML private TableColumn<Loan, Boolean> returnedColumn;

    @FXML private TextField bookIdField;
    @FXML private TextField userIdField;
    @FXML private TextField loanDateField;
    @FXML private TextField returnDateField;
    @FXML private TextField returnedField;
    @FXML private TextField searchField; // New search field

    private ObservableList<Loan> loanList = FXCollections.observableArrayList();
    private LoanDAO loanDAO = new LoanDAO();

    @FXML
public void initialize() {
    // Setup columns in the table
    idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
    bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().getBookIdProperty().asObject());
    userIdColumn.setCellValueFactory(cellData -> cellData.getValue().getUserIdProperty().asObject());
    loanDateColumn.setCellValueFactory(cellData -> cellData.getValue().getLoanDateProperty());
    returnDateColumn.setCellValueFactory(cellData -> cellData.getValue().getReturnDateProperty());
    returnedColumn.setCellValueFactory(cellData -> cellData.getValue().getReturnedProperty());

    loansTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            bookIdField.setText(String.valueOf(newValue.getBookId()));
            userIdField.setText(String.valueOf(newValue.getUserId()));
            loanDateField.setText(newValue.getLoanDate().toString());
            returnDateField.setText(newValue.getReturnDate() != null ? newValue.getReturnDate().toString() : "");
            returnedField.setText(String.valueOf(newValue.isReturned()));
        } else {
            // Default behavior if no row is selected
            LocalDate today = LocalDate.now();
            loanDateField.setText(today.toString());
            returnDateField.setText(today.toString());
        }
    });

    // Load loans from the database
    loanList.setAll(loanDAO.getAllLoans());
    
    // Set the table's data source
    loansTable.setItems(loanList);

    // Set default values for loanDateField and returnDateField on initialization
    LocalDate today = LocalDate.now();
    loanDateField.setText(today.toString());
    returnDateField.setText(today.toString());
}


    @FXML
public void handleAddLoan(ActionEvent event) {
    try {
        int bookId = Integer.parseInt(bookIdField.getText());
        int userId = Integer.parseInt(userIdField.getText());
        LocalDate loanDate = LocalDate.parse(loanDateField.getText());
        LocalDate returnDate = LocalDate.parse(returnDateField.getText());
        boolean returned = Boolean.parseBoolean(returnedField.getText());

        Loan newLoan = new Loan(0, bookId, userId, loanDate, returnDate, returned);
        loanDAO.addLoan(newLoan);

        loanList.setAll(loanDAO.getAllLoans());

    } catch (UserNotFoundException | BookNotFoundException | BookUnavailableException e) {
        showAlert("Error", e.getMessage());
    } catch (Exception e) {
        showAlert("Error", "An error occurred while adding the loan.");
    }
}


    @FXML
    public void handleUpdateLoan(ActionEvent event) {
    Loan selectedLoan = loansTable.getSelectionModel().getSelectedItem();
    if (selectedLoan != null) {
        // Ask for confirmation before updating
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText("Are you sure you want to update this loan?");
        alert.setContentText("Changes will be saved to the database.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            selectedLoan.setBookId(Integer.parseInt(bookIdField.getText()));
            selectedLoan.setUserId(Integer.parseInt(userIdField.getText()));
            selectedLoan.setLoanDate(LocalDate.parse(loanDateField.getText()));
            selectedLoan.setReturnDate(LocalDate.parse(returnDateField.getText()));
            selectedLoan.setReturned(Boolean.parseBoolean(returnedField.getText()));

            // Update loan in the database
            loanDAO.updateLoan(selectedLoan);

            // Refresh the table with updated list from database
            loansTable.refresh();

            // Clear input fields
            bookIdField.clear();
            userIdField.clear();
            loanDateField.clear();
            returnDateField.clear();
            returnedField.clear();
        }
    } else {
        showAlert("Error", "Please select a loan to update.");
    }
}

@FXML
public void handleDeleteLoan(ActionEvent event) {
    Loan selectedLoan = loansTable.getSelectionModel().getSelectedItem();
    if (selectedLoan != null) {
        // Ask for confirmation before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this loan?");
        alert.setContentText("This action cannot be undone.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            loanDAO.deleteLoan(selectedLoan.getId());
            loanList.setAll(loanDAO.getAllLoans()); // Refresh after deletion
        }
    } else {
        showAlert("Error", "Please select a loan to delete.");
    }
}

// Method to show alert messages
private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
}


    @FXML
    public void handleSearch() {
        String searchText = searchField.getText().toLowerCase();

        // Filter the loans based on bookId, userId, loanDate, returnDate, and returned fields
        ObservableList<Loan> filteredLoans = FXCollections.observableArrayList();
        for (Loan loan : loanDAO.getAllLoans()) {
            boolean matches = false;

            // Check if the loan matches the search criteria in any field
            if (String.valueOf(loan.getBookId()).contains(searchText) ||
                String.valueOf(loan.getUserId()).contains(searchText) ||
                loan.getLoanDate().toString().contains(searchText) ||
                loan.getReturnDate().toString().contains(searchText) ||
                String.valueOf(loan.isReturned()).contains(searchText)) {
                matches = true;
            }

            // If it matches any field, add to the filtered list
            if (matches) {
                filteredLoans.add(loan);
            }
        }

        // Set the filtered list to the table
        loansTable.setItems(filteredLoans);
    }
}