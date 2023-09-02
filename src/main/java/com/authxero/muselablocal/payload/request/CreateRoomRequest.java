package com.authxero.muselablocal.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateRoomRequest {
    @NotNull
    @Size(min = 1, max = 30)
    private String roomName;

    @Size(min = 1, max = 64)
    private String roomPassword;

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
