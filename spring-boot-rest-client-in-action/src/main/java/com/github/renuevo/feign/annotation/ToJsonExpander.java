package com.github.renuevo.feign.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Param;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToJsonExpander implements Param.Expander{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String expand(Object value) {
        return objectMapper.writeValueAsString(value);
    }

}
