package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.CartCourseService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("cart")
public class CartController {
    private final UserService userService;
    private final CartCourseService cartCourseService;

    public CartController(UserService userService, CartCourseService cartCourseService) {
        this.userService = userService;
        this.cartCourseService = cartCourseService;
    }

    @GetMapping("/getAllHeader")
    public String getAllCourseInCartHeader(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(user);
                model.addAttribute("cartCourses", cartCourses);
            }
            return "home/carts/cartHeader";
        }
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String getAllCourseInCart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(user);

                AtomicReference<Double> total = new AtomicReference<>((double) 0);
                cartCourses.forEach(cartCourse -> {
                    if(cartCourse.getCourse().getSalePrice()==0)
                        total.updateAndGet(v -> v + cartCourse.getCourse().getPrice());
                    else
                        total.updateAndGet(v -> v + cartCourse.getCourse().getSalePrice());
                });

                model.addAttribute("total", total);

                model.addAttribute("cartCourses", cartCourses);
            }
            return "home/carts/cart";
        }
        return "redirect:/login";
    }

    @GetMapping("deleteFromCart/{id}")
    public String deleteFromCart(@PathVariable int id){
        cartCourseService.deleteById(id);
        return "redirect:/cart/list";
    }
}
