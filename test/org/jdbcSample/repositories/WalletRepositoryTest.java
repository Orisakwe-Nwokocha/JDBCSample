package org.jdbcSample.repositories;

import org.jdbcSample.models.Wallet;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WalletRepositoryTest {
    private final WalletRepository walletRepository = new WalletRepository();

    @Test
    public void saveWalletTest() {
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("1000.50"));
        Wallet savedWallet = walletRepository.save(wallet);
        System.out.println(savedWallet);
        assertNotNull(savedWallet);
    }

    @Test
    public void findWalletTest() {
        Optional<Wallet> wallet = walletRepository.findById(6L);
        assertTrue(wallet.isPresent());
        assertEquals(wallet.get().getBalance(), new BigDecimal("1000.5"));
    }
}