package com.alex.courses.exseption_handling;

public class AccessNotGrantedException extends RuntimeException{
    public AccessNotGrantedException(String message) {
        super(message);
    }
}
