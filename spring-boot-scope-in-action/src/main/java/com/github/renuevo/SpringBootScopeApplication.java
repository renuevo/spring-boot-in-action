package com.github.renuevo;

import com.github.renuevo.scope.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootScopeApplication implements CommandLineRunner {

    @Autowired
    private ScopeService scopeService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScopeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scopeService.scopeTest();
    }
}
