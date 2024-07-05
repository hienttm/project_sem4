package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/review")
public class ReviewAdminController {
    private final ReviewService reviewService;

    public ReviewAdminController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("list")
    public String list(Model model){
        List<Review> reviews = reviewService.getAllReview();
        model.addAttribute("reviews", reviews);
        return "admin/reviews/index";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<Review> reviewOptional = reviewService.getReviewById(id);
        if(reviewOptional.isPresent()){
            Review review = reviewOptional.get();
            if(review.getStatus() == 1){
                review.setStatus(2);
            }
            else {
                review.setStatus(1);
            }
            review.setCreateAt(new Date(System.currentTimeMillis()));
            reviewService.add(review);
        }
        return "redirect:/admin/review/list";
    }

    @GetMapping("featured/{id}")
    public String featured(@PathVariable int id){
        Optional<Review> reviewOptional = reviewService.getReviewById(id);
        if(reviewOptional.isPresent()){
            Review review = reviewOptional.get();
            if(review.getFeatured() == 1){
                review.setFeatured(2);
            }
            else {
                review.setFeatured(1);
            }
            reviewService.add(review);
        }
        return "redirect:/admin/review/list";
    }
}
