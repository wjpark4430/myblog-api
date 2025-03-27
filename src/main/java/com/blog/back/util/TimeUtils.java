package com.blog.back.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String formatCreatedAt(ZonedDateTime createdAt) {
        ZonedDateTime seoulTime = createdAt.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return seoulTime.format(formatter);
    }
}
