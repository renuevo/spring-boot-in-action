package com.github.renuevo.elastic.controller;

import com.github.renuevo.dto.ElasticDataDto;
import com.github.renuevo.dto.ElasticParamDto;
import com.github.renuevo.elastic.service.ElasticWebFluxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

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

    @GetMapping("/search")
    public Flux<ElasticDataDto> searchDoc(@ModelAttribute @Valid ElasticParamDto elasticParamDto, Errors errors) {
        try {
        /*    if (errors.hasErrors())
                return Flux.just(ResponseEntity.badRequest().body(errors.getAllErrors()));
*/
            //search


            //self hateoas
            Link selfLink = ControllerLinkBuilder
                    .linkTo(ControllerLinkBuilder.methodOn(ElasticWebfluxController.class).searchDoc(elasticParamDto, errors))
                    .withSelfRel();

        } catch (Exception e) {
            log.error("Elastic Webflux List Search Error {}", e.getMessage(), e);
            log.error("Error Param {}", elasticParamDto);
            //  return Flux.just(ResponseEntity.badRequest().body(e.getMessage()));
        }
        return elasticWebFluxService.searchDoc(elasticParamDto);
    }

}
