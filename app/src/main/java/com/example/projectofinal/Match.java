package com.example.projectofinal;

public class Match {
    private int matchId;
    private String matchDate;
    private String matchTime;
    private String matchLocation;
    private String status;
    private Team team1;
    private Team team2;

    // Constructor
    public Match(int matchId, String matchDate, String matchTime, String matchLocation, String status, Team team1, Team team2) {
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.matchLocation = matchLocation;
        this.status = status;
        this.team1 = team1;
        this.team2 = team2;
    }

    // Getters and Setters
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchLocation() {
        return matchLocation;
    }

    public void setMatchLocation(String matchLocation) {
        this.matchLocation = matchLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}

