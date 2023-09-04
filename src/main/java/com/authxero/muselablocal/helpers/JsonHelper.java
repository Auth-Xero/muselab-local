package com.authxero.muselablocal.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, valueType);
    }
}
