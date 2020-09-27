package com.github.renuevo.repo.store.event;

import com.github.renuevo.repo.order.OrderMapper;
import com.github.renuevo.repo.store.StoreRdbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final StoreRdbRepository storeRdbRepository;


    @EventListener
    public void orderInsert(OrderEvent orderEvent){
        storeRdbRepository.orderStore(OrderMapper.INSTANCE.orderToOrderDataModel(orderEvent.getOrderHistory()));
    }

}
