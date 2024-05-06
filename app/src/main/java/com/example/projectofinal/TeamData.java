package com.example.projectofinal;

public class TeamData {
    private String teamName;
    private String logoPic;
    private String shortName;

    public TeamData(String teamName, String logoPic, String shortName) {
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
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

