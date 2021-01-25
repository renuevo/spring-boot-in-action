package com.github.renuevo.feign.controller;


import com.github.renuevo.common.NaverProperty;
import com.github.renuevo.dto.NaverBlogParamDto;
import com.github.renuevo.dto.NaverResponse;
import com.github.renuevo.feign.SampleBuildFeignClient;
import com.github.renuevo.feign.SampleFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeignClientController {

    private final SampleFeignClient sampleFeignClient;
    private final SampleBuildFeignClient sampleBuildFeignClient;
    private final NaverProperty naverProperty;

    @GetMapping("/test")
    public NaverResponse callSampleFeginClient() {
        return sampleFeignClient.naverBlogSearch(naverProperty.getId(), naverProperty.getSecret(), NaverBlogParamDto.builder().query("카카오페이").start(1).display(10).sort("sim").build());
    }


}
