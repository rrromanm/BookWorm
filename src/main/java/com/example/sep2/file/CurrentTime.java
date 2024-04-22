package com.example.sep2.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {
    private static final DateTimeFormatter EUROPEAN_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy_MM_dd");

    private static CurrentTime instance;

    private CurrentTime() {
    }

    public static CurrentTime getInstance() {
        if (instance == null) {
            synchronized(CurrentTime.class) {
                if (instance == null) {
                    instance = new CurrentTime();
                }
            }
        }
        return instance;
    }

    public String getFormattedTime() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(EUROPEAN_TIME_FORMATTER);
    }

    public String getFormattedIsoDate() {
        LocalDateTime time = LocalDateTime.now();
        return time.format(ISO_DATE_FORMATTER);
    }
}
