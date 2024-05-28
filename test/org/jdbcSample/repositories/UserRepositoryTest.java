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
        User savedUser = userRepository.save(user);
        System.out.println(savedUser.getWalletId());
        System.out.println(savedUser.getId());
        assertNotNull(savedUser);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 6L;
        Long walletId = 600L;
        User user = userRepository.getUserBy(userId);
        User updatedUser = userRepository.updateUser(userId, walletId);

        assertNotNull(user);
        assertNotNull(updatedUser);

        assertEquals(0L, user.getWalletId());
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(walletId, updatedUser.getWalletId());
    }

    @Test
    public void testFindUser() {
        Long userId = 4L;
        User user = userRepository.getUserBy(userId);

        assertNotNull(user);
        assertEquals(0L, user.getWalletId());
    }

}