package com.benny.multiple.cloud.after;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONUtil {

    static ObjectMapper objectMapper = new ObjectMapper();


    public static String toJson(Object obj){
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("to jsonString fail",e);
            throw new RuntimeException(e);
        }
        return jsonString;
    }

}
