package com.mycompany.booktrack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class ManageBooksController {

    @FXML private TableView<Book> booksTable;
    @FXML private TableColumn<Book, Integer> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> genreColumn;
    @FXML private TableColumn<Book, Boolean> availabilityColumn;

    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField genreField;
    @FXML private TextField availabilityField;
    @FXML private TextField searchField;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private BookDAO bookDAO = new BookDAO();

    @FXML
    public void initialize() {
        // Setup columns in the table
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().getAuthorProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
        availabilityColumn.setCellValueFactory(cellData -> cellData.getValue().getAvailableProperty());
        
        
        // Add a listener to populate fields when a user is selected
        booksTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null) {
        titleField.setText(newValue.getTitle());
        authorField.setText(newValue.getAuthor());
        genreField.setText(newValue.getGenre());
        availabilityField.setText(String.valueOf(newValue.isAvailable()));
    }
});


        // Load books from the database
        bookList.setAll(bookDAO.getAllBooks());
        
        // Set the table's data source
        booksTable.setItems(bookList);
    }

    @FXML
    public void handleAddBook(ActionEvent event) {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();
        boolean available = Boolean.parseBoolean(availabilityField.getText());

        // Create new book and save to the database
        Book newBook = new Book(0, title, author, genre, available);
        bookDAO.addBook(newBook);
        
        // Refresh the table with updated list from database
        bookList.setAll(bookDAO.getAllBooks());

        // Clear input fields
        titleField.clear();
        authorField.clear();
        genreField.clear();
        availabilityField.clear();
    }

    @FXML
public void handleUpdateBook(ActionEvent event) {
    Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
    if (selectedBook != null) {
        // Ask for confirmation before updating
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Update");
        alert.setHeaderText("Are you sure you want to update this book?");
        alert.setContentText("Changes will be saved to the database.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            selectedBook.setTitle(titleField.getText());
            selectedBook.setAuthor(authorField.getText());
            selectedBook.setGenre(genreField.getText());
            selectedBook.setAvailable(Boolean.parseBoolean(availabilityField.getText()));

            // Update book in the database
            bookDAO.updateBook(selectedBook);

            // Refresh the table with updated list from database
            booksTable.refresh();

            // Clear input fields
            titleField.clear();
            authorField.clear();
            genreField.clear();
            availabilityField.clear();
        }
    } else {
        showAlert("Error", "Please select a book to update.");
    }
}

@FXML
public void handleDeleteBook(ActionEvent event) {
    Book selectedBook = booksTable.getSelectionModel().getSelectedItem();
    if (selectedBook != null) {
        // Ask for confirmation before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this book?");
        alert.setContentText("This action cannot be undone.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            bookDAO.deleteBook(selectedBook.getId());
            bookList.setAll(bookDAO.getAllBooks()); // Refresh after deletion
        }
    } else {
        showAlert("Error", "Please select a book to delete.");
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
    public void handleSearch(KeyEvent event) {
        String searchText = searchField.getText().toLowerCase();

        // Filter the books based on title, author, genre, and availability
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        for (Book book : bookDAO.getAllBooks()) {
            boolean matches = false;
            
            // Check if the book matches the search criteria in any field
            if (book.getTitle().toLowerCase().contains(searchText) ||
                book.getAuthor().toLowerCase().contains(searchText) ||
                book.getGenre().toLowerCase().contains(searchText) ||
                String.valueOf(book.isAvailable()).toLowerCase().contains(searchText)) {
                matches = true;
            }

            // If it matches any field, add to the filtered list
            if (matches) {
                filteredBooks.add(book);
            }
        }

        // Set the filtered list to the table
        booksTable.setItems(filteredBooks);
    }
}
