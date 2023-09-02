package com.authxero.muselablocal.payload.response;

import com.authxero.muselablocal.payload.request.LoginRequest;

public class LoginResponse {
    private long id;
    private String username;
    private String token;

    public LoginResponse(long id, String username, String token){
       this.id = id;
       this.username = username;
       this.token = token;
    }

    public String getToken() {
        return token;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
