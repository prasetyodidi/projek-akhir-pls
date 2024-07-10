package repositories;

import models.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.Database;

public class AdminRepository {

    public void createUser(Admin user) throws SQLException {
        String query = "INSERT INTO admin (nama, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        }
    }

    public Admin getUserById(int id) throws SQLException {
        String query = "SELECT * FROM admin WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(resultSet.getInt("id"), resultSet.getString("nama"), resultSet.getString("email"), resultSet.getString("password"));
            }
        }
        return null;
    }

    public List<Admin> getAllUsers() throws SQLException {
        List<Admin> users = new ArrayList<>();
        String query = "SELECT * FROM admin";
        try (Statement statement = Database.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                users.add(new Admin(resultSet.getInt("id"), resultSet.getString("nama"), resultSet.getString("email"), resultSet.getString("password")));
            }
        }
        return users;
    }

    public void updateUser(Admin user) throws SQLException {
        String query = "UPDATE admin SET nama = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM admin WHERE id = ?";
        try (PreparedStatement statement = Database.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
