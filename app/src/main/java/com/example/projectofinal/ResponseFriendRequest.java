package com.example.projectofinal;

public class ResponseFriendRequest {
    int requestId;
    String status;

    public ResponseFriendRequest(int requestId, String status) {
        this.requestId = requestId;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
