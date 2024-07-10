package tests;

import controllers.AdminController;
import java.sql.SQLException;

public class UserTest {
    public static void main(String[] args) throws SQLException {
        AdminController userController = new AdminController();

        // Create users
        System.out.println(userController.createUser("John Doe", "john@example.com", "Admin#123"));
        System.out.println(userController.createUser("Jane Smith", "jane@example.com", "Admin#123"));

        // Retrieve and display users
        System.out.println("Daftar Pengguna:");
        userController.getAllUsers();

        // Update a user
        System.out.println(userController.updateUser(1, "John Updated", "johnupdated@example.com", "Admin#123"));

        // Retrieve and display updated user
        System.out.println("Pengguna dengan ID 1 setelah diupdate:");
        System.out.println(userController.getUserById(1));

        // Delete a user
        System.out.println(userController.deleteUser(2));

        // Retrieve and display all users after deletion
        System.out.println("Daftar Pengguna setelah penghapusan:");
        userController.getAllUsers();
    }
}
