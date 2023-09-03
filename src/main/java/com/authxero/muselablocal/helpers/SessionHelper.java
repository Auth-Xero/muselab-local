package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.User;

import java.util.concurrent.ConcurrentHashMap;

public class SessionHelper {
    private final static ConcurrentHashMap<String, User> SESSIONS = new ConcurrentHashMap<>();

    public static void addUser(String s, User u) {
        SESSIONS.put(s, u);
    }

    public static User getUserBySession(String s) {
        return SESSIONS.get(s);
    }

    public static User getUserByUsername(String u) {
        return SESSIONS.values().stream().filter(user -> u.equals(user.getUsername())).findFirst().orElse(null);
    }

    public static User getUserById(long id) {
        return SESSIONS.values().stream().filter(user -> id == user.getId()).findFirst().orElse(null);
    }

    public static boolean existsById(long id) {
        return SESSIONS.values().stream().anyMatch(user -> user.getId() == id);
    }

    public static boolean existsByUsername(String username) {
        return SESSIONS.values().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static boolean existsBySession(String session) {
        return SESSIONS.containsKey(session);
    }

    public static void removeBySession(String session) {
        SESSIONS.remove(session);
    }

    public static User createUser(String username) {
        String token = RandomHelper.generateRandomString(32);
        User u = new User(username);
        u.setToken(token);
        addUser(token, u);
        return u;
    }
}
