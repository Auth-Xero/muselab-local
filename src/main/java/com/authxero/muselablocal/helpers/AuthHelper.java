package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class AuthHelper {
    private final static ConcurrentHashMap<String, User> SESSIONS = new ConcurrentHashMap<>();

    public static boolean authenticated(HttpServletRequest req){
        String token = req.getHeader("token");
        if(token == null) return false;
        return SessionHelper.existsBySession(token);
    }

    public static User authenticate(HttpServletRequest req){
        String token = req.getHeader("token");
        return SessionHelper.getUserBySession(token);
    }
}
