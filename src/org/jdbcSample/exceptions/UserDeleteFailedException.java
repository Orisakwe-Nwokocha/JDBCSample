package org.jdbcSample.exceptions;

public class UserDeleteFailedException extends RuntimeException {
    public UserDeleteFailedException(String message) {
        super(message);
    }
}

