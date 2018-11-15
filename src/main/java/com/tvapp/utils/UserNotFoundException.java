package com.tvapp.utils;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super("Could not find user " + email);
    }
}
