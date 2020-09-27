package com.github.renuevo.repo.store;

import com.github.renuevo.repo.order.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Integer stock;

    @OneToMany
    @JoinColumn(name = "store_id")
    private final Set<OrderHistory> orderHistorySet = new LinkedHashSet<>();

    public boolean orderCount(OrderHistory orderHistory) {
        if (stock - orderHistory.getCount() >= 0) {
            this.orderHistorySet.add(orderHistory);
            return true;
        }
        return false;
    }

}
