package com.cookbook.cookbook.exception;

public class ServerException extends RuntimeException {
    public ServerException(final String message) {
        super(message);
    }
}