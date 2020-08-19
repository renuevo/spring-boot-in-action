package com.github.renuevo.domain.person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRedisRepository personRedisRepository;

    @GetMapping("/save/{name}")
    public String save(@PathVariable String name) {
        PersonEntity personEntity = PersonEntity.builder()
                .name(name)
                .age((new Random()).nextInt(30) + 1)
                .birthday(LocalDate.now())
                .build();

        personEntity = personRedisRepository.save(personEntity);
        log.info("PersonEntity Save : {}", personEntity);
        return personEntity.toString();
    }

    @GetMapping("/get/{name}")
    public PersonEntity get(@PathVariable String name) {
        PersonEntity personEntity = personRedisRepository.findById(name).orElseThrow();
        log.info("PersonEntity Get : {}", personEntity);
        return personEntity;
    }


}
