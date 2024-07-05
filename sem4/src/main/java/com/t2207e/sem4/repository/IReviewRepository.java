package com.t2207e.sem4.repository;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> getReviewsByCourse_CourseIdOrderByCreateAtDesc(int courseId);
    List<Review> getReviewsByUserAndCourse(User user, Course course);
    List<Review> getReviewsByFeaturedAndStatus(Integer featured, Integer Status);
}
