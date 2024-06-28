package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    List<OrderDetail> getAllOrderDetail();
    Optional<OrderDetail> getOrderDetailById(Integer id);
    void add(OrderDetail orderDetail);
    void deleteById(Integer id);
    Integer countOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByCourse_CourseId(Integer courseId);
}
