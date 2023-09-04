package com.authxero.muselablocal.models;

import com.authxero.muselablocal.helpers.RandomHelper;

public class RoomSession {
    private long userId;
    private long roomId;
    private String sessionToken;

    public RoomSession(long userId, long roomId){
        this.userId = userId;
        this.roomId = roomId;
        this.sessionToken = RandomHelper.generateRandomString(32);
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
