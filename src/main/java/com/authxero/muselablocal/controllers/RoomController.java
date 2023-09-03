package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.helpers.AuthHelper;
import com.authxero.muselablocal.helpers.RoomHelper;
import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.models.User;
import com.authxero.muselablocal.payload.request.CreateRoomRequest;
import com.authxero.muselablocal.payload.request.JoinRoomRequest;
import com.authxero.muselablocal.payload.response.MessageResponse;
import com.authxero.muselablocal.payload.response.RoomListResponse;
import com.authxero.muselablocal.payload.response.RoomResponse;
import com.authxero.muselablocal.payload.response.RoomSessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(new RoomResponse(r.getRoomId(), r.getRoomName(), r.isLocked()));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        if (!AuthHelper.authenticated(request))
            return ResponseEntity.status(401).body(new MessageResponse("Not authenticated."));
        return ResponseEntity.ok(new RoomListResponse(RoomHelper.getRoomList()));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRoomRequest joinRoomRequest, HttpServletRequest request) {
        if (!AuthHelper.authenticated(request))
            return ResponseEntity.status(401).body(new MessageResponse("Not authenticated."));
        if (!RoomHelper.existsById(joinRoomRequest.getRoomId()))
            return ResponseEntity.status(404).body(new MessageResponse("Room not found."));
        User u = AuthHelper.authenticate(request);
        Room r = RoomHelper.getRoomById(joinRoomRequest.getRoomId());
        RoomSession rs;
        try {
            if (r.isLocked() && joinRoomRequest.getRoomPassword() != null)
                rs = RoomHelper.loginToRoom(u, joinRoomRequest.getRoomId(), joinRoomRequest.getRoomPassword());
            else rs = RoomHelper.loginToRoom(u, joinRoomRequest.getRoomId());
            return ResponseEntity.ok(new RoomSessionResponse(r.getRoomId(), r.getRoomName(), rs.getSessionToken()));
        } catch (Exception exception) {
            return ResponseEntity.status(401).body(new MessageResponse(exception.getMessage()));
        }
    }
}
