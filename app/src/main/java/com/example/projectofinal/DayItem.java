package com.example.projectofinal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import java.util.Calendar;
import java.util.Locale;

public class DayItem {
    private String dayOfWeek;
    private String dayOfMonth;
    private String month;

    public DayItem(String dayOfWeek, String dayOfMonth, String month) {
        this.dayOfWeek = capitalizeFirstLetter(dayOfWeek.substring(0, 3));
        this.dayOfMonth = dayOfMonth;
        this.month = capitalizeFirstLetter(month.substring(0, 3));
    }

    private String capitalizeFirstLetter(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int monthNumber = convertMonthToNumber(month);
        return String.format(Locale.getDefault(), "%04d/%02d/%02d", year, monthNumber, Integer.parseInt(dayOfMonth));
    }

    private int convertMonthToNumber(String month) {
        switch (month.toLowerCase()) {
            case "jan":
                return 1;
            case "feb":
                return 2;
            case "mar":
                return 3;
            case "apr":
                return 4;
            case "may":
                return 5;
            case "jun":
                return 6;
            case "jul":
                return 7;
            case "aug":
                return 8;
            case "sep":
                return 9;
            case "oct":
                return 10;
            case "nov":
                return 11;
            case "dec":
                return 12;
            default:
                return 0;
        }
    }
}


class DayGenerator {

    public static List<DayItem> generateDays() {
        List<DayItem> dayList = new ArrayList<>();

        // Establece el locale en español
        Locale locale = new Locale("es", "ES");
        Calendar calendar = Calendar.getInstance(locale);
        int currentMonth = calendar.get(Calendar.MONTH);
        int lastDayOfMonth;

        while (calendar.get(Calendar.MONTH) <= Calendar.AUGUST) {
            String dayOfWeek = new SimpleDateFormat("EEEE", locale).format(calendar.getTime());
            String dayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String month = new SimpleDateFormat("MMMM", locale).format(calendar.getTime());

            dayList.add(new DayItem(dayOfWeek, dayOfMonth, month));

            lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (calendar.get(Calendar.DAY_OF_MONTH) == lastDayOfMonth && calendar.get(Calendar.MONTH) != Calendar.AUGUST) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, 1);
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        return dayList;
    }

    public static void main(String[] args) {
        List<DayItem> days = generateDays();
        for (DayItem day : days) {
            System.out.println(day.getDayOfWeek() + "\n" + day.getDayOfMonth() + "\n" + day.getMonth() + "\n");
        }
    }
}
