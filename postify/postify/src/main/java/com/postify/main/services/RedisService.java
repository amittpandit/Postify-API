package com.postify.main.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public <T> void storeData(String key, T value, Long ttl, TimeUnit unit){
        ValueOperations<String ,Object> valueOperations=redisTemplate.opsForValue();
        if (ttl!=null && unit !=null){
            valueOperations.set(key,value,ttl,unit);
        }else {
            valueOperations.set(key,value);
        }
    }

    public <T> T retrieveData(String key,Class<T> clazz){
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ValueOperations<String ,Object> valueOperations=redisTemplate.opsForValue();
        Object value=valueOperations.get(key);
        if (value!=null){
            return objectMapper.convertValue(value,clazz);
        }
        return null;
    }
}
