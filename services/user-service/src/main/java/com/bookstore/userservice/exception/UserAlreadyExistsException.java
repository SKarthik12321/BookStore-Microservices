package com.bookstore.userservice.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) { super(message); }
}
