package com.alex.courses.exseption_handling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleNoSuchHumanException(ResourceNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BindingAlreadyExistsException.class)
    public Map<String, String> handleBindingAlreadyExistsException(BindingAlreadyExistsException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessNotGrantedException.class)
    public Map<String, String> handleAccessNotGrantedException(AccessNotGrantedException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BindingNotFoundException.class)
    public Map<String, String> handleBindingNotFoundException(BindingNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", exception.getMessage());
        return errorMap;
    }

@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    // if an incorrect value is entered (for example, a string instead of a number)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public Map<String, String> handleInvalidFormatException(InvalidFormatException exception) {
        Map<String, String> errorMap = new HashMap<>();
        String field = exception.getPath().isEmpty() ? "unknown" : exception.getPath().get(0).getFieldName();
        errorMap.put(field, "Invalid value. Expected type: " + exception.getTargetType().getSimpleName());
        return errorMap;
    }

    // for delete admin with his courses
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        SQLException sqlException = exception.getSQLException();
        String errorMessage = "Before you delete an admin, you must delete all his courses";
        errorMap.put("errorExplanation", sqlException.getMessage());
        errorMap.put("errorMessage", errorMessage);

        return errorMap;
    }
}
