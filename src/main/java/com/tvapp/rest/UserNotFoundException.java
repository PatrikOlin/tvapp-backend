package com.tvapp.rest;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("Could not find user " + email);
    }
}
