package com.authxero.muselablocal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private long id;
    private String username;
    @JsonIgnore
    private String token;
    @JsonIgnore
    private final ConcurrentLinkedQueue<ResponseEntity<String>> resultQueues = new ConcurrentLinkedQueue<>();
    @JsonIgnore
    private DeferredResult<ResponseEntity<String>> deferredResult;

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
