package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    Integer countOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByCourse_CourseId(Integer courseId);
}
