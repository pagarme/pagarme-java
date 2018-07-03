package me.pagarme.util;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.joda.time.DateTime;

import me.pagar.security.TLSSocketConnectionFactory;

public class PagarmeCalendar {
    private static final String HOLIDAY_CALENDAR_PATH = "https://raw.githubusercontent.com/pagarme/business-calendar/master/data/brazil/";

    private static JSONArray getPagarmeOfficialHolidayCalendar(Integer year) throws Exception {
        URL holidayCalendarURL = new URL(HOLIDAY_CALENDAR_PATH + year + ".json");
        HttpsURLConnection connection = (HttpsURLConnection) holidayCalendarURL.openConnection();
        
        String version = System.getProperty("java.version");
        int sysMajorVersion = Integer.parseInt(String.valueOf(version.charAt(2)));

        if (sysMajorVersion == 6) {
            connection.setSSLSocketFactory(new TLSSocketConnectionFactory());
        }

        connection.connect();
        Scanner scanner = new Scanner(connection.getInputStream());
        String responseJSON = scanner.useDelimiter("\\Z").next();
        scanner.close();
        JSONObject calendarJSON = new JSONObject(responseJSON);
        JSONArray holidayCalendarAsJSONArray = (JSONArray) calendarJSON.getJSONArray("calendar");

        return holidayCalendarAsJSONArray;
    }

    private static String[] getHolidays(Integer year) throws Exception {
        JSONArray holidayCalendarAsJSONArray = getPagarmeOfficialHolidayCalendar(year);
        String[] holidays = new String[holidayCalendarAsJSONArray.length()];
        for (int i = 0; i < holidayCalendarAsJSONArray.length(); i++) {
            holidays[i] = holidayCalendarAsJSONArray.getJSONObject(i).get("date").toString();
        }

        return holidays;
    }

    public static DateTime getValidWeekday() throws Exception {
        DateTime weekday = new DateTime().plusDays(7);

        while ((isWeekend(weekday) == true || isHoliday(weekday) == true)){
            weekday = weekday.plusDays(2);
        }

        return weekday;
    }

    public static Boolean isWeekend(DateTime weekday) throws Exception {
        DateFormat dayName = new SimpleDateFormat("EEE", Locale.ENGLISH);
        return (dayName.format(weekday.toDate()).equals("Sun") || dayName.format(weekday.toDate()).equals("Sat"));
    }

    public static Boolean isHoliday(DateTime weekday) throws Exception {
        DateFormat yearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
        for (String holiday : getHolidays(weekday.getYear())) {
            if (yearMonthDay.format(weekday.toDate()).equals(holiday)) {
                return true;
            }
        }

        return false;
    }
}
