package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.entity.Review;
import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CourseService courseService;
    private final CourseTypeService courseTypeService;
    private final EmailService emailService;
    private final ReviewService reviewService;
    private final TeacherRegisterService teacherRegisterService;

    public HomeController(CourseService courseService, CourseTypeService courseTypeService, EmailService emailService, ReviewService reviewService, TeacherRegisterService teacherRegisterService) {
        this.courseService = courseService;
        this.courseTypeService = courseTypeService;
        this.emailService = emailService;
        this.reviewService = reviewService;
        this.teacherRegisterService = teacherRegisterService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Course> courses = courseService.findTopCourses();
        model.addAttribute("courses", courses);

        List<CourseType> courseTypes = courseTypeService.getAllCourseType();
        model.addAttribute("courseTypes", courseTypes);

        List<Review> reviews = reviewService.getReviewsByFeatured(1);
        model.addAttribute("reviews", reviews);

        List<TeacherRegister> teacherRegisters = teacherRegisterService.listTeacherRegisters();
        model.addAttribute("teacherRegisters", teacherRegisters);
        return "home/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model){
        if(!Objects.equals(keyword, "")){
            List<CourseDTO> searchCourseDTOs = courseService.GetAllCourseProcedure(keyword);
            model.addAttribute("searchCourseDTOs", searchCourseDTOs);
        }
        return "home/search";
    }
    @GetMapping("contactus")
    public String contactus(Model model){
        return "home/contactus";
    }
    @PostMapping("sendcontactus")
    public String SendContactus( Model model,@RequestParam("name") String name,@RequestParam("email") String email, @RequestParam("message")String message){
        try{
            emailService.getMailNotiContactEmail(email,message,name);
            emailService.sendMailNotiContactUsStatus(email);
            model.addAttribute("message", "Send Message Success!");
            model.addAttribute("statusMessage", "success");
        }catch (Exception e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("statusMessage", "error");
        }
        return "home/contactus";
    }
}
