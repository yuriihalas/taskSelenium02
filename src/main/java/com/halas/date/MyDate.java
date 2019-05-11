package com.halas.date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    private static final Logger LOG = LogManager.getLogger(MyDate.class);
    private static final int FROM_MILLIS_TO_MINUTES = 1000 * 60;
    private Date instant;
    private SimpleDateFormat sdf;

    public MyDate() {
        instant = new Date();
        sdf = new SimpleDateFormat("HH:mm");
    }

    public String getCurrentTimeHoursMinutes() {
        instant = new Date();
        return sdf.format(instant);
    }

    private Date convertToSeconds(String time) {
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public boolean isOnePlusOrOneLessMinutes(String timeFirst, String timeSecond) {
        long timeFirstMillis = convertToSeconds(timeFirst).getTime() / (FROM_MILLIS_TO_MINUTES);
        long timeSecondMillis = convertToSeconds(timeSecond).getTime() / (FROM_MILLIS_TO_MINUTES);
        long resultDiffBetweenDates = Math.abs(timeFirstMillis - timeSecondMillis);
        LOG.debug("Difference in minutes between dates: " + resultDiffBetweenDates);
        return resultDiffBetweenDates <= 1;
    }
}
