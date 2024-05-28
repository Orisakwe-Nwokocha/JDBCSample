package org.jdbcSample.services;

import org.jdbcSample.exceptions.UserNotFoundException;
import org.jdbcSample.models.User;
import org.jdbcSample.models.Wallet;
import org.jdbcSample.repositories.UserRepository;

import java.math.BigDecimal;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final WalletService walletService = new WalletService();

    public String transferFunds(Long senderId, BigDecimal amount, Long recipientId) {
        User sender = getUserBy(senderId);
        User recipient = getUserBy(recipientId);

        Wallet senderWallet = walletService.getWalletBy(sender.getWalletId());
        Wallet recipientWallet = walletService.getWalletBy(recipient.getWalletId());

        senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
        recipientWallet.setBalance(recipientWallet.getBalance().add(amount));

        walletService.update(senderWallet);
        walletService.update(recipientWallet);
        return "Successfully transferred " + amount + " from " + senderId + " to " + recipientId;
    }

    public User getUserBy(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
