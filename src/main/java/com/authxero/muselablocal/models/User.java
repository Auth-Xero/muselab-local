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

    private final ConcurrentLinkedQueue<ResponseEntity<String>> resultQueues = new ConcurrentLinkedQueue<>();
    private DeferredResult<ResponseEntity<String>> deferredResult;

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

    public ResponseEntity<String> nextMessage() {
        return resultQueues.poll();
    }

    public DeferredResult<ResponseEntity<String>> getDeferredResult() {
        return deferredResult;
    }

    public void setDeferredResult(DeferredResult<ResponseEntity<String>> deferredResult) {
        this.deferredResult = deferredResult;
    }
}
