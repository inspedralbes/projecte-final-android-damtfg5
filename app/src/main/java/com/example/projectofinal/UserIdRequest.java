package com.example.projectofinal;

public class UserIdRequest {
    private int userId;

    public UserIdRequest(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
