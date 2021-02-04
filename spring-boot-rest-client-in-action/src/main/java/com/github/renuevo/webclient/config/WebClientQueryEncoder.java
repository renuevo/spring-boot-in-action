package com.github.renuevo.webclient.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class WebClientQueryEncoder {

    private final ObjectMapper objectMapper;

    public MultiValueMap<String, String> getQueryEncode(Object queryDto) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, String> queryMap = objectMapper.convertValue(queryDto, new TypeReference<>() {
        });
        params.setAll(queryMap);
        return params;
    }

}
