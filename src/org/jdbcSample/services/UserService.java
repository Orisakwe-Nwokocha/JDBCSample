package org.jdbcSample.services;

import org.jdbcSample.exceptions.JdbcSampleAppException;
import org.jdbcSample.exceptions.UserNotFoundException;
import org.jdbcSample.models.User;
import org.jdbcSample.models.Wallet;
import org.jdbcSample.repositories.UserRepository;
import org.jdbcSample.utils.db.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static org.jdbcSample.utils.db.DatabaseConnectionManager.getInstance;

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final WalletService walletService = new WalletService();

    public String transferFunds(Long senderId, BigDecimal amount, Long recipientId) {
        try(Connection connection = getInstance().getConnection()) {
            connection.setAutoCommit(false);

            User sender = getUserBy(senderId);
            User recipient = getUserBy(recipientId);
            Wallet senderWallet = walletService.getWalletBy(sender.getWalletId());
            Wallet recipientWallet = walletService.getWalletBy(recipient.getWalletId());

            senderWallet.setBalance(senderWallet.getBalance().subtract(amount));
            recipientWallet.setBalance(recipientWallet.getBalance().add(amount));

            walletService.update(senderWallet, connection);
//            if (true) throw new JdbcSampleAppException("test");
            walletService.update(recipientWallet, connection);
            connection.commit();
            return "Successfully transferred " + amount + " from " + senderId + " to " + recipientId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserBy(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
