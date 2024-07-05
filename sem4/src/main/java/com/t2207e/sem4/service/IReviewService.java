package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.entity.User;

import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<Review> getAllReview();
    Optional<Review> getReviewById(Integer id);
    void add(Review review);
    void deleteById(Integer id);
    List<Review> getReviewsByCourseId(int courseId);
    List<Review> getReviewsByUserAndCourse(User user, Course course);
    List<Review> getReviewsByFeatured(Integer featured);
}
