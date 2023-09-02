package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class RoomHelper {
    private final static List<Room> ROOM_LIST = new ArrayList<>();

    public static Room createRoom(String name, String password) {
        Room r = new Room(name, password);
        ROOM_LIST.add(r);
        try {
            writeRoomToDisk(r);
        }catch (Exception ignored){}
        return r;
    }

    public static Room createRoom(String name) {
        Room r = new Room(name);
        ROOM_LIST.add(r);
        try {
            writeRoomToDisk(r);
        }catch (Exception ignored){}
        return r;
    }

    public static Room getRoomByIndex(int i) {
        return ROOM_LIST.get(i);
    }

    public static Room getRoomById(long id) {
        return ROOM_LIST.stream().filter(room -> id == room.getRoomId()).findFirst().orElse(null);
    }

    public static List<Room> getRoomList() {
        return ROOM_LIST;
    }

    private static void writeRoomToDisk(Room r) throws IOException {
        String path = Long.toString(r.getRoomId());
        FileHelper.createFolder(path);
        String json = JsonHelper.toJson(r);
        FileHelper.writeFile(path+"/room.json",json);
    }
}
