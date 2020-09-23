package com.github.renuevo.domain.order;

import com.github.renuevo.domain.store.StoreDataModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDataModel {
    private final StoreDataModel storeDataModel;
    private final Integer count;
    private final String name;
}
