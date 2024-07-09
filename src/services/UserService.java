/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import models.User;
import repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author adria
 */
public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void createUser(User user) throws SQLException {
        userRepository.createUser(user);
    }

    public User getUserById(int id) throws SQLException {
        return userRepository.getUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        return userRepository.getAllUsers();
    }

    public void updateUser(User user) throws SQLException {
        userRepository.updateUser(user);
    }

    public void deleteUser(int id) throws SQLException {
        userRepository.deleteUser(id);
    }
}
