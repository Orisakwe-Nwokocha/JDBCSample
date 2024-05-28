package org.jdbcSample.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private Connection connection;

    private DatabaseConnectionManager() {}

    private static final class SINGLETON {
        private static final DatabaseConnectionManager INSTANCE = new DatabaseConnectionManager();
    }

    public static DatabaseConnectionManager getInstance() {
        return SINGLETON.INSTANCE;
    }

    public Connection getConnection() {
        if (connection != null) return connection;
        /*
         * TODO: mysql-> jdbc:mysql://localhost:3306
         * TODO: postgres-> jdbc:postgresql://localhost:5432
         *
         * String url = "jdbc:mysql://localhost:3306/jdbc sample?createDatabaseIfNotExist=true";
         * String username = "root";
         */

        String url = "jdbc:postgresql://localhost:5432/jdbc sample";
        String username = "postgres";
        String password = "password";

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
