package com.github.renuevo.repo.order;

import com.github.renuevo.domain.order.OrderDataModel;
import com.github.renuevo.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRdbRepository implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    @Override
    public List<OrderDataModel> findOrderAll() {
        return OrderMapper
                .INSTANCE
                .collectionOrderToOrderDataModelList(jpaOrderRepository.findAll());
    }

    @Override
    public OrderDataModel saveOrder(OrderDataModel orderDataModel) {
        //return jpaOrderRepository.save(OrderMapper.INSTANCE.orderDataModelToOrder(orderDataModel));
        return null;
    }
}
