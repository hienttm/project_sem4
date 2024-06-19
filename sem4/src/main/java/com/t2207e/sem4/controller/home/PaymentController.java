package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class PaymentController {
    private final VNPAYService vnPayService;
    private final UserService userService;
    private final CartCourseService cartCourseService;
    private final PaymentMethodService paymentMethodService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public PaymentController(VNPAYService vnPayService, UserService userService, CartCourseService cartCourseService, PaymentMethodService paymentMethodService, OrderService orderService, OrderDetailService orderDetailService) {
        this.vnPayService = vnPayService;
        this.userService = userService;
        this.cartCourseService = cartCourseService;
        this.paymentMethodService = paymentMethodService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/payment")
    public String payment(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(user);

                AtomicReference<Integer> total = new AtomicReference<>(0);
                cartCourses.forEach(cartCourse -> {
                    total.updateAndGet(v -> v + (int) cartCourse.getCourse().getPrice());
                });

                Order order =new Order();
                order.setStatus(2);
                order.setPaymentMethod(paymentMethodService.getPaymentMethodById(2).get());
                order.setUser(userService.getUserByUsername(authentication.getName()).get());

                orderService.add(order);


                model.addAttribute("order", order);
                model.addAttribute("total", total);
                model.addAttribute("cartCourses", cartCourses);
            }
            return "home/orders/createOrder";
        }
        return "redirect:/login";
    }

    // Chuyển hướng người dùng đến cổng thanh toán VNPAY
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("orderInfo") int orderId,
                              HttpServletRequest request){
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && orderOptional.isPresent()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(userOptional.get());
            cartCourses.forEach(cartCourse -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderOptional.get());
                orderDetail.setCourse(cartCourse.getCourse());
                orderDetail.setPrice(cartCourse.getCourse().getPrice());
                orderDetailService.add(orderDetail);
            });

            cartCourseService.deleteAll();
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal*25000, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        if(paymentStatus == 1){
            Optional<Order> orderOptional = orderService.getOrderById(Integer.parseInt(orderInfo));
            if(orderOptional.isPresent()){
                Order order = orderOptional.get();
                order.setStatus(1);
                order.setPaymentCode(transactionId);
                orderService.add(order);
            }
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        model.addAttribute("error", "Lỗi thanh toán!!!!");

        String s = paymentStatus == 1 ? "home/orders/orderSuccess" : "home/orders/createOrder";
        return s;
    }
}
