package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    private final IOrderRepository orderRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public void add(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> getOrderByUserAndEventAndStatus(User user, Event event, int status) {
        return orderRepository.getOrderByUserAndEventAndStatus(user, event, status);
    }

}
