package com.authxero.muselablocal.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Room {
    private String roomId;

    @JsonIgnore
    private List<User> participants;

    @JsonIgnore
    private String roomPassword;

    public List<User> getParticipants() {
        return participants;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }
}
