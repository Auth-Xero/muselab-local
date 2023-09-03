package com.authxero.muselablocal.payload.response;

import com.authxero.muselablocal.models.Room;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.ArrayList;
import java.util.List;

public class RoomListResponse {
    private List<RoomResponse> data;

    public RoomListResponse(List<Room> rooms) {
        data = new ArrayList<>();
        for (Room r : rooms) {
            data.add(new RoomResponse(r.getRoomId(), r.getRoomName(), r.isLocked()));
        }
    }

    public List<RoomResponse> getData() {
        return data;
    }

    public void setData(List<RoomResponse> data) {
        this.data = data;
    }
}
