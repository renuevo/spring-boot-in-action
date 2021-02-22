package com.github.renuevo.resttemplate.service;

import com.github.renuevo.dto.NaverResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class RestTemplateService {

    @Value("${naver.url.search}")
    private String url;
    private final RestTemplate restTemplate;

    public NaverResponse naverRestTemplateCall() {
        return restTemplate.getForObject(url + "/{subUrl}", NaverResponse.class, "blog.json");
    }

}
