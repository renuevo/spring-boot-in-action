package com.github.renuevo.repo.order;

import com.github.renuevo.domain.order.OrderDataModel;
import com.github.renuevo.domain.order.OrderRepository;
import com.github.renuevo.repo.store.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRdbRepository implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public List<OrderDataModel> findOrderAll() {
        return OrderMapper
                .INSTANCE
                .collectionOrderToOrderDataModelList(jpaOrderRepository.findAll());
    }

    @Override
    public OrderDataModel saveOrder(OrderDataModel orderDataModel) {
        OrderHistory orderHistory = jpaOrderRepository.save(OrderMapper.INSTANCE.orderDataModelToOrder(orderDataModel));
        publisher.publishEvent(new OrderEvent(orderHistory));
        return OrderMapper.INSTANCE.orderToOrderDataModel(orderHistory);
    }
}
