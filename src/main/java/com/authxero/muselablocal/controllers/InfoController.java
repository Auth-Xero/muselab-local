package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.payload.response.CheckResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    //Endpoint for client to check if server really is a MuseLab Local server
    @GetMapping("/muselab_check")
    public ResponseEntity<?> museLabCheck(){
        return ResponseEntity.ok(new CheckResponse("thisIsAMuseLabLocalServer"));
    }
}
