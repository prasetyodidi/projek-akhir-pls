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

    String adminMigrateQuery = "CREATE TABLE IF NOT EXISTS admin ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "nama VARCHAR(100) NOT NULL, "
            + "email VARCHAR(100) NOT NULL UNIQUE, "
            + "password VARCHAR(255) NOT NULL)";

    String anggotaMigrateQuery = "CREATE TABLE IF NOT EXISTS anggota ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "nama VARCHAR(100) NOT NULL, "
            + "alamat VARCHAR(255), "
            + "telepon VARCHAR(20), "
            + "email VARCHAR(100) NOT NULL UNIQUE, "
            + "password VARCHAR(255) NOT NULL, "
            + "tanggal_bergabung DATE NOT NULL)";

    String simpananMigrateQuery = "CREATE TABLE IF NOT EXISTS simpanan ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "id_anggota INT, "
            + "jumlah DECIMAL(15, 2) NOT NULL, "
            + "tanggal_simpan DATE NOT NULL, "
            + "FOREIGN KEY (id_anggota) REFERENCES anggota(id))";

    String pinjamanMigrateQuery = "CREATE TABLE IF NOT EXISTS pinjaman ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "
            + "id_anggota INT, "
            + "jumlah DECIMAL(15, 2) NOT NULL, "
            + "tanggal_pinjam DATE NOT NULL, "
            + "tanggal_jatuh_tempo DATE NOT NULL, "
            + "status ENUM('belum_lunas', 'lunas') DEFAULT 'belum_lunas', "
            + "FOREIGN KEY (id_anggota) REFERENCES anggota(id))";

    public void userMigration(Connection connection) throws SQLException {
        executeMigration(connection, userMigrateQuery, "User");
    }

    public void logComeMigration(Connection connection) throws SQLException {
        executeMigration(connection, logComeMigrateQuery, "Logcomes");
    }

    public void adminMigration(Connection connection) throws SQLException {
        executeMigration(connection, adminMigrateQuery, "Admin");
    }

    public void anggotaMigration(Connection connection) throws SQLException {
        executeMigration(connection, anggotaMigrateQuery, "Anggota");
    }

    public void simpananMigration(Connection connection) throws SQLException {
        executeMigration(connection, simpananMigrateQuery, "Simpanan");
    }

    public void pinjamanMigration(Connection connection) throws SQLException {
        executeMigration(connection, pinjamanMigrateQuery, "Pinjaman");
    }

    private void executeMigration(Connection connection, String query, String tableName) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            int result = statement.executeUpdate();
            System.err.println(tableName + " table migration executed with result: " + result);
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
            adminMigration(connection);
            anggotaMigration(connection);
            simpananMigration(connection);
            pinjamanMigration(connection);
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
