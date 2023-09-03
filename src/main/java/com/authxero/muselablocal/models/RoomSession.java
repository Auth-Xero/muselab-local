package com.authxero.muselablocal.models;

import com.authxero.muselablocal.helpers.RandomHelper;

public class RoomSession {
    private User user;
    private Room room;
    private String sessionToken;

    public RoomSession(User user, Room room){
        this.user = user;
        this.room = room;
        this.sessionToken = RandomHelper.generateRandomString(32);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
