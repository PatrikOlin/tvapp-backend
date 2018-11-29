package com.tvapp.utils.exceptions.user;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Invalid email or password");
    }
}
