package com.github.renuevo.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class NaverBlogParamDto {
    private final String query;
    private final int display;
    private final int start;
    private final String sort;
}
