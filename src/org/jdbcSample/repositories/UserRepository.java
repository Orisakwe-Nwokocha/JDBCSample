package org.jdbcSample.repositories;

import org.jdbcSample.models.User;

import java.sql.*;

public class UserRepository {
    public static Connection connect() {
        /*
         * TODO: mysql-> jdbc:mysql://localhost:3306
         * TODO: postgres-> jdbc:postgresql://localhost:5432
         */
        String url = "jdbc:postgresql://localhost:5432/jdbc sample";
//        String url = "jdbc:mysql://localhost:3306/jdbc sample?createDatabaseIfNotExist=true";
//        String username = "root";
        String username = "postgres";
        String password = "password";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User save(User user) {
        String getIdSQLStatement = "SELECT count(*) FROM users";
        String sql = "insert into users (id, wallet_id) values (?, ?)";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getIdSQLStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long currentId = resultSet.getLong(1);

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, currentId + 1);
            preparedStatement.setObject(2, user.getWalletId());
            preparedStatement.execute();

            return getUserBy(currentId + 1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to connect t", e);
        }
    }

    private User getUserBy(Long id) {
        String sql = "SELECT * FROM users where id=?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long userId = resultSet.getLong(1);
            Long walletId = resultSet.getLong(1);
            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);
            return user;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed to connect t", e);
        }
    }
}
