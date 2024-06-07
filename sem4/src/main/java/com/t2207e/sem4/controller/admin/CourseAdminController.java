package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/course")
@RequiredArgsConstructor
public class CourseAdminController {
    private final CourseService courseService;
    @GetMapping("list")
    public String course(Model model) {
        List<Course> courses = courseService.getAllCourse();
        model.addAttribute("courses", courses);
        return "admin/course/index";
    }
    @GetMapping("add")
    public String add(Model model){
        Course courses = new Course();
        model.addAttribute("courses", courses);
        return "admin/course/add";
    }
    @PostMapping("add")
    public String add(@Valid @ModelAttribute("courses") Course course, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/course/add";
        }

        courseService.add(course);
        return "redirect:/admin/course/list";
    }

}
