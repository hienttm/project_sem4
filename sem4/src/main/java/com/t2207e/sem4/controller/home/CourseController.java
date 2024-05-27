package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/list/{page}")
    public String List(@RequestParam(value = "searchInput", required = false) String searchInput, @PathVariable Integer page, Model model){
        model.addAttribute("searchInput", searchInput);
        String searchName = "";
        if (searchInput != null) {
            searchName = searchInput;
        }

        //Total Page
        Integer totalCourse = courseService.countCoursesByCourseNameContainingAndStatus(searchName, 1);
        double totalPage = Math.ceil((double) totalCourse / 6);

        model.addAttribute("totalPage", (int) totalPage);

        //Get List
        model.addAttribute("page", page);
        List<CourseDTO> courseDTOs = courseService.GetAllCourseProcedurePaging(searchName, page, 6);
        model.addAttribute("courseDTOs", courseDTOs);
        return "home/courses/list";
    }

    @GetMapping("/detail/{id}")
    public String Detail(@PathVariable Integer id, Model model){

        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            model.addAttribute("course", course);
            return "home/courses/detail";
        }
        else {
            return "redirect:course/list/1";
        }
    }
}
