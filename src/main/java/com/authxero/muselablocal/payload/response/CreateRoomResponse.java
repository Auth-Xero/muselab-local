package com.authxero.muselablocal.payload.response;

public class CreateRoomResponse {
    private long roomId;
    private String roomName;
    private boolean locked;

    public CreateRoomResponse(long roomId, String roomName, boolean locked){
        this.roomId = roomId;
        this.roomName = roomName;
        this.locked = locked;
    }

    public String getRoomName() {
        return roomName;
    }

    public long getRoomId() {
        return roomId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }
}
