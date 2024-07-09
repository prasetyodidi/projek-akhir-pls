/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import java.sql.SQLException;

/**
 *
 * @author adria
 */
public class MigrationTest {

    public static void main(String[] args) {
        try {
            migrations.Migrations migration = new migrations.Migrations();
            migration.migrate();
            System.err.println("Success");
        } catch (SQLException error) {
            System.err.println("Migration failed: " + error.getMessage());
        }
    }
}
