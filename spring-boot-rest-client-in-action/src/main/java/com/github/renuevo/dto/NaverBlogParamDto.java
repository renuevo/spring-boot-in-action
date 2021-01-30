package com.github.renuevo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class NaverBlogParamDto {
    private final String query;
    private final Integer display;
    private final Integer start;
    private final String sort;
    private final LocalDate dateTest;
}
