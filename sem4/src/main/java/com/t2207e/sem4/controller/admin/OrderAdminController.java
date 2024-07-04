package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.service.OrderDetailService;
import com.t2207e.sem4.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/order")
public class OrderAdminController {
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public OrderAdminController(OrderService orderService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("list")
    public String List(Model model){
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "admin/orders/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable int id, Model model){
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            model.addAttribute("order", order);

            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrder(order);
            model.addAttribute("orderDetails", orderDetails);
        }
        return "admin/orders/detail";
    }
}
