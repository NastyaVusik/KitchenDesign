package com.example.kitchendesign.util;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Data
public class DateAndTimeFormatter {

//    private LocalDateTime localDateTime;

    public static String dateAndTimeParser(LocalDateTime localDateTime) {

    DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return localDateTime.format(dateTimeFormatter);
}
}
