package com.github.renuevo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "naver.client")
public class NaverProperty {
    private String id;
    private String secret;
}
