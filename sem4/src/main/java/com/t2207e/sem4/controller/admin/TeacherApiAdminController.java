package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.OrderDetailService;
import com.t2207e.sem4.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("admin/api/teacher")
public class TeacherApiAdminController {
    private final OrderDetailService orderDetailService;
    private final UserService userService;

    public TeacherApiAdminController(OrderDetailService orderDetailService, UserService userService) {
        this.orderDetailService = orderDetailService;
        this.userService = userService;
    }

    @GetMapping("getOrderDetails/{id}")
    public List<Object[]> getOrderDetails(
            @PathVariable Integer id,
            @RequestParam("startAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startAt,
            @RequestParam("endAt") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endAt) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Object[]> orderDetails = orderDetailService.getOrderDetailsByUserAndDate(user.getUserId(), startAt, endAt);
            return orderDetails;
        }
        return Collections.emptyList();
    }
}
