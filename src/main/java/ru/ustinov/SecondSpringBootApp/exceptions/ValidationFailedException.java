package ru.ustinov.SecondSpringBootApp.exceptions;

public class ValidationFailedException extends Exception {
    public ValidationFailedException(String message) { super(message); }
}
