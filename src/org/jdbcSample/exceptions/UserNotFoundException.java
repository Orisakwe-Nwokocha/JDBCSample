package org.jdbcSample.exceptions;

public class UserNotFoundException extends JdbcSampleAppException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

