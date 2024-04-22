package com.cookbook.cookbook.exceptions;

public class ServerException extends RuntimeException {
    public ServerException(final String message) {
        super(message);
    }
}