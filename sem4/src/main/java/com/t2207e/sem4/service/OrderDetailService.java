package com.t2207e.sem4.service;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.dto.UserDoExamDTO;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.IOrderDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService implements IOrderDetailService{
    private final IOrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(IOrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public void add(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Integer id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public Integer countOrderDetailsByCourse_CourseId(Integer courseId) {
        return orderDetailRepository.countOrderDetailsByCourse_CourseId(courseId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.getOrderDetailsByOrder(order);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrder_User(User user) {
        return orderDetailRepository.getOrderDetailsByOrder_StatusAndOrder_User(1 ,user);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByCourse_CourseId(Integer courseId) {
        return orderDetailRepository.getOrderDetailsByCourse_CourseId(courseId);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByCourse_User_UserId(Integer userId) {
        return orderDetailRepository.getOrderDetailsByCourse_User_UserId(userId);
    }

    @Override
    @Transactional
    public List<RevenueDTO> RevenueChartDayTeacherProcedure(Integer userId) {
        List<Object[]> resultList = orderDetailRepository.RevenueChartDayTeacherProcedure(userId);
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
    public List<RevenueDTO> RevenueChartWeekTeacherProcedure(Integer userId) {
        List<Object[]> resultList = orderDetailRepository.RevenueChartWeekTeacherProcedure(userId);
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
    public List<RevenueDTO> RevenueChartMonthTeacherProcedure(Integer userId) {
        List<Object[]> resultList = orderDetailRepository.RevenueChartMonthTeacherProcedure(userId);
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
    public List<RevenueDTO> RevenueChartYearTeacherProcedure(Integer userId) {
        List<Object[]> resultList = orderDetailRepository.RevenueChartYearTeacherProcedure(userId);
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
    public List<Object[]> getOrderDetailsByUserAndDate(Integer userId, Date startDate, Date endDate) {
        return orderDetailRepository.getOrderDetailsByUserAndDateProcedure(userId, startDate, endDate);
    }
}
