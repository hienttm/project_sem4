package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> getAllOrder();
    Optional<Order> getOrderById(Integer id);
    void add(Order order);
    void deleteById(Integer id);
}
