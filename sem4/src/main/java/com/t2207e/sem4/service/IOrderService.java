package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.User;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> getAllOrder();
    Optional<Order> getOrderById(Integer id);
    void add(Order order);
    void deleteById(Integer id);
    Optional<Order> getOrderByUserAndEventAndStatus(User user, Event event, int status);
    List<Order> getOrdersByStatus(Integer status);
    List<RevenueDTO> TotalDayProcedure();
    List<RevenueDTO> TotalWeekProcedure();
    List<RevenueDTO> TotalMonthProcedure();
    List<RevenueDTO> TotalYearProcedure();

}
