package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
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
    private final EventService eventService;

    public PaymentController(VNPAYService vnPayService, UserService userService, CartCourseService cartCourseService, PaymentMethodService paymentMethodService, OrderService orderService, OrderDetailService orderDetailService, EventService eventService) {
        this.vnPayService = vnPayService;
        this.userService = userService;
        this.cartCourseService = cartCourseService;
        this.paymentMethodService = paymentMethodService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.eventService = eventService;
    }

    @GetMapping("/payment")
    public String payment(Model model, @RequestParam(required = false) Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(user);

                AtomicReference<Integer> total = new AtomicReference<>(0);
                cartCourses.forEach(cartCourse -> {
                    if(cartCourse.getCourse().getSalePrice()==0)
                        total.updateAndGet(v -> v + (int) cartCourse.getCourse().getPrice());
                    else
                        total.updateAndGet(v -> v + (int) cartCourse.getCourse().getSalePrice());
                });
                Order order =new Order();
                Optional<PaymentMethod> paymentMethodOptional = paymentMethodService.getPaymentMethodById(2);
                if (id==null && paymentMethodOptional.isPresent()) {
                    order.setStatus(2);
                    order.setPaymentMethod(paymentMethodOptional.get());
                    order.setUser(userService.getUserByUsername(authentication.getName()).get());

                    orderService.add(order);
                }
                else {
                    Optional<Order> orderOptional = orderService.getOrderById(id);
                    if(orderOptional.isPresent()){
                        order = orderOptional.get();
                        model.addAttribute("validateCoupon", "Coupon is error or has been used");
                    }
                    else {
                        return "redirect:/cart/list";
                    }
                }


                List<Event> events = eventService.getEventsByAreaAndStatus(1, 1);

                model.addAttribute("events", events);
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
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("orderInfo") int orderId,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "coupon", required = false) String coupon,
                              HttpServletRequest request){
        Optional<Order> orderOptional = orderService.getOrderById(orderId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && orderOptional.isPresent()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(userOptional.get());

            if(!coupon.isEmpty()){
                Optional<Event> eventOptional = eventService.getEventByCode(coupon);

                if(eventOptional.isPresent()){
                    Event event = eventOptional.get();

                    Optional<Order> orderCheckOptional = orderService.getOrderByUserAndEventAndStatus(userOptional.get(), event, 1);
                    if(orderCheckOptional.isPresent()){
                        return "redirect:/payment?id=" + orderInfo;
                    }

                    LocalDateTime now = LocalDateTime.now();

                    LocalDateTime eventEndAt = Instant.ofEpochMilli(event.getEndAt().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    LocalDateTime eventStartAt = Instant.ofEpochMilli(event.getStartAt().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();

                    if(now.isBefore(eventEndAt) && eventStartAt.isBefore(now) && event.getQuantity()>0 && orderTotal > event.getMinPrice()){
                        if(Objects.equals(event.getCategorySale().getCategorySaleName(), "Ratio")){
                            int saleNumber = (int) (orderTotal * (event.getSale()/100));
                            if(saleNumber>event.getMaxSale()){
                                orderTotal = orderTotal - (int)event.getMaxSale();
                            }
                            else {
                                orderTotal = orderTotal - saleNumber;
                            }
                        }
                        else {
                            if(event.getSale()>orderTotal){
                                orderTotal = 0;
                            }
                            else {
                                orderTotal = (int) (orderTotal - event.getSale());
                            }
                        }
                        orderOptional.get().setEvent(event);
                    }
                    else {
                        return "redirect:/payment?id=" + orderInfo;
                    }
                }
                else {
                    return "redirect:/payment?id=" + orderInfo;
                }
            }

            orderOptional.get().setDescription(description);
            orderOptional.get().setTotal((double)orderTotal);
            orderService.add(orderOptional.get());
            cartCourses.forEach(cartCourse -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderOptional.get());
                orderDetail.setCourse(cartCourse.getCourse());
                if(cartCourse.getCourse().getSalePrice()==0)
                    orderDetail.setPrice(cartCourse.getCourse().getPrice());
                else
                    orderDetail.setPrice(cartCourse.getCourse().getSalePrice());
                orderDetailService.add(orderDetail);
            });
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

                if(order.getEvent()!=null){
                    Event event = order.getEvent();
                    event.setQuantity(event.getQuantity()-1);
                    eventService.add(event);
                }


                cartCourseService.deleteAll();
            }
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        model.addAttribute("error", "Lỗi thanh toán!!!!");

        String s = paymentStatus == 1 ? "home/orders/orderSuccess" : "redirect:/cart/list";
        return s;
    }
}
