package org.jdbcSample.services;

import org.jdbcSample.exceptions.WalletNotFoundException;
import org.jdbcSample.models.Wallet;
import org.jdbcSample.repositories.WalletRepository;

import java.sql.Connection;

public class WalletService {
    private final WalletRepository walletRepository = new WalletRepository();

    public Wallet getWalletBy(Long id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }

    public Wallet update(Wallet wallet, Connection connection) {
        return walletRepository.update(wallet.getId(), wallet.getBalance(), connection);
    }

}
