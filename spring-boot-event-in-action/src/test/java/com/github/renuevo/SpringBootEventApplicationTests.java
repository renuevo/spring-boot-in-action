package com.github.renuevo;


import com.github.renuevo.domain.order.OrderDataModel;
import com.github.renuevo.repo.order.OrderMapper;
import com.github.renuevo.repo.order.OrderRdbRepository;
import com.github.renuevo.repo.store.JpaStoreRepository;
import com.github.renuevo.repo.store.Store;
import com.github.renuevo.repo.store.StoreRdbRepository;
import com.github.renuevo.setup.OrderHistoryBuilder;
import com.github.renuevo.setup.StoreBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootEventApplicationTests {

    @Autowired
    JpaStoreRepository jpaStoreRepository;

    @Autowired
    StoreRdbRepository storeRdbRepository;

    @Autowired
    OrderRdbRepository orderRdbRepository;


    @BeforeEach
    public void init() {
        //given
        //List.of(1, 2, 3, 4, 5).forEach(number -> jpaStoreRepository.save(StoreBuilder.storeNumberBuilder(number)));
    }

    @Test
    @Transactional
    void orderEventTest() {

        //given
        Store store = jpaStoreRepository.save(StoreBuilder.storeBuilder());

        //when
        OrderDataModel orderDataModel = orderRdbRepository.saveOrder(OrderMapper
                .INSTANCE
                .orderToOrderDataModel(OrderHistoryBuilder.orderHistoryBuilder(store)));

        //then
        store = jpaStoreRepository.getOne(store.getId());
        assertThat(store.getOrderHistorySet()
                .stream()
                .findFirst()
                .orElseThrow()
                .getId())
                .isEqualTo(orderDataModel.getId());

    }

}
