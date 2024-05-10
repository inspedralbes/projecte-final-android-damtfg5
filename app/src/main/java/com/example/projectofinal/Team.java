package com.example.projectofinal;

public class Team {
    private int id;
    private String teamName;
    private String logoPic;
    private String shortName;

    public Team(int id, String teamName, String logoPic, String shortName) {
        this.id = id;
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
