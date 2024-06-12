package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.CartCourse;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.ICartCourseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
public class CartCourseService implements ICartCourseService{
    private final ICartCourseRepository cartCourseRepository;

    public CartCourseService(ICartCourseRepository cartCourseRepository) {
        this.cartCourseRepository = cartCourseRepository;
    }

    @Override
    public List<CartCourse> getAllCartCourse() {
        return cartCourseRepository.findAll();
    }

    @Override
    public Optional<CartCourse> getCartCourseById(Integer id) {
        return cartCourseRepository.findById(id);
    }

    @Override
    public void add(CartCourse cartCourse) {
        cartCourseRepository.save(cartCourse);
    }

    @Override
    public void deleteById(Integer id) {
        cartCourseRepository.deleteById(id);
    }

    @Override
    public List<CartCourse> getCartCoursesByUser(User user) {
        return cartCourseRepository.getCartCoursesByUser(user);
    }

    @Override
    public void deleteAll() {
        cartCourseRepository.deleteAll();
    }
}
