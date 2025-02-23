package com.mycompany.booktrack;

import java.sql.*;

public class DashboardDAO {

    // Method to get total number of books
    public int getTotalBooks() {
        String query = "SELECT COUNT(*) FROM books";
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get the number of active users
    public int getActiveUsers() {
        String query = "SELECT COUNT(DISTINCT userId) FROM loans WHERE isReturned = false";
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get the number of books currently in loan
    public int getBooksInLoan() {
        String query = "SELECT COUNT(*) FROM loans WHERE isReturned = false";
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get the number of late loans
    public int getLateLoans() {
        String query = "SELECT COUNT(*) FROM loans WHERE isReturned = false AND returnDate < CURDATE()";
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}