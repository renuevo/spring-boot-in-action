package com.github.renuevo;

import com.github.renuevo.scope.ScopeService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class SpringBootScopeApplication implements CommandLineRunner {

    private final ScopeService scopeService;


    public static void main(String[] args) {
        SpringApplication.run(SpringBootScopeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scopeService.scopeTest();
    }
}
