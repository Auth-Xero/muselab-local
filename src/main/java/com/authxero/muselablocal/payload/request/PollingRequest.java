package com.authxero.muselablocal.payload.request;

import com.fasterxml.jackson.databind.JsonNode;

public class PollingRequest {
    private int type;
    private JsonNode data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
