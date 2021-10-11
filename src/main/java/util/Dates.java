package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Dates {
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Australia/Sydney");
    public static String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TIME_ZONE);
        return simpleDateFormat.format(date);
    }
    public static String format(Date date) {return format(date, "dd/MM HH:mm:ss");}
    public static String format(long timestamp) {return format(new Date(timestamp));}
    public static String currentTimeMinute() {return format(new Date());}
    public static String currentTimeMillis() {return format(new Date(), "dd/MM HH:mm:ss.SSS");}
    public static String financialYear(Date date) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Australia/Sydney"));
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month < 6) // 6 = July
            return "" + (year - 1) + "-" + year;
        else
            return "" + year + "-" + (year + 1);
    }
}
