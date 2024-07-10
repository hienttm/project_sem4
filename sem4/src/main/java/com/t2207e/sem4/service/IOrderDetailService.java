package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IOrderDetailService {
    List<OrderDetail> getAllOrderDetail();
    Optional<OrderDetail> getOrderDetailById(Integer id);
    void add(OrderDetail orderDetail);
    void deleteById(Integer id);
    Integer countOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    List<OrderDetail> getOrderDetailsByOrder_User(User user);
    List<OrderDetail> getOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByCourse_User_UserId(Integer userId);
    List<RevenueDTO> RevenueChartDayTeacherProcedure(Integer userId);
    List<RevenueDTO> RevenueChartWeekTeacherProcedure(Integer userId);
    List<RevenueDTO> RevenueChartMonthTeacherProcedure(Integer userId);
    List<RevenueDTO> RevenueChartYearTeacherProcedure(Integer userId);
    List<Object[]> getOrderDetailsByUserAndDate(Integer userId, Date startDate, Date endDate);
}
