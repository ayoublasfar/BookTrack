package com.mycompany.booktrack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    // Method to get all loans
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM Loans";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                loans.add(new Loan(
                        resultSet.getInt("id"),
                        resultSet.getInt("bookId"),
                        resultSet.getInt("userId"),
                        resultSet.getDate("loanDate").toLocalDate(),
                        resultSet.getDate("returnDate").toLocalDate(),
                        resultSet.getBoolean("isReturned")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loans;
    }

    // Method to check if a user exists
    private boolean doesUserExist(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    // Method to check if a book exists
    private boolean doesBookExist(int bookId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Books WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        }
    }

    // Method to check if a book is available
    private boolean isBookAvailable(int bookId) throws SQLException {
        String query = "SELECT isAvailable FROM Books WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getBoolean("isAvailable");
        }
    }

    // Method to update book availability
    private void updateBookAvailability(int bookId, boolean isAvailable) throws SQLException {
        String query = "UPDATE Books SET isAvailable = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, isAvailable);
            statement.setInt(2, bookId);
            statement.executeUpdate();
        }
    }

    // Method to add a new loan
    public boolean addLoan(Loan loan) throws UserNotFoundException, BookNotFoundException, BookUnavailableException {
        try (Connection connection = DatabaseManager.getConnection()) {
            if (!doesUserExist(loan.getUserId())) {
                throw new UserNotFoundException("User with ID " + loan.getUserId() + " does not exist.");
            }
            if (!doesBookExist(loan.getBookId())) {
                throw new BookNotFoundException("Book with ID " + loan.getBookId() + " does not exist.");
            }
            if (!isBookAvailable(loan.getBookId())) {
                throw new BookUnavailableException("Book with ID " + loan.getBookId() + " is currently unavailable.");
            }

            String query = "INSERT INTO Loans (bookId, userId, loanDate, returnDate, isReturned) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, loan.getBookId());
                statement.setInt(2, loan.getUserId());
                statement.setDate(3, Date.valueOf(loan.getLoanDate()));
                statement.setDate(4, Date.valueOf(loan.getReturnDate()));
                statement.setBoolean(5, loan.isReturned());
                boolean added = statement.executeUpdate() > 0;

                // Mark book as unavailable
                if (added) {
                    updateBookAvailability(loan.getBookId(), false);
                }
                return added;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing loan
    public boolean updateLoan(Loan loan) {
        String query = "UPDATE Loans SET bookId = ?, userId = ?, loanDate = ?, returnDate = ?, isReturned = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, loan.getBookId());
            statement.setInt(2, loan.getUserId());
            statement.setDate(3, Date.valueOf(loan.getLoanDate()));
            statement.setDate(4, Date.valueOf(loan.getReturnDate()));
            statement.setBoolean(5, loan.isReturned());
            statement.setInt(6, loan.getId());

            int rowsUpdated = statement.executeUpdate();

            // Update book availability based on return status
            if (rowsUpdated > 0 && loan.isReturned()) {
                updateBookAvailability(loan.getBookId(), true);
            }

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Method to delete a loan
    public boolean deleteLoan(int id) {
        String query = "DELETE FROM Loans WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}