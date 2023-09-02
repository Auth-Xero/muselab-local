package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.helpers.AuthHelper;
import com.authxero.muselablocal.helpers.RoomHelper;
import com.authxero.muselablocal.helpers.SessionHelper;
import com.authxero.muselablocal.helpers.UserHelper;
import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.User;
import com.authxero.muselablocal.payload.request.CreateRoomRequest;
import com.authxero.muselablocal.payload.request.LoginRequest;
import com.authxero.muselablocal.payload.response.CreateRoomResponse;
import com.authxero.muselablocal.payload.response.LoginResponse;
import com.authxero.muselablocal.payload.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateRoomRequest createRoomRequest, HttpServletRequest request) {
        if (!AuthHelper.authenticated(request))
            return ResponseEntity.status(401).body(new MessageResponse("Not authenticated."));
        Room r;

        if (createRoomRequest.getRoomPassword() == null) r = RoomHelper.createRoom(createRoomRequest.getRoomName());
        else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(createRoomRequest.getRoomPassword());
            r = RoomHelper.createRoom(createRoomRequest.getRoomName(), encodedPassword);
        }
        return ResponseEntity.ok(new CreateRoomResponse(r.getRoomId(), r.getRoomName(), r.isLocked()));
    }
}
