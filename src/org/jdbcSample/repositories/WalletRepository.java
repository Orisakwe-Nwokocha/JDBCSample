package org.jdbcSample.repositories;

import org.jdbcSample.exceptions.UserUpdateFailedException;
import org.jdbcSample.exceptions.WalletNotSavedException;
import org.jdbcSample.models.Wallet;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.jdbcSample.utils.db.DatabaseConnectionManager.getInstance;

@SuppressWarnings({"all"})
public class WalletRepository {

    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallets (id, balance) VALUES (?, ?)";
        try (Connection connection = getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement = connection.prepareStatement(sql);
            Long id = generateId();
            preparedStatement.setLong(1, id);
            preparedStatement.setBigDecimal(2, wallet.getBalance());
            preparedStatement.execute();

            return getWalletBy(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new WalletNotSavedException("Failed to save wallet: " + e);
        }
    }

    private Long generateId() {
        try(Connection connection = getInstance().getConnection()) {
            String sql = "select max(id) from wallets";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1) + 1;
        } catch (SQLException e) {
            throw new WalletNotSavedException("Failed to save wallet: " + e);
        }
    }

    private Wallet getWalletBy(Long id) {
        String sql = "SELECT * FROM wallets where id=?";
        try (Connection connection = getInstance().getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long walletId = resultSet.getLong(1);
            BigDecimal balance = resultSet.getBigDecimal(2);

            Wallet wallet = new Wallet();
            wallet.setId(walletId);
            wallet.setBalance(balance);
            return wallet;
        } catch (SQLException e) {
            return null;
        }
    }

    public Optional<Wallet> findById(Long id) {
        Wallet wallet = getWalletBy(id);
        if (wallet != null) return Optional.of(wallet);
        return Optional.empty();
    }

    public Wallet update(Long id, BigDecimal balance, Connection connection) {
        try {
            String sql = "UPDATE wallets SET balance=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, balance);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return getWalletBy(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new UserUpdateFailedException("Failed to update wallet: " + e);
        }
    }
}
