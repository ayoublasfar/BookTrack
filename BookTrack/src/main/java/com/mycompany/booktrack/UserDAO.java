package com.mycompany.booktrack;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Method to get a user by ID
    public User getUserById(int id) {
        String query = "SELECT * FROM Users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Method to add a new user
    public boolean addUser(User user) {
        String query = "INSERT INTO Users (name, email, phoneNumber) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing user
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET name = ?, email = ?, phoneNumber = ? WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhoneNumber());
            statement.setInt(4, user.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if a user has active loans where returned is false
    private boolean hasActiveLoans(int userId) {
    String query = "SELECT COUNT(*) FROM Loans WHERE userId = ? AND isReturned = 0"; // 0 for 'false'
    try (Connection connection = DatabaseManager.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            System.out.println("Active loans count for user " + userId + ": " + count);
            return count > 0; // True if there are active loans
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    // Updated deleteUser method
    public boolean deleteUser(int id) throws ActiveUserException {
        if (hasActiveLoans(id)) {
            throw new ActiveUserException("Cannot delete user with active loans. Please return all books first.");
        }

        String query = "DELETE FROM Users WHERE id = ?";
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
