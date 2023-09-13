package com.benny.multiple.cloud.after.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JSONUtil {

    @Autowired
    ObjectMapper objectMapper ;


    public String toJson(Object obj){
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
