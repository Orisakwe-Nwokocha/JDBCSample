package org.jdbcSample.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    public static Connection connect() {
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
