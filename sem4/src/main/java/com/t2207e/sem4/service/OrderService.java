package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.IOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Order> getOrdersByStatus(Integer status) {
        return orderRepository.getOrdersByStatus(status);
    }

    @Override
    @Transactional
    public List<RevenueDTO> TotalDayProcedure() {
        List<Object[]> resultList = orderRepository.TotalDayProcedure();
        return resultList.stream()
                .map(result -> {
                    RevenueDTO revenueDTO = new RevenueDTO();
                    revenueDTO.setCount((Integer) result[0]);
                    revenueDTO.setPrice((Double) result[1]);
                    return revenueDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RevenueDTO> TotalWeekProcedure() {
        List<Object[]> resultList = orderRepository.TotalWeekProcedure();
        return resultList.stream()
                .map(result -> {
                    RevenueDTO revenueDTO = new RevenueDTO();
                    revenueDTO.setCount((Integer) result[0]);
                    revenueDTO.setPrice((Double) result[1]);
                    return revenueDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RevenueDTO> TotalMonthProcedure() {
        List<Object[]> resultList = orderRepository.TotalMonthProcedure();
        return resultList.stream()
                .map(result -> {
                    RevenueDTO revenueDTO = new RevenueDTO();
                    revenueDTO.setCount((Integer) result[0]);
                    revenueDTO.setPrice((Double) result[1]);
                    return revenueDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RevenueDTO> TotalYearProcedure() {
        List<Object[]> resultList = orderRepository.TotalYearProcedure();
        return resultList.stream()
                .map(result -> {
                    RevenueDTO revenueDTO = new RevenueDTO();
                    revenueDTO.setCount((Integer) result[0]);
                    revenueDTO.setPrice((Double) result[1]);
                    return revenueDTO;
                })
                .collect(Collectors.toList());
    }

}
