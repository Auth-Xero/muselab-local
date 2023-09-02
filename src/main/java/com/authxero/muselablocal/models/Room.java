package com.authxero.muselablocal.models;


import com.authxero.muselablocal.helpers.JsonHelper;
import com.authxero.muselablocal.helpers.RandomHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Room {
    private long roomId;

    private String roomName;

    private boolean locked;

    public Room(String roomName) {
        this.roomName = roomName;
        this.locked = false;
        this.roomId = RandomHelper.generateRandomLong(0, 1000000000);
    }

    public Room(String roomName, String roomPassword) {
        this.roomName = roomName;
        this.locked = true;
        this.roomId = RandomHelper.generateRandomLong(0, 1000000000);
        this.roomPassword = roomPassword;
    }

    @JsonIgnore
    private List<User> participants;

    private String roomPassword;

    public List<User> getParticipants() {
        return participants;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
