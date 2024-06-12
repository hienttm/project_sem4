package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Review;

import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<Review> getAllReview();
    Optional<Review> getReviewById(Integer id);
    void add(Review review);
    void deleteById(Integer id);
    List<Review> getReviewsByCourseId(int courseId);
}
