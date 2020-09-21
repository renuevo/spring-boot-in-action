package com.github.renuevo.domain.order;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDataModel {
    private int productNumber;
    private int count;
    private String name;
}
