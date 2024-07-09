package controllers;

import models.User;
import services.UserService;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public String createUser(String name, String email) {
        try {
            userService.createUser(new User(0, name, email));
            return "User berhasil ditambahkan.";
        } catch (SQLException e) {
            return "Gagal menambahkan user: " + e.getMessage();
        }
    }

    public User getUserById(int id) throws SQLException {
        return userService.getUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userService.getAllUsers();
    }

    public String updateUser(int id, String name, String email) {
        try {
            userService.updateUser(new User(id, name, email));
            return "User berhasil diupdate.";
        } catch (SQLException e) {
            return "Gagal mengupdate user: " + e.getMessage();
        }
    }

    public String deleteUser(int id) {
        try {
            userService.deleteUser(id);
            return "User berhasil dihapus.";
        } catch (SQLException e) {
            return "Gagal menghapus user: " + e.getMessage();
        }
    }
}
