package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.CourseTypeService;
import com.t2207e.sem4.service.EmailService;
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

    public HomeController(CourseService courseService, CourseTypeService courseTypeService, EmailService emailService) {
        this.courseService = courseService;
        this.courseTypeService = courseTypeService;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String home(Model model){
        List<CourseDTO> courseDTOs = courseService.GetAllCourseProcedure("");
        model.addAttribute("courseDTOs", courseDTOs);

        List<CourseType> courseTypes = courseTypeService.getAllCourseType();
        model.addAttribute("courseTypes", courseTypes);
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
       System.out.println(email);
       System.out.println(message);
       System.out.println(name);
       emailService.getMailNotiContactEmail(email,message,name);
       emailService.sendMailNotiContactUsStatus(email);
        return "home/contactus";
    }
}
