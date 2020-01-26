package com.starling.challenge.exception;

public class MissingAccountException extends RuntimeException {
    public MissingAccountException(String message) {
        super(message);
    }
}
