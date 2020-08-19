package com.github.renuevo.domain.person;

import org.springframework.data.repository.CrudRepository;


public interface PersonRedisRepository extends CrudRepository<PersonEntity, String> {
}
