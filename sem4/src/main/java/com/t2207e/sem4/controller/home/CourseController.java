package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.ChapterService;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.CourseTypeService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final ChapterService chapterService;
    private final UserService userService;
    private final CourseTypeService courseTypeService;


    public CourseController(CourseService courseService, ChapterService chapterService, UserService userService, CourseTypeService courseTypeService) {
        this.courseService = courseService;
        this.chapterService = chapterService;
        this.userService = userService;
        this.courseTypeService = courseTypeService;
    }

    @GetMapping("/list/{page}")
    public String List(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "course_type_name", required = false) String course_type_name, @PathVariable Integer page, Model model){
        model.addAttribute("searchInput", searchInput);
        model.addAttribute("course_type_name", course_type_name);

        String searchName = "";
        if (searchInput != null) {
            searchName = searchInput;
        }

        //Total Page
        Integer totalCourse = courseService.countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(searchName, 1, (course_type_name == null?"":course_type_name));
        double totalPage = Math.ceil((double) totalCourse / 6);

        model.addAttribute("totalPage", (int) totalPage);

        //Get List
        model.addAttribute("page", page);
        List<CourseDTO> courseDTOs = courseService.GetAllCourseProcedurePaging(searchName, page, 6, (course_type_name == null?"":course_type_name));
        model.addAttribute("courseDTOs", courseDTOs);
        return "home/courses/list";
    }

    @GetMapping("/detail/{id}")
    public String Detail(@PathVariable Integer id, Model model){

        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            model.addAttribute("course", course);

            List<Course> coursesHaveSameName = courseService.getCoursesByUser(course.getUser());
            model.addAttribute("coursesHaveSameName", coursesHaveSameName);

            List<Chapter> chapters = chapterService.getChaptersByCourse(course);
            model.addAttribute("chapters", chapters);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                Optional<User> userOptional = userService.getUserByUsername(username);
                if(userOptional.isPresent()){
                    User user = userOptional.get();
                    List<OrderDetailByUserDTO> orderDetailByUserDTOs = courseService.GetOrderDetailByUserIdProcedure(user.getUserId());

                    boolean checkBuy = orderDetailByUserDTOs.stream().anyMatch(dto -> dto.getCourseId() == course.getCourseId());

                    model.addAttribute("checkBuy", checkBuy);
                }
            }

            return "home/courses/detail";
        }
        return "redirect:course/list/1";
    }
}
