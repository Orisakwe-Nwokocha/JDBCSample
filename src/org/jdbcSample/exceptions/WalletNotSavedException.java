package org.jdbcSample.exceptions;

public class WalletNotSavedException extends JdbcSampleAppException {
    public WalletNotSavedException(String message) {
        super(message);
    }
}

