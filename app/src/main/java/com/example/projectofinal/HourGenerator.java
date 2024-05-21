package com.example.projectofinal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HourGenerator {

    public static List<String> generateHoursFromNowUntil22() {
        List<String> hoursList = new ArrayList<>();

        // Obtener la hora actual del sistema
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        if (currentMinute != 0 && currentMinute != 30) {
            if (currentMinute < 30) {
                currentMinute = 30;
            } else {
                currentHour++;
                currentMinute = 0;
            }
        }

        // Generar las horas desde la hora ajustada actual hasta las 22:00
        for (int hour = currentHour; hour < 22; hour++) {
            for (int minute = (hour == currentHour) ? currentMinute : 0; minute < 60; minute += 30) {
                String formattedHour = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                hoursList.add(formattedHour);
            }
        }

        // Agregar 22:00 como último valor
        hoursList.add("22:00");

        return hoursList;
    }
}


