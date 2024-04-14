package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.handlers.MessageHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/polling")
public class LongPollingController {
    private final MessageHandler messageHandler;

    public LongPollingController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @GetMapping("/message/{type:[0-7]}")
    public DeferredResult<ResponseEntity<?>> message(@PathVariable("type") Integer type, HttpServletRequest request) {
        return messageHandler.handleMessage(type, request);
    }
}
