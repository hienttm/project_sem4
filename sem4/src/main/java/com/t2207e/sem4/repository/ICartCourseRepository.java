package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartCourseRepository extends JpaRepository<CartCourse, Integer> {
    List<CartCourse> getCartCoursesByUser(User user);
}
