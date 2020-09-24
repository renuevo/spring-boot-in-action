package com.github.renuevo.setup;

import com.github.renuevo.repo.order.OrderHistory;
import com.github.renuevo.repo.store.Store;

public class OrderHistoryBuilder {

    public static OrderHistory orderHistoryBuilder(Store store){
        return OrderHistory.builder()
                .id(null)
                .count(1)
                .name("Test Order")
                .store(store)
                .build();
    }

}
