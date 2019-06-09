package cc.lastone.hiblog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String date() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    public static String date(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    public static String date(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String date(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date strtotime(String format, String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
