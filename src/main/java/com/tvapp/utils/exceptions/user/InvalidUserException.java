package com.tvapp.utils.exceptions.user;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Invalid email or password");
    }
}
