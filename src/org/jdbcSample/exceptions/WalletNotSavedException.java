package org.jdbcSample.exceptions;

public class WalletNotSavedException extends RuntimeException {
    public WalletNotSavedException(String message) {
        super(message);
    }
}

