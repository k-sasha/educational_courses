package com.alex.courses.exseption_handling;

public class BindingAlreadyExistsException extends RuntimeException {
    public BindingAlreadyExistsException(String message) {
        super(message);
    }
}

