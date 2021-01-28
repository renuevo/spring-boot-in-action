package com.github.renuevo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NaverBlogParamDto {
    private final String query;
    private final Integer display;
    private final Integer start;
    private final String sort;
}
