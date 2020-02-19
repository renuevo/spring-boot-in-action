package com.github.renuevo.feign;


import com.github.renuevo.dto.NaverBlogParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Sample-Feign-Client", url = "https://openapi.naver.com/v1/search")
public interface SampleFeignClient {

    @GetMapping(value="blog.json")
    String naverBlogSearch(
            @RequestHeader("X-Naver-Client-Id") String id,
            @RequestHeader("X-Naver-Client-Secret") String secret,
            @RequestParam NaverBlogParam naverBlogParam);

}
