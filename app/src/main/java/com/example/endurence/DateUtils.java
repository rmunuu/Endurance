package com.example.endurence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dateString, formatter);
    }
}
