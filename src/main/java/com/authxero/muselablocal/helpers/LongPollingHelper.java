package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.models.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class LongPollingHelper {
    private final static ConcurrentHashMap<String, RoomSession> SESSIONS = new ConcurrentHashMap<>();
    public static final long TIMEOUT_MS = 45000;

    public static boolean authenticated(HttpServletRequest req) {
        String token = req.getHeader("sessionToken");
        if (token == null) return false;
        return existsBySessionToken(token);
    }

    public static RoomSession authenticate(HttpServletRequest req) {
        String token = req.getHeader("sessionToken");
        return getRoomSessionBySessionToken(token);
    }

    private static boolean existsBySessionToken(String session) {
        return SESSIONS.containsKey(session);
    }

    private static void removeBySession(String session) {
        SESSIONS.remove(session);
    }

    private static RoomSession getRoomSessionBySessionToken(String s) {
        return SESSIONS.get(s);
    }

    public static void addSession(String s, RoomSession rs) {
        SESSIONS.put(s, rs);
    }

}
