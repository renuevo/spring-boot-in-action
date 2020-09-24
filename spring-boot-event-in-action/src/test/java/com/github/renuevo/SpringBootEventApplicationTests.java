package com.github.renuevo;


import com.github.renuevo.repo.order.JpaOrderRepository;
import com.github.renuevo.repo.order.OrderHistory;
import com.github.renuevo.repo.store.JpaStoreRepository;
import com.github.renuevo.repo.store.Store;
import com.github.renuevo.setup.OrderHistoryBuilder;
import com.github.renuevo.setup.StoreBuilder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootEventApplicationTests {

    @Autowired
    JpaStoreRepository jpaStoreRepository;

    @Autowired
    JpaOrderRepository jpaOrderRepository;


    @BeforeEach
    public void init() {
        Store store = jpaStoreRepository.save(StoreBuilder.storeBuilder());
        OrderHistory orderHistory = jpaOrderRepository.save(OrderHistoryBuilder.orderHistoryBuilder(store));
    }

    @Test
    public void insert() {
        init();
        System.out.println(jpaOrderRepository.findAll().get(0).getId());
    }

}
