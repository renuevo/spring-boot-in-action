package com.github.renuevo.web;

import com.github.renuevo.domain.simple.SimpleTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisController {

    private final SimpleTestService simpleTestService;

    @GetMapping("/simple_test")
    public void simpleTest(){
        simpleTestService.simpleTest();
    }


}
