package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
