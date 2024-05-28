package org.jdbcSample.services;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private final UserService userService = new UserService();
    private final WalletService walletService = new WalletService();

    @Test
    public void testTransferFunds() {
        Long senderId = 2L;
        Long receiverId = 4L;
        BigDecimal transferAmount = new BigDecimal("2000.00");
        String response = userService.transferFunds(senderId, transferAmount, receiverId);
        assertNotNull(response);
    }
}