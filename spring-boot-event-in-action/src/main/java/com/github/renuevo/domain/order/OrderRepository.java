package com.github.renuevo.domain.order;

import java.util.List;

public interface OrderRepository {

    List<OrderDataModel> findOrderAll();
    OrderDataModel saveOrder(OrderDataModel orderDataModel);

}
