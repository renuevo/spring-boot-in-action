package com.github.renuevo.webclient.controller;

import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.webclient.config.WebClientQueryEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/webclient")
public class WebClientController {

    @Qualifier("commonWebClient")
    private final WebClient commonWebClient;

    @Qualifier("naverWebClient")
    private final WebClient naverWebClient;

    private final WebClientQueryEncoder webclientQueryEncoder;

    @GetMapping("/search")
    public NaverResponse getNaverSearch(@RequestParam("query") String query) {
        return naverWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/blog.json")
                                .queryParams(webclientQueryEncoder.getQueryEncode(
                                        NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build()))
                                .build())
                        .retrieve()
                        .bodyToMono(NaverResponse.class)
                        .toProcessor()  //Deprecated. -> share()
                        .block();
    }

    @GetMapping("/webflux/search")
    public Mono<NaverResponse> getNaverWebfluxSearch(@RequestParam("query") String query) {
        return naverWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/blog.json")
                        .queryParams(webclientQueryEncoder.getQueryEncode(
                                NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build()))
                        .build())
                .retrieve()
                .bodyToMono(NaverResponse.class);
    }


}
