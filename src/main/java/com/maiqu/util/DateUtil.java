package com.maiqu.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT2 = "yyyy-MM-dd";

    public static DateTimeFormatter df = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static DateTimeFormatter df2 = DateTimeFormatter.ofPattern(TIME_FORMAT2);

    public static LocalDateTime parseLocalDateTime(String dateTime){
        return LocalDateTime.parse(dateTime,df);
    }

    public static LocalDate parseLocalDate(String date){
        return LocalDate.parse(date,df2);
    }

    public static String getLocalDateTimeStr(LocalDateTime dateTime){
        return df.format(dateTime);
    }

    public static String getLocalDateStr(LocalDateTime date){
        return df2.format(date);
    }

    public static void main(String[] args) {
        LocalDate s = parseLocalDate("2020-09-10");
        System.out.print(s);
        LocalDateTime s2 = parseLocalDateTime("2020-09-10 00:00:00");
        System.out.print(s2);
    }
}
