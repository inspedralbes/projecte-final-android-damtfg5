package com.example.projectofinal;

public class TeamData {
    private int id;
    private String teamName;
    private String logoPic;
    private String shortName;
    private int idRequest;

    public TeamData(String teamName, String logoPic, String shortName) {
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
    }

    public TeamData(int id, String teamName, String logoPic, String shortName, int idRequest) {
        this.id = id;
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
        this.idRequest = idRequest;
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

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }
}

