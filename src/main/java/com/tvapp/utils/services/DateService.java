package com.tvapp.utils.services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {
    public static String simpleDateFormatter(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
