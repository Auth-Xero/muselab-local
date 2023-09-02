package com.authxero.muselablocal.payload.response;

import com.authxero.muselablocal.models.Room;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.ArrayList;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class RoomListResponse {
    private List<RoomResponse> array;

    public RoomListResponse(List<Room> rooms) {
        array = new ArrayList<>();
        for (Room r : rooms) {
            array.add(new RoomResponse(r.getRoomId(), r.getRoomName(), r.isLocked()));
        }
    }

    public List<RoomResponse> getArray() {
        return array;
    }

    public void setArray(List<RoomResponse> array) {
        this.array = array;
    }
}
