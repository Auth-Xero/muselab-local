package com.authxero.muselablocal.payload.response;

import com.fasterxml.jackson.databind.JsonNode;

public class PollingResponse {
    private int type;
    private JsonNode data;

    public PollingResponse(int type, JsonNode data){
        this.type = type;
        this.data = data;
    }

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
