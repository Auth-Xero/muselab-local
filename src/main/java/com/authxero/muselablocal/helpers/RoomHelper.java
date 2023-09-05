package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class RoomHelper {
    private final static List<Room> ROOM_LIST = new ArrayList<>();

    @Autowired
    public RoomHelper() throws IOException {
        List<String> folders = FileHelper.listFoldersInDataFolder();
        for (String f : folders) {
            try {
                String roomData = FileHelper.readFile(f + "/room.json");
                Room r = JsonHelper.fromJson(roomData, Room.class);
                ROOM_LIST.add(r);
            } catch (Exception ignored) {
            }
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

    public static void updateRoomById(long id, Room r) {
        int index = IntStream.range(0, ROOM_LIST.size())
                .filter(i -> ROOM_LIST.get(i).getRoomId() == id)
                .findFirst()
                .orElse(-1);
        ROOM_LIST.set(index, r);
    }

    public static void updateUserById(long id, User u, long roomId) {
        Room r = getRoomById(roomId);
        int index = IntStream.range(0, r.getParticipants().size())
                .filter(i -> r.getParticipants().get(i).getId() == id)
                .findFirst()
                .orElse(-1);
        r.getParticipants().set(index, u);
        int index1 = IntStream.range(0, ROOM_LIST.size())
                .filter(i -> ROOM_LIST.get(i).getRoomId() == r.getRoomId())
                .findFirst()
                .orElse(-1);
        ROOM_LIST.set(index1, r);
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
        if (r == null) throw new Exception("Room not found!");
        if (r.isLocked())
            if (!BCrypt.checkpw(password, r.getRoomPassword()))
                throw new Exception("The password to room " + r.getRoomName() + " was incorrect!");
        RoomSession rs = new RoomSession(u.getId(), r.getRoomId());
        LongPollingHelper.addSession(rs.getSessionToken(), rs);
        return rs;
    }

    public static RoomSession loginToRoom(User u, long id) throws Exception {
        Room r = getRoomById(id);
        if (r == null) throw new Exception("Room not found!");
        if (r.isLocked()) throw new Exception("The room you are trying to access is locked!");
        RoomSession rs = new RoomSession(u.getId(), r.getRoomId());
        LongPollingHelper.addSession(rs.getSessionToken(), rs);
        return rs;
    }

}
