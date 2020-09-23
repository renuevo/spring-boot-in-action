package com.github.renuevo.repo.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderHistory, Long> {
}
