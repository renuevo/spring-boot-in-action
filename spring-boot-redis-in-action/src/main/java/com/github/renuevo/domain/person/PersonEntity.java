package com.github.renuevo.domain.person;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("presonEntity")
public class PersonEntity implements Serializable {

    @Id
    private String name;
    private Integer age;
    private LocalDate birthday;

}
