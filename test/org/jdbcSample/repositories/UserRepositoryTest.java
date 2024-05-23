package org.jdbcSample.repositories;

import org.jdbcSample.models.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @Test
    public void testDatabaseConnection() {
        try (Connection connection = UserRepository.connect()) {
            assertNotNull(connection);
            System.out.println("Database connection established-> " + connection);

        } catch (SQLException e) {
            e.printStackTrace();
            assertNull(e);
        }
    }

    @Test
    public void saveUserTest() {
        User user = new User();
//        user.setWalletId(1L);
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.getWalletId());
        System.out.println(savedUser.getId());
        assertNotNull(savedUser);
    }

}