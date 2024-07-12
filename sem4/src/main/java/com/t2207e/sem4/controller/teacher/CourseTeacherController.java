package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("roleTeacher/course")
public class CourseTeacherController {
    private final TeacherRegisterService teacherRegisterService;
    private final UserService userService;
    private final CourseService courseService;
    private final CourseTypeService courseTypeService;
    private final CategoryService categoryService;
    private final HelperService helperService;
    private final StatusCourseService statusCourseService;
    private final ChapterService chapterService;
    private final ExamService examService;
    private final OrderDetailService orderDetailService;

    public CourseTeacherController(TeacherRegisterService teacherRegisterService, UserService userService, CourseService courseService, CourseTypeService courseTypeService, CategoryService categoryService, HelperService helperService, StatusCourseService statusCourseService, ChapterService chapterService, ExamService examService, OrderDetailService orderDetailService) {
        this.teacherRegisterService = teacherRegisterService;
        this.userService = userService;
        this.courseService = courseService;
        this.courseTypeService = courseTypeService;
        this.categoryService = categoryService;
        this.helperService = helperService;
        this.statusCourseService = statusCourseService;
        this.chapterService = chapterService;
        this.examService = examService;
        this.orderDetailService = orderDetailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("list")
    public String courses(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            if(userOptional.isPresent()){
                List<Object> courses = courseService.getCoursesByUserSelected(userOptional.get());
                model.addAttribute("courses", courses);

                return "teacher/courses/index";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("add")
    public String add(Model model){
        Course course = new Course();
        model.addAttribute("course", course);
        List<CourseType> courseTypes = courseTypeService.getAllCourseType();
        model.addAttribute("courseTypes", courseTypes);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "teacher/courses/add";
    }

    @PostMapping("add")
    public String addCourse(@Valid @ModelAttribute Course course, BindingResult bindingResult, @RequestBody(required = false) MultipartFile fileImage, @RequestBody(required = false) MultipartFile fileVideo, Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            if(bindingResult.hasErrors()){
                List<CourseType> courseTypes = courseTypeService.getAllCourseType();
                model.addAttribute("courseTypes", courseTypes);
                List<Category> categories = categoryService.getAllCategory();
                model.addAttribute("categories", categories);
                return "teacher/courses/add";
            }

            course.setUser(user);
            course.setCensor(user);

            if(!fileImage.isEmpty()){
                course.setImage(helperService.ConvertFromImageToBase64String(fileImage));
            }

            if(!fileVideo.isEmpty()){
                course.setCourseVideo(helperService.ConvertFromVideoToBase64String(fileVideo));
            }

            courseService.add(course);
            StatusCourse statusCourse = new StatusCourse();
            statusCourse.setCourse(course);

            statusCourseService.add(statusCourse);
            return "redirect:/roleTeacher/course/edit/" + course.getCourseId();
        }
        return "redirect:/login";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();

            Optional<Course> courseOptional = courseService.getCourseById(id);
            if(courseOptional.isPresent()){
                if(user == courseOptional.get().getUser()){
                    Course course = courseOptional.get();
                    model.addAttribute("course", course);

                    List<StatusCourse> statusCourses = statusCourseService.getStatusCoursesByCourse_CourseId(course.getCourseId());
                    model.addAttribute("statusCourses", statusCourses);

                    List<Chapter> chapters = chapterService.getChaptersByCourseAndStatus(course, 1);
                    model.addAttribute("chapters", chapters);

                    List<Exam> exams = examService.getExamsByCourseAndStatus(course, 1);
                    model.addAttribute("exams", exams);

                    List<CourseType> courseTypes = courseTypeService.getAllCourseType();
                    model.addAttribute("courseTypes", courseTypes);

                    List<Category> categories = categoryService.getAllCategory();
                    model.addAttribute("categories", categories);

                    List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByCourse_CourseId(course.getCourseId());
                    model.addAttribute("orderDetails", orderDetails);

                    return "teacher/courses/edit";
                }
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }

    @PostMapping("edit")
    public String editCourse(@Valid @ModelAttribute Course course, BindingResult bindingResult, @RequestBody(required = false) MultipartFile fileImage, @RequestBody(required = false) MultipartFile fileVideo, Model model) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            if(bindingResult.hasErrors()){
                List<StatusCourse> statusCourses = statusCourseService.getStatusCoursesByCourse_CourseId(course.getCourseId());
                model.addAttribute("statusCourses", statusCourses);
                List<Chapter> chapters = chapterService.getChaptersByCourseAndStatus(course, 1);
                model.addAttribute("chapters", chapters);
                List<Exam> exams = examService.getExamsByCourseAndStatus(course, 1);
                model.addAttribute("exams", exams);
                List<CourseType> courseTypes = courseTypeService.getAllCourseType();
                model.addAttribute("courseTypes", courseTypes);
                List<Category> categories = categoryService.getAllCategory();
                model.addAttribute("categories", categories);
                return "teacher/courses/edit";
            }

            course.setUser(user);
            course.setCensor(user);

            if(!fileImage.isEmpty()){
                course.setImage(helperService.ConvertFromImageToBase64String(fileImage));
            }

            if(!fileVideo.isEmpty()){
                course.setCourseVideo(helperService.ConvertFromVideoToBase64String(fileVideo));
            }

            courseService.add(course);
            return "redirect:/roleTeacher/course/edit/" + course.getCourseId();
        }
        return "redirect:/login";
    }

    @PostMapping("submitCourse/{id}")
    public String submitCourse(@PathVariable int id, @RequestParam(required = false) String descriptionStatus){
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            course.setStatus(2);
            courseService.add(course);

            StatusCourse statusCourse = new StatusCourse();
            statusCourse.setStatusName("NOT APPROVED");
            statusCourse.setColor("primary");
            statusCourse.setDescription(descriptionStatus);
            statusCourse.setUpdateAt(new Date(System.currentTimeMillis()));
            statusCourse.setCourse(course);
            statusCourseService.add(statusCourse);

            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/roleTeacher/course/list";
    }
}
