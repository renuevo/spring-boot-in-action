package com.github.renuevo.elastic.controller;

import com.github.renuevo.dto.ElasticDataDto;
import com.github.renuevo.elastic.service.ElasticWebFluxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <pre>
 * @className : ElasticWebfluxController
 * @author : Deokhwa.Kim
 * @since : 2020-04-29
 * </pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/elastic/")
public class ElasticWebfluxController {

    private final ElasticWebFluxService elasticWebFluxService;

    @GetMapping("/{id}")
    public Mono<ElasticDataDto> viewDoc(@PathVariable String id) {
        return elasticWebFluxService.viewDoc(id)
                .onErrorResume(error -> {
                    log.error("Get Error {}", error.getMessage(), error);
                    return Mono.empty();
                });
    }

}
