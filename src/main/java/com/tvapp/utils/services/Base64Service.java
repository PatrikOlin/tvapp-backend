package com.tvapp.utils.services;


import com.tvapp.utils.exceptions.user.InvalidUserException;

import java.util.Base64;
import java.util.Map;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class Base64Service {

    /**
     * Static method for decoding incoming data
     *
     * @param data to decode
     * @return the decoded data
     */
    public static String decodeData(String data) {
        byte[] decodedBytes;
        try {

            for (int i = 0; i < 3; i++) {
                decodedBytes = Base64.getDecoder().decode(data);
                data = new String(decodedBytes);
            }
        } catch (Exception e) {
            throw new InvalidUserException();
        }
        return data;
    }

    /**
     * Static method for decoding the login string
     * @param userKey incoming parameter
     * @return the decoded data
     */
    public static String[] decodeLogin(String userKey) {
        byte[] decodedBytes = Base64.getDecoder().decode(userKey);

        return new String(decodedBytes).split(":");
    }

    /**
     * Encode data to send
     * @param data to encode
     * @return the encoded String
     */
    public static String encodeData(String data) {
        byte[] encodedBytes = data.getBytes();
        return Base64.getEncoder().encodeToString(encodedBytes);
    }
}
