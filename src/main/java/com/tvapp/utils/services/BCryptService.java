package com.tvapp.utils.services;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptService {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
