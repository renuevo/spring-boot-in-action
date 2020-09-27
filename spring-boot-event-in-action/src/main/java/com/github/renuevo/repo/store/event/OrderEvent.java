package com.github.renuevo.repo.store.event;

import com.github.renuevo.repo.order.OrderHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderEvent {

    private final OrderHistory orderHistory;

}
