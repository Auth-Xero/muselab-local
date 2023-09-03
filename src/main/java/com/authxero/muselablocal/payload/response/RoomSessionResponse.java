package com.authxero.muselablocal.payload.response;

public class RoomSessionResponse {
    private long roomId;
    private String roomName;
    private String sessionToken;

    public RoomSessionResponse(long roomId, String roomName, String sessionToken) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.sessionToken = sessionToken;
    }

    public String getRoomName() {
        return roomName;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

}
