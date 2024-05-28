package org.jdbcSample.exceptions;

public class WalletNotFoundException extends JdbcSampleAppException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}

