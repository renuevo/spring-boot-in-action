package com.github.renuevo.domain.simple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SimpleTestService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void simpleTest() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("name", "rubber.duck");
        log.info("Simple Test Name : {}", valueOperations.get("name"));
    }

}
