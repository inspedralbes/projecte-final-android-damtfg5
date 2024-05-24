package com.example.projectofinal;

public class DayLocationRequest {
    private String day;
    private String matchLocation;

    public DayLocationRequest(String day, String matchLocation) {
        this.day = day;
        this.matchLocation = matchLocation;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

}

