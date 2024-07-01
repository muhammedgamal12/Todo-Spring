package com.example.user.exception.user;

public class EmailAlreadyExistException extends Exception{
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
