package ru.ustinov.SecondSpringBootApp.exceptions;

public class UnsupportedCodeException extends RuntimeException {
    public UnsupportedCodeException(String message) {
        super(message);
    }
}
