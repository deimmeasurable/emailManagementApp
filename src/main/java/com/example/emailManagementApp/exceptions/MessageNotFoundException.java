package com.example.emailManagementApp.exceptions;

public class MessageNotFoundException extends UserDoesNotExistException {
    public MessageNotFoundException(String message) {
        super(message);
    }
}
