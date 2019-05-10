package com.halas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    public String getCurrentTimeHoursMinutes() {
        Date instant = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(instant);
    }
}
