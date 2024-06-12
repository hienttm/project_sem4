package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> getReviewsByCourse_CourseId(int courseId);
}
