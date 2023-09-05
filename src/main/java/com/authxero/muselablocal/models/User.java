package com.authxero.muselablocal.models;

import com.authxero.muselablocal.helpers.RandomHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private long id;
    private String username;
    private String token;
    private Long lastUpdate;
    private final ConcurrentLinkedQueue<ResponseEntity<?>> resultQueues = new ConcurrentLinkedQueue<>();
    private DeferredResult<ResponseEntity<?>> deferredResult;

    public User(String username) {
        this.username = username;
        this.id = RandomHelper.generateRandomLong(0, 1000000000);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean hasNextMessage() {
        return resultQueues.peek() != null;
    }

    public ResponseEntity<?> nextMessage() {
        return resultQueues.poll();
    }

    public DeferredResult<ResponseEntity<?>> getDeferredResult() {
        return deferredResult;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate() {
        this.lastUpdate = System.currentTimeMillis();
    }

    public void setDeferredResult(DeferredResult<ResponseEntity<?>> deferredResult) {
        this.deferredResult = deferredResult;
    }

    public void enqueueMessage(ResponseEntity<?> msg) {
        resultQueues.add(msg);
    }
}
