package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
