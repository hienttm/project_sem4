package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.service.CourseTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("courseType")
public class CourseTypeController {
    private final CourseTypeService courseTypeService;

    public CourseTypeController(CourseTypeService courseTypeService) {
        this.courseTypeService = courseTypeService;
    }

    @GetMapping("getAll")
    public String getAll(Model model){
        List<CourseType> courseTypes = courseTypeService.getAllCourseType();
        model.addAttribute("courseTypes", courseTypes);
        return "home/courseTypes/index";
    }
}
