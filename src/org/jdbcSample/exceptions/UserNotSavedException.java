package org.jdbcSample.exceptions;

public class UserNotSavedException extends JdbcSampleAppException {
    public UserNotSavedException(String message) {
        super(message);
    }
}

