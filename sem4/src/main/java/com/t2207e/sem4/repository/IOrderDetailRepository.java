package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    Integer countOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByCourse_CourseId(Integer courseId);
    List<OrderDetail> getOrderDetailsByCourse_User_UserId(Integer userId);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    List<OrderDetail> getOrderDetailsByOrder_StatusAndOrder_User(Integer status ,User user);
    @Procedure(name = "RevenueChartDayTeacherProcedure")
    List<Object[]> RevenueChartDayTeacherProcedure(@Param("userId") Integer userId);
    @Procedure(name = "RevenueChartWeekTeacherProcedure")
    List<Object[]> RevenueChartWeekTeacherProcedure(@Param("userId") Integer userId);
    @Procedure(name = "RevenueChartMonthTeacherProcedure")
    List<Object[]> RevenueChartMonthTeacherProcedure(@Param("userId") Integer userId);
    @Procedure(name = "RevenueChartYearTeacherProcedure")
    List<Object[]> RevenueChartYearTeacherProcedure(@Param("userId") Integer userId);

    @Procedure(name = "getOrderDetailsByUserAndDateProcedure")
    List<Object[]> getOrderDetailsByUserAndDateProcedure(@Param("userId") Integer userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
