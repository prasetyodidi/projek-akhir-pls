/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package migrations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Database;

/**
 *
 * @author adria
 */
public class Migrations {

    String userMigrateQuery = "CREATE TABLE IF NOT EXISTS users ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "name VARCHAR(100) NOT NULL, "
            + "email VARCHAR(100) NOT NULL UNIQUE)";
    String logComeMigrateQuery = "CREATE TABLE IF NOT EXISTS log_comes ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "amount INTEGER NOT NULL, "
            + "username VARCHAR(100) NOT NULL, "
            + "description VARCHAR(200), "
            + "type VARCHAR(100) NOT NULL, "
            + "date VARCHAR(20) NOT NULL)";

    public void userMigration(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(userMigrateQuery);
            int result = statement.executeUpdate();
            System.err.println("User table migration executed with result: " + result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void logComeMigration(Connection connection) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(logComeMigrateQuery);
            int result = statement.executeUpdate();
            System.err.println("Logcomes table migration executed with result: " + result);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void migrate() throws SQLException {
        Connection connection = Database.getConnection();
        try {
            connection.setAutoCommit(false);
            userMigration(connection);
            logComeMigration(connection);
            connection.commit();
        } catch (SQLException exception) {
            connection.rollback();
            System.err.println("Migration failed: " + exception.getMessage());
            throw new SQLException(exception.getMessage());
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
}
