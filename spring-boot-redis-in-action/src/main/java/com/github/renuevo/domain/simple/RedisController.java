package com.github.renuevo.domain.simple;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/simple")
public class RedisController {

    private final SimpleTestService simpleTestService;

    @GetMapping("/test")
    public void simpleTest(){
        simpleTestService.simpleTest();
    }


}
