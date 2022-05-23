package com.example.emailManagementApp.exceptions;

public class UserDoesNotExistException extends EmailManagementAppException {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
