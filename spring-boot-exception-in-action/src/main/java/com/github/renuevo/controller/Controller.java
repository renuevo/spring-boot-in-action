package com.github.renuevo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/test")
    public ResponseEntity<?> errorTest() throws Exception {
        if (true)
            throw new Exception("");
        return ResponseEntity.ok("test");
    }

}