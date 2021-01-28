package com.github.renuevo.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class NaverBlogParamDto {
    private final String query;
    private final Integer display;
    private final Integer start;
    private final String sort;
    private final String testParam;
}
