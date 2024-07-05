package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService{
    private final IReviewRepository reviewRepository;

    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public void add(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void deleteById(Integer id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReviewsByCourseId(int courseId) {
        return reviewRepository.getReviewsByCourse_CourseIdOrderByCreateAtDesc(courseId);
    }

    @Override
    public List<Review> getReviewsByUserAndCourse(User user, Course course) {
        return reviewRepository.getReviewsByUserAndCourse(user, course);
    }

    @Override
    public List<Review> getReviewsByFeatured(Integer featured) {
        return reviewRepository.getReviewsByFeaturedAndStatus(featured, 1);
    }
}
