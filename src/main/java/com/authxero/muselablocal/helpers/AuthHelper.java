package com.authxero.muselablocal.helpers;

import jakarta.servlet.http.HttpServletRequest;

public class AuthHelper {
    public static boolean authenticated(HttpServletRequest req){
        String token = req.getHeader("token");
        if(token == null) return false;
        return SessionHelper.existsBySession(token);
    }
}
