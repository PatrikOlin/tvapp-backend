package com.tvapp.utils.exceptions.user;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User already exist");
    }
}
