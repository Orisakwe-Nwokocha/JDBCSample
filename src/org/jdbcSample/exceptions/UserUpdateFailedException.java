package org.jdbcSample.exceptions;

public class UserUpdateFailedException extends JdbcSampleAppException {
    public UserUpdateFailedException(String message) {
        super(message);
    }
}

