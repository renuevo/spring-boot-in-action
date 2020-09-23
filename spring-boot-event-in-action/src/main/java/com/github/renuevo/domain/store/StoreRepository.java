package com.github.renuevo.domain.store;

import com.github.renuevo.domain.order.OrderDataModel;

public interface StoreRepository {

    StoreDataModel saveStore(StoreDataModel storeDataModel);
    StoreDataModel orderStore(OrderDataModel orderDataModel);

}
