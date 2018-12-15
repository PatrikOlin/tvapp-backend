package com.tvapp.utils.services;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class DateService {
    public static String simpleDateFormatter(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
