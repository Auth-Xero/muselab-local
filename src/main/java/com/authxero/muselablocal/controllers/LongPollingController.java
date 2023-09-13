package com.authxero.muselablocal.controllers;

import com.authxero.muselablocal.helpers.JsonHelper;
import com.authxero.muselablocal.helpers.LongPollingHelper;
import com.authxero.muselablocal.helpers.RoomHelper;
import com.authxero.muselablocal.helpers.SessionHelper;
import com.authxero.muselablocal.models.EMessage;
import com.authxero.muselablocal.models.Room;
import com.authxero.muselablocal.models.RoomSession;
import com.authxero.muselablocal.models.User;
import com.authxero.muselablocal.payload.response.PollingResponse;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
                        if (rs.isInRoom()) {
                            deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                            return deferredResult;
                        }
                        Room r = RoomHelper.getRoomById(rs.getRoomId());
                        //System.out.println(r.getParticipants().size());
                        User user = SessionHelper.getUserById(rs.getUserId());
                        r.addParticipant(user);
                        ArrayNode userList = JsonHelper.OBJECT_MAPPER.createArrayNode();
                        for (User u : r.getParticipants()) {
                            ObjectNode userObj = JsonHelper.OBJECT_MAPPER.createObjectNode();
                            userObj.put("username", u.getUsername());
                            userObj.put("id", u.getId());
                            userList.add(userObj);
                        }
                        ObjectNode notifyMessage = JsonHelper.OBJECT_MAPPER.createObjectNode();
                        notifyMessage.put("message", user.getUsername() + " has joined room " + r.getRoomName());
                        notifyMessage.put("user", user.getId());
                        notifyMessage.set("userList", userList);
                        //System.out.println(r.getParticipants().size());
                        for (User u : r.getParticipants()) {
                            try {
                                DeferredResult<ResponseEntity<?>> dr = u.getDeferredResult();
                                if (user.getId() == u.getId()) {
                                    deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.JOIN.getValue(), notifyMessage)));
                                } else {
                                    u.enqueueMessage(ResponseEntity.ok().body(new PollingResponse(EMessage.JOIN.getValue(), notifyMessage)));
                                    if (dr != null) {
                                        dr.setResult(u.nextMessage());
                                        u.setDeferredResult(null);
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        rs.setInRoom(true);
                        LongPollingHelper.addSession(rs.getSessionToken(), rs);
                        RoomHelper.updateRoomById(r.getRoomId(), r);
                    }
                    return deferredResult;
                }
                case CLOSE -> {
                    if (!LongPollingHelper.authenticated(request)) {
                        deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                        return deferredResult;
                    } else {
                        deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                        handleClose(request);
                    }
                    return deferredResult;
                }
                case NO_CHANGES -> {
                    if (!LongPollingHelper.authenticated(request)) {
                        deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                    } else {
                        rs = LongPollingHelper.authenticate(request);
                        if (!rs.isInRoom()) {
                            deferredResult.setResult(ResponseEntity.ok().body(new PollingResponse(EMessage.CLOSE.getValue(), null)));
                            return deferredResult;
                        }
                        Room r = RoomHelper.getRoomById(rs.getRoomId());
                        User user = SessionHelper.getUserById(rs.getUserId());
                        if (r != null) {
                            r.setUpdated();
                            User finalUser = r.getParticipants().stream().filter(u -> u.getId() == user.getId()).findFirst().orElse(null);
                            finalUser.setLastUpdate();
                            try {
                                if (finalUser.hasNextMessage()) {
                                    deferredResult.setResult(finalUser.nextMessage());
                                } else finalUser.setDeferredResult(deferredResult);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            RoomHelper.updateUserById(finalUser.getId(), finalUser, r.getRoomId());
                        }
                    }
                    return deferredResult;
                }
            }
        } catch (Exception ignored) {
        }
        return deferredResult;
    }

    private void handleClose(HttpServletRequest request) {
        RoomSession rs = LongPollingHelper.authenticate(request);
        Room r = RoomHelper.getRoomById(rs.getRoomId());
        //System.out.println(r.getParticipants().size());
        User user = SessionHelper.getUserById(rs.getUserId());
        r.removeParticipant(user);
        ArrayNode userList = JsonHelper.OBJECT_MAPPER.createArrayNode();
        for (User pu : r.getParticipants()) {
            ObjectNode userObj = JsonHelper.OBJECT_MAPPER.createObjectNode();
            userObj.put("username", pu.getUsername());
            userObj.put("id", pu.getId());
            userList.add(userObj);
        }
        ObjectNode notifyMessage = JsonHelper.OBJECT_MAPPER.createObjectNode();
        notifyMessage.put("message", user.getUsername() + " has left");
        ObjectNode notifyLeave = JsonHelper.OBJECT_MAPPER.createObjectNode();
        notifyLeave.put("type", EMessage.LEAVE.getValue());
        notifyLeave.set("data", notifyMessage);
        notifyLeave.set("users", userList);
        for (User u : r.getParticipants()) {
            try {
                DeferredResult<ResponseEntity<?>> dr = u.getDeferredResult();
                u.enqueueMessage(ResponseEntity.ok().body(new PollingResponse(EMessage.LEAVE.getValue(), notifyMessage)));
                if (dr != null) {
                    dr.setResult(u.nextMessage());
                    u.setDeferredResult(null);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        rs.setInRoom(false);
        LongPollingHelper.addSession(rs.getSessionToken(), rs);
        RoomHelper.updateRoomById(r.getRoomId(), r);
    }

}
