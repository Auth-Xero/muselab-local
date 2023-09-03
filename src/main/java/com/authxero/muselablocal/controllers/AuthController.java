package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.helpers.SessionHelper;
import com.authxero.muselablocal.models.User;
import com.authxero.muselablocal.payload.request.LoginRequest;
import com.authxero.muselablocal.payload.response.LoginResponse;
import com.authxero.muselablocal.payload.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        if (SessionHelper.existsByUsername(loginRequest.getUsername()))
            return ResponseEntity.status(403).body(new MessageResponse("Username is already taken!"));
        User u = SessionHelper.createUser(loginRequest.getUsername());
        return ResponseEntity.ok(new LoginResponse(u.getId(), u.getUsername(), u.getToken()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null) return ResponseEntity.ok(new MessageResponse("You have been logged out."));
        SessionHelper.removeBySession(token);
        return ResponseEntity.ok(new MessageResponse("You have been logged out."));
    }
}