package com.authxero.muselablocal.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
