/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import models.Admin;
import repositories.AdminRepository;

import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author adria
 */
public class AdminService {
    private AdminRepository adminRepository;

    public AdminService() {
        this.adminRepository = new AdminRepository();
    }

    public void createUser(Admin user) throws SQLException {
        adminRepository.createUser(user);
    }

    public Admin getUserById(int id) throws SQLException {
        return adminRepository.getUserById(id);
    }

    public List<Admin> getAllUsers() throws SQLException {
        return adminRepository.getAllUsers();
    }

    public void updateUser(Admin user) throws SQLException {
        adminRepository.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException {
        adminRepository.deleteUser(id);
    }
}
