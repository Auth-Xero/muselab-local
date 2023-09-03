package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomHelper {
    private final static List<Room> ROOM_LIST = new ArrayList<>();

    private final static Map<String, RoomSession> ROOM_MAP = new HashMap<>();

    @Autowired
    public RoomHelper() throws IOException {
        List<String> folders = FileHelper.listFoldersInDataFolder();
        for (String f : folders) {
            try {
                String roomData = FileHelper.readFile(f + "/room.json");
                Room r = JsonHelper.fromJson(roomData, Room.class);
                ROOM_LIST.add(r);
            }
            catch (Exception ignored){}
        }
        System.out.println(ROOM_LIST.size());
    }

    public static Room createRoom(String name, String password) {
        Room r = new Room(name, password);
        ROOM_LIST.add(r);
        try {
            writeRoomToDisk(r);
        } catch (Exception ignored) {
        }
        return r;
    }

    public static Room createRoom(String name) {
        Room r = new Room(name);
        ROOM_LIST.add(r);
        try {
            writeRoomToDisk(r);
        } catch (Exception ignored) {
        }
        return r;
    }

    public static Room getRoomByIndex(int i) {
        return ROOM_LIST.get(i);
    }

    public static Room getRoomById(long id) {
        return ROOM_LIST.stream().filter(room -> id == room.getRoomId()).findFirst().orElse(null);
    }

    public static boolean existsById(long id) {
        return ROOM_LIST.stream().anyMatch(room -> id == room.getRoomId());
    }

    public static List<Room> getRoomList() {
        return ROOM_LIST;
    }

    private static void writeRoomToDisk(Room r) throws IOException {
        String path = Long.toString(r.getRoomId());
        FileHelper.createFolder(path);
        String json = JsonHelper.toJson(r);
        FileHelper.writeFile(path + "/room.json", json);
    }

    public static RoomSession loginToRoom(User u, long id, String password) throws Exception {
        Room r = getRoomById(id);
        if(r == null) throw new Exception("Room not found!");
        if(!BCrypt.checkpw(password, r.getRoomPassword())) throw new Exception("The password to room "+r.getRoomName()+" was incorrect!");
        RoomSession rs = new RoomSession(u,r);
        ROOM_MAP.put(rs.getSessionToken(),rs);
        return rs;
    }

    public static RoomSession loginToRoom(User u, long id) throws Exception {
        Room r = getRoomById(id);
        if(r == null) throw new Exception("Room not found!");
        RoomSession rs = new RoomSession(u,r);
        ROOM_MAP.put(rs.getSessionToken(),rs);
        return rs;
    }

}
