package com.authxero.muselablocal.helpers;

import com.authxero.muselablocal.models.User;

public class UserHelper {
    public static User createUser(String username) {
        String token = RandomHelper.generateRandomString(32);
        User u = new User(username);
        u.setToken(token);
        SessionHelper.addUser(token, u);
        return u;
    }
}
