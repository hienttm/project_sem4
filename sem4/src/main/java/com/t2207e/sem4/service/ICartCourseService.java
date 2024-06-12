package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.User;

import java.util.List;
import java.util.Optional;

public interface ICartCourseService {
    List<CartCourse> getAllCartCourse();
    Optional<CartCourse> getCartCourseById(Integer id);
    void add(CartCourse cartCourse);
    void deleteById(Integer id);
    List<CartCourse> getCartCoursesByUser(User user);

    void deleteAll();
}
