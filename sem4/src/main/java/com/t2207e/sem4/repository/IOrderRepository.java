package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> getOrderByUserAndEventAndStatus(User user, Event event, int status);
}
