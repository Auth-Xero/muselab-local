package com.authxero.muselablocal.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JoinRoomRequest {
    @NotNull
    private long roomId;

    @Size(min = 1, max = 64)
    private String roomPassword;

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
