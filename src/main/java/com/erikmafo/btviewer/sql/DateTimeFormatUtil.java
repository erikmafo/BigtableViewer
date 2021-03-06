package com.erikmafo.btviewer.sql;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateTimeFormatUtil {

    private static final String DATE = "uuuu-MM-dd";
    private static final String DATE_HOUR = "uuuu-MM-dd HH";
    private static final String DATE_HOUR_MINUTE = "uuuu-MM-dd HH:mm";
    private static final String DATE_HOUR_MINUTE_SECOND = "uuuu-MM-dd HH:mm:ss";
    private static final String DATE_HOUR_MINUTE_SECOND_MILLIS = "uuuu-MM-dd HH:mm:ss:SS";

    public static long toMicros(String dateTime) {
        return TimeUnit.MILLISECONDS.toMicros(toEpochMilli(dateTime));
    }

    private static long toEpochMilli(String dateTime) {
        String format;
        var isDate = false;
        if (dateTime.length() == DATE.length()) {
            isDate = true;
            format = DATE;
        } else if (dateTime.length() == DATE_HOUR.length()) {
            format = DATE_HOUR;
        } else if (dateTime.length() == DATE_HOUR_MINUTE.length()) {
            format = DATE_HOUR_MINUTE;
        } else if (dateTime.length() == DATE_HOUR_MINUTE_SECOND.length()) {
            format = DATE_HOUR_MINUTE_SECOND;
        }  else if (dateTime.length() == DATE_HOUR_MINUTE_SECOND_MILLIS.length()) {
            format = DATE_HOUR_MINUTE_SECOND_MILLIS;
        } else {
            throw new IllegalArgumentException(getErrorMessage(dateTime));
        }
        return toMillis(dateTime, format, isDate);
    }

    private static long toMillis(String dateTime, String format, boolean isDate) {
        dateTime = dateTime.trim();
        var offset = ZoneOffset.ofHours(0);
        var formatter = DateTimeFormatter.ofPattern(format);
        var dt = isDate ?
                LocalDateTime.of(LocalDate.parse(dateTime, formatter), LocalTime.of(0, 0, 0)) :
                LocalDateTime.parse(dateTime, formatter);
        return dt.toInstant(offset).toEpochMilli();
    }

    private static String getErrorMessage(String dateTime) {
        return String.format("Could not parse %s as date. Supported formats are: \n%s",
                dateTime, String.join("\n", supportedFormats()));
    }

    private static List<String> supportedFormats() {
        return Arrays.asList(
                DATE,
                DATE_HOUR,
                DATE_HOUR_MINUTE,
                DATE_HOUR_MINUTE_SECOND,
                DATE_HOUR_MINUTE_SECOND_MILLIS
        );
    }
}
