package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.helpers.LongPollingHelper;
import com.authxero.muselablocal.helpers.RoomHelper;
import com.authxero.muselablocal.helpers.SessionHelper;
import com.authxero.muselablocal.models.EMessage;
import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.payload.response.PollingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/polling")
public class LongPollingController {
    EMessage[] MESSAGE_TYPES = EMessage.values();

    @GetMapping("/message/{type:[0-7]}")
    public DeferredResult<ResponseEntity<?>> message(@PathVariable("type") Integer type, HttpServletRequest request) {
        RoomSession rs;
        ResponseEntity<?> timeoutResult = ResponseEntity.ok().body(new PollingResponse(EMessage.NO_CHANGES.getValue(), null));
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(LongPollingHelper.TIMEOUT_MS, timeoutResult);
        try {
            switch (MESSAGE_TYPES[type]) {
                case LOGIN -> {
                    if (!LongPollingHelper.authenticated(request)) {
                        deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                        return deferredResult;
                    } else {
                        rs = LongPollingHelper.authenticate(request);
                        if(rs.isInRoom()){
                            deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                            return deferredResult;
                        }
                        Room r = RoomHelper.getRoomById(rs.getRoomId());
                        r.addParticipant(SessionHelper.getUserById(rs.getUserId()));
                    }
                }

            }
        } catch (Exception ignored) {
        }
        return deferredResult;
    }


}
