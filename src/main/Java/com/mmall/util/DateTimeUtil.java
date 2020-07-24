package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String str) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(str);
        DateTime dateTime = dateTimeFormatter.parseDateTime(STANDARD_FORMAT);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
}
