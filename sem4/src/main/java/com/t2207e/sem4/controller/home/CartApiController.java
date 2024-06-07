package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.CartCourseService;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("api/cart")
public class CartApiController {
    private final UserService userService;
    private final CartCourseService cartCourseService;
    private final CourseService courseService;


    public CartApiController(UserService userService, CartCourseService cartCourseService, CourseService courseService) {
        this.userService = userService;
        this.cartCourseService = cartCourseService;
        this.courseService = courseService;
    }

    @GetMapping("getCount")
    public Integer getCount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                List<CartCourse> cartCourses = cartCourseService.getCartCoursesByUser(user);
                return cartCourses.size();
            }
            return 0;
        }
        return 0;
    }

    @GetMapping("addToCart/{id}")
    public String addToCart(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            Optional<Course> courseOptional = courseService.getCourseById(id);
            if(userOptional.isPresent() && courseOptional.isPresent()){
                User user = userOptional.get();
                Course course = courseOptional.get();

                AtomicReference<Boolean> check = new AtomicReference<>(false);

                List<CartCourse> cartCourses = cartCourseService.getAllCartCourse();
                cartCourses.stream().filter(cc -> cc.getCourse() == course).forEach(cc -> {
                    check.set(true);
                });

                if(!check.get()){
                    CartCourse cartCourse = new CartCourse();
                    cartCourse.setCourse(course);
                    cartCourse.setUser(user);
                    cartCourseService.add(cartCourse);
                }
                return "Success!";
            }
            return "User not found";
        }
        return "Please Login and try again!";
    }
}
