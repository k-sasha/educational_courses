package com.alex.courses.exseption_handling;

public class NoSuchHumanException extends RuntimeException{
    public NoSuchHumanException(String message) {
        super(message);
    }
}
