package com.github.renuevo.domain.store;

import com.github.renuevo.repo.order.OrderHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreDataModel {

    private final Long id;
    private final String name;
    private final Integer stock;
    private final List<OrderHistory> orderHistoryList;

}
