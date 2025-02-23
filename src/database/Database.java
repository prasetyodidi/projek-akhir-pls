package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;

public class Database {
    private static java.sql.Connection connection;

    public static java.sql.Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://localhost:3306/projectakhir?zeroDateTimeBehavior=CONVERT_TO_NULL");
            dataSource.setUser("root");
            dataSource.setPassword("password");

            connection = dataSource.getConnection();
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
