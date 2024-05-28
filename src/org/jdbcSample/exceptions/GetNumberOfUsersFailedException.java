package org.jdbcSample.exceptions;

public class GetNumberOfUsersFailedException extends RuntimeException {
    public GetNumberOfUsersFailedException(String message) {
        super(message);
    }
}

