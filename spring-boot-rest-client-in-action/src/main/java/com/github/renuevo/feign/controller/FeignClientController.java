package com.github.renuevo.feign.controller;


import com.github.renuevo.common.NaverProperty;
import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.feign.SampleBuildFeignClient;
import com.github.renuevo.feign.SampleFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeignClientController {

    private final SampleFeignClient sampleFeignClient;
    private final SampleBuildFeignClient sampleBuildFeignClient;
    private final NaverProperty naverProperty;

    @GetMapping("/client/search")
    public NaverResponse callSampleFeignClient(@RequestParam("query") String query) {
        return sampleFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build());
    }

    @GetMapping("/build-client/search")
    public NaverResponse callSampleBuildFeignClient(@RequestParam("query") String query) {
        return sampleBuildFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query(query).start(1).display(10).sort("sim").build());
    }


}
