package ru.denusariy.plumbing.exception;

public class PlumberNotFoundException extends RuntimeException {
    public PlumberNotFoundException(String message) {
        super(message);
    }
}
