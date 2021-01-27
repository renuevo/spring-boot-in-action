package com.github.renuevo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableFeignClients
@EnableReactiveFeignClients
@EnableWebFlux
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootRestClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestClientApplication.class, args);
    }
}
