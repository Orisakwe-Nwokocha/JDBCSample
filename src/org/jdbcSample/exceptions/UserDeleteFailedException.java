package org.jdbcSample.exceptions;

public class UserDeleteFailedException extends JdbcSampleAppException {
    public UserDeleteFailedException(String message) {
        super(message);
    }
}

