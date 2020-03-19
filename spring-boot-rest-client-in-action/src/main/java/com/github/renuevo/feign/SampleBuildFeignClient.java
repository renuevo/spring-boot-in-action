package com.github.renuevo.feign;

import com.github.renuevo.dto.NaverBlogParam;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public interface SampleBuildFeignClient {

    @GetMapping(value="blog.json")
    String naverBlogSearch(
            @RequestHeader("X-Naver-Client-Id") String id,
            @RequestHeader("X-Naver-Client-Secret") String secret,
            @SpringQueryMap NaverBlogParam naverBlogParam);
}
