package com.example.projectofinal;

public class InviteBody {
    private int teamId;
    private int userId;

    public InviteBody(int teamId, int userId) {
        this.teamId = teamId;
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

