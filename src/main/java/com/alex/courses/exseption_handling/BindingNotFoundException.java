package com.alex.courses.exseption_handling;

public class BindingNotFoundException extends RuntimeException{
    public BindingNotFoundException(String message) {
        super(message);
    }
}
