package com.github.renuevo.resttemplate.controller;

import com.github.renuevo.resttemplate.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplateService restTemplateService;


}
