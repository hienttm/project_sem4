package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Event;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> getOrderByUserAndEventAndStatus(User user, Event event, int status);
    List<Order> getOrdersByStatus(Integer status);
    @Procedure(name = "TotalDayProcedure")
    List<Object[]> TotalDayProcedure();
    @Procedure(name = "TotalWeekProcedure")
    List<Object[]> TotalWeekProcedure();
    @Procedure(name = "TotalMonthProcedure")
    List<Object[]> TotalMonthProcedure();
    @Procedure(name = "TotalYearProcedure")
    List<Object[]> TotalYearProcedure();
}
