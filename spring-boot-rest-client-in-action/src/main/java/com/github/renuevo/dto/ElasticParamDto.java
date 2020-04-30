package com.github.renuevo.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ElasticParamDto {

    String keyword;

    @Min(1)
    int limit =3;

    @Min(0)
    int page = 0;

}
