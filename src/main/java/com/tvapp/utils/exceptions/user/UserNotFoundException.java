package com.tvapp.utils.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Could not find user");
    }
}
