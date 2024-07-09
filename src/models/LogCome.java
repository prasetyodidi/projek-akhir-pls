package models;

import database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LogCome {
    private int id;
    private int amount;
    private String username;
    private String description;
    private String type;
    private String date;

    public LogCome(int id, int amount, String username, String description, String type, String date) {
        this.id = id;
        this.amount = amount;
        this.username = username;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public LogCome() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    // Getters and setters omitted for brevity
    public void setDate(String date) {    
        this.date = date;
    }

    // Create operation
    public static void create(int amount, String username, String description, String type, String date) throws SQLException {
        String query = "INSERT INTO log_comes (amount, username, description, type, date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, amount);
            stmt.setString(2, username);
            stmt.setString(3, description);
            stmt.setString(4, type);
            stmt.setString(5, date);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating log entry failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    // Optionally handle the generated ID if needed
                } else {
                    throw new SQLException("Creating log entry failed, no ID obtained.");
                }
            }
        }
    }

    // Read operation: Get all log entries
    public static List<LogCome> getAll() throws SQLException {
        List<LogCome> logComes = new ArrayList<>();
        String query = "SELECT * FROM log_comes";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
            while (resultSet.next()) {
                LogCome logCome = new LogCome(
                        resultSet.getInt("id"),
                        resultSet.getInt("amount"),
                        resultSet.getString("username"),
                        resultSet.getString("description"),
                        resultSet.getString("type"),
                        resultSet.getString("date"));
                logComes.add(logCome);
            }
        }
        return logComes;
    }

    // Read operation: Get log entry by ID
    public static LogCome getById(int id) throws SQLException {
        String query = "SELECT * FROM log_comes WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new LogCome(
                            resultSet.getInt("id"),
                            resultSet.getInt("amount"),
                            resultSet.getString("username"),
                            resultSet.getString("description"),
                            resultSet.getString("type"),
                            resultSet.getString("date"));
                }
            }
        }
        // If no log entry with the given ID is found, return null
        return null;
    }

    // Update operation
    public static void update(int id, int amount, String username, String description, String type, String date) throws SQLException {
        String query = "UPDATE log_comes SET amount=?, username=?, description=?, type=?, date=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, amount);
            stmt.setString(2, username);
            stmt.setString(3, description);
            stmt.setString(4, type);
            stmt.setString(5, date);
            stmt.setInt(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating log entry failed, no rows affected.");
            }
        }
    }

    // Delete operation
    public static void delete(int id) throws SQLException {
        String query = "DELETE FROM log_comes WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting log entry failed, no rows affected.");
            }
        }
    }
}
