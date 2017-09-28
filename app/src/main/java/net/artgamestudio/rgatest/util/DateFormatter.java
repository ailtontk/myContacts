package net.artgamestudio.rgatest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateFormatter {

    public final static String DATE_DEFAULT = "dd/MM/yyyy";

    /**
     * Formats a calendar based on a specific format.
     * @param format A specific format to convert. Ex. dd/MM/aaaa
     * @param calendar A setted calendar to format
     */
    public static String format(String format, Calendar calendar) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * Formats a calendar based on a specific format.
     * @param format A specific format to convert. Ex. dd/MM/aaaa
     * @param calendar A setted calendar to format
     */
    public static String format(String format, Calendar calendar, TimeZone timeZone) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * Convert a String date into a calendar
     * @param dateFormat A specific format to convert. Ex. dd/MM/aaaa
     * @param date The date to convert
     * @return A calendar converted with the String date, or a calendar with the current date
     * if something went wrong.
     */
    public static Calendar dateToCalendar(String dateFormat, String date) {
        Calendar calendar = Calendar.getInstance();

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            calendar.setTime(simpleDateFormat.parse(date));
        } catch (Exception error) {
            calendar = Calendar.getInstance();
        }

        return calendar;
    }
}
