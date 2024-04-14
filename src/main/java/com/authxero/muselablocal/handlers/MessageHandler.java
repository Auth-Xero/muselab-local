package com.authxero.muselablocal.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
@Component
public class MessageHandler {
    private final com.authxero.muselablocal.controllers.MessageService messageService;

    public MessageHandler(com.authxero.muselablocal.controllers.MessageService messageService) {
        this.messageService = messageService;
    }

    public DeferredResult<ResponseEntity<?>> handleMessage(Integer type, HttpServletRequest request) {
        return messageService.handleMessage(type, request);
    }
}
