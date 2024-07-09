package controllers;

import models.LogCome;
import services.LogComeService;

import java.sql.SQLException;
import java.util.List;

public class LogComeController {

    private final LogComeService logComeService;

    public LogComeController() {
        this.logComeService = new LogComeService();
    }

    // Create operation
    public LogCome createLogCome(int amount, String username, String description, String type, String date) throws SQLException {
        LogCome logCome = new LogCome(0, amount, username, description, type, date);
        return logComeService.createLogCome(logCome);
    }

    // Read operation: Get all log entries
    public List<LogCome> getAllLogComes() throws SQLException {
        return logComeService.getAllLogComes();
    }

    // Read operation: Get log entry by ID
    public LogCome getLogComeById(int id) throws SQLException {
        return logComeService.getLogComeById(id);
    }

    // Update operation
    public LogCome updateLogCome(int id, int amount, String username, String description, String type, String date) throws SQLException {
        LogCome logCome = new LogCome(id, amount, username, description, type, date);
        return logComeService.updateLogCome(logCome);
    }

    // Delete operation
    public void deleteLogCome(int id) throws SQLException {
        logComeService.deleteLogCome(id);
    }
}
