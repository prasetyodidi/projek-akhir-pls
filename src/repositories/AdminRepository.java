/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;
import models.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.Database;
/**
 *
 * @author adria
 */
public class AdminRepository {

    public void createUser(Admin user) throws SQLException {
        String query = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.executeUpdate();
        }
    }

    public Admin getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password"));
            }
        }
        return null;
    }

    public List<Admin> getAllUsers() throws SQLException {
        List<Admin> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Statement statement = Database.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(new Admin(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getString("password")));
            }
        }
        return users;
    }

    public void updateUser(Admin user) throws SQLException {
        String query = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setInt(3, user.getId());
            statement.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}