package com.bravi.agenda.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String AMERICA_ARGENTINA_BUENOS_AIRES = "America/Argentina/Buenos_Aires";

    private DateUtils() {
    }

    public static String dateTimeNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        LocalDateTime now = LocalDateTime.now(ZoneId.of(AMERICA_ARGENTINA_BUENOS_AIRES));
        return now.format(formatter);
    }

}
