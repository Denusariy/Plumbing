package ru.denusariy.plumbing.exception;

public class OutOfHousesLimitException extends RuntimeException {
    public OutOfHousesLimitException(String message) {
        super(message);
    }
}
