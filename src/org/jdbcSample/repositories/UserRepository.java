package org.jdbcSample.repositories;

import org.jdbcSample.exceptions.GetNumberOfUsersFailedException;
import org.jdbcSample.exceptions.UserDeleteFailedException;
import org.jdbcSample.exceptions.UserNotSavedException;
import org.jdbcSample.exceptions.UserUpdateFailedException;
import org.jdbcSample.models.User;

import java.sql.*;
import java.util.Optional;

@SuppressWarnings({"all"})
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
        String sql = "insert into users (id, wallet_id) values (?, ?)";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
            Long id = generateId();
            preparedStatement.setLong(1, id);
            preparedStatement.setObject(2, user.getWalletId());
            preparedStatement.execute();

            return getUserBy(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new UserNotSavedException("Failed to save user: " + e);
        }
    }

    private Long generateId() {
        try(Connection connection = connect()) {
            String sql = "select max(id) from users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1) + 1;
        } catch (SQLException e) {
            throw new UserNotSavedException("Failed to save user: " + e);
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
            Long walletId = resultSet.getLong(2);

            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public Optional<User> findById(Long id) {
        User user = getUserBy(id);
        if (user != null) return Optional.of(user);
        return Optional.empty();
    }

    public User updateUser(Long id, Long walletId) {
        try (Connection connection = connect()) {
            String sql = "UPDATE users SET wallet_id=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, walletId);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return getUserBy(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new UserUpdateFailedException("Failed to update user: " + e);
        }
    }

    public long getNumberOfUsers() {
        String sql = "SELECT count(*) FROM users";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new GetNumberOfUsersFailedException("Failed to count users: " + e);
        }
    }

    public void deleteUser(Long id) {
        try (Connection connection = connect()) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new UserDeleteFailedException("Failed to delete user: " + e);
        }
    }
}
