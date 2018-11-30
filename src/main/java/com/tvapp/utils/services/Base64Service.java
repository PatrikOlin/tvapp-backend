package com.tvapp.utils.services;


import com.tvapp.utils.exceptions.user.InvalidUserException;

import java.util.Base64;

public class Base64Service {

    public static String decodePassword(String password) {
        byte[] decodedBytes;
        try {

            for (int i = 0; i < 3; i++) {
                decodedBytes = Base64.getDecoder().decode(password);
                password = new String(decodedBytes);
            }
        } catch (Exception e) {
            throw new InvalidUserException();
        }
        return password;
    }
}
