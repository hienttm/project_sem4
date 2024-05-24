package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.CourseTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CourseService courseService;
    private final CourseTypeService courseTypeService;

    public HomeController(CourseService courseService, CourseTypeService courseTypeService) {
        this.courseService = courseService;
        this.courseTypeService = courseTypeService;
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
}
