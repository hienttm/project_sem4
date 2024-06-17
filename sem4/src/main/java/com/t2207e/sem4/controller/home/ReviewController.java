package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.ReviewService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, CourseService courseService, UserService userService) {
        this.reviewService = reviewService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/uploadReview/{id}")
    public String UploadReview(@PathVariable Integer id, @RequestParam(required = false) Integer rating, @RequestParam(required = false) String comment, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            Optional<Course> courseOptional = courseService.getCourseById(id);

            if (rating != null && rating >= 1 && rating <= 5 && courseOptional.isPresent() && userOptional.isPresent()) {

                List<Review> reviews = reviewService.getReviewsByUserAndCourse(userOptional.get(), courseOptional.get());
                if(reviews.isEmpty()){
                    Review review = new Review();
                    review.setStarNumber(rating);
                    review.setComment(comment);
                    review.setCourse(courseOptional.get());
                    review.setUser(userOptional.get());
                    reviewService.add(review);

                    redirectAttributes.addFlashAttribute("statusMessage", "success");
                    redirectAttributes.addFlashAttribute("message", "Review successfully added.");
                }
                else {
                    redirectAttributes.addFlashAttribute("statusMessage", "error");
                    redirectAttributes.addFlashAttribute("message", "The product has been previously rated by user.");
                }
            }
            else {
                redirectAttributes.addFlashAttribute("statusMessage", "error");
                redirectAttributes.addFlashAttribute("message", "Error Rating.");
            }
        }
        else {
            redirectAttributes.addFlashAttribute("statusMessage", "error");
            redirectAttributes.addFlashAttribute("message", "Error Authentication.");
        }
        return "redirect:/course/detail/" + id;
    }
}
