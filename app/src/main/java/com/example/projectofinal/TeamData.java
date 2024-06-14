package com.example.projectofinal;

public class TeamData {
    private int id;
    private int idUser;
    private String teamName;
    private String logoPic;
    private String shortName;
    private int idRequest;

    public TeamData(int idUser, String teamName, String logoPic, String shortName) {
        this.idUser = idUser;
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
    }

    public TeamData(int id, int idUser, String teamName, String logoPic, String shortName, int idRequest) {
        this.id = id;
        this.idUser = idUser;
        this.teamName = teamName;
        this.logoPic = logoPic;
        this.shortName = shortName;
        this.idRequest = idRequest;
    }

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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public int getIdRequestTeam() {
        return idRequest;
    }

    public void setIdRequestTeam(int idRequest) {
        this.idRequest = idRequest;
    }
}

