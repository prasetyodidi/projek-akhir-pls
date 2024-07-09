package controllers;

import models.Admin;
import services.AdminService;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private AdminService adminService;

    public AdminController() {
        this.adminService = new AdminService();
    }

    public String createUser(String name, String email, String password) {
        try {
            adminService.createUser(new Admin(0, name, email, password));
            return "User berhasil ditambahkan.";
        } catch (SQLException e) {
            return "Gagal menambahkan user: " + e.getMessage();
        }
    }

    public Admin getUserById(int id) throws SQLException {
        return adminService.getUserById(id);
    }

    public List<Admin> getAllUsers() throws SQLException {
        return adminService.getAllUsers();
    }

    public String updateUser(int id, String name, String email, String password) {
        try {
            adminService.updateUser(new Admin(id, name, email, password));
            return "User berhasil diupdate.";
        } catch (SQLException e) {
            return "Gagal mengupdate user: " + e.getMessage();
        }
    }

    public String deleteUser(int id) {
        try {
            adminService.deleteUser(id);
            return "User berhasil dihapus.";
        } catch (SQLException e) {
            return "Gagal menghapus user: " + e.getMessage();
        }
    }
}
