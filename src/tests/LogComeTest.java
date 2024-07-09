package tests;

import controllers.LogComeController;
import models.LogCome;

import java.sql.SQLException;
import java.util.List;

public class LogComeTest {

    public static void main(String[] args) throws SQLException {
        LogComeController logComeController = new LogComeController();

        // Create log entries
        System.out.println("Creating Log Entries...");
        logComeController.createLogCome(100, "user1", "Description 1", "Type A", "2024-07-03");
        logComeController.createLogCome(150, "user2", "Description 2", "Type B", "2024-07-04");

        // Retrieve and display all log entries
        System.out.println("\nRetrieving All Log Entries:");
        List<LogCome> allLogComes = logComeController.getAllLogComes();
        allLogComes.forEach(logCome -> System.out.println(logCome.toString()));

        // Get log entry by ID
        System.out.println("\nRetrieving Log Entry with ID 1:");
        LogCome logComeById = logComeController.getLogComeById(1);
        System.out.println(logComeById != null ? logComeById.toString() : "Log entry not found.");

        // Update a log entry
        System.out.println("\nUpdating Log Entry with ID 1...");
        logComeController.updateLogCome(1, 120, "user1", "Updated Description", "Type A", "2024-07-03");

        // Retrieve and display updated log entry
        System.out.println("\nRetrieving Updated Log Entry with ID 1:");
        LogCome updatedLogCome = logComeController.getLogComeById(1);
        System.out.println(updatedLogCome != null ? updatedLogCome.toString() : "Log entry not found.");

        // Delete a log entry
        System.out.println("\nDeleting Log Entry with ID 2...");
        logComeController.deleteLogCome(2);

        // Retrieve and display all log entries after deletion
        System.out.println("\nRetrieving All Log Entries after Deletion:");
        List<LogCome> remainingLogComes = logComeController.getAllLogComes();
        remainingLogComes.forEach(logCome -> System.out.println(logCome.toString()));
    }
}
