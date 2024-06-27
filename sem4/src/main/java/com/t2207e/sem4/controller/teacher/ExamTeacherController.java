package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.ExamService;
import com.t2207e.sem4.service.UserService;
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
import java.util.Optional;

@Controller
@RequestMapping("roleTeacher/exam")
public class ExamTeacherController {
    private final ExamService examService;
    private final CourseService courseService;
    private final UserService userService;

    public ExamTeacherController(ExamService examService, CourseService courseService, UserService userService) {
        this.examService = examService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/add/{courseId}")
    public String addExam(@PathVariable int courseId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (authentication != null && authentication.isAuthenticated() && courseOptional.isPresent()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Course course = courseOptional.get();

            if(course.getUser() == user){
                Exam exam = new Exam();
                model.addAttribute("exam", exam);
                return "teacher/exams/add";
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }

    @PostMapping("add/{courseId}")
    public String addExamPost(@Valid @ModelAttribute Exam exam, BindingResult bindingResult , @PathVariable int courseId, Model model) {
        if(bindingResult.hasErrors()){
            return "teacher/exams/add";
        }
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if(courseOptional.isPresent()){
            exam.setCourse(courseOptional.get());
            examService.add(exam);
            return "redirect:/roleTeacher/exam/edit/" + exam.getExamId();
        }
        return "redirect:/roleTeacher/course/list";
    }

    @GetMapping("edit/{id}")
    public String editChapter(@PathVariable int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Optional<Exam> examOptional = examService.getExamById(id);
            if (examOptional.isPresent()){
                Exam exam = examOptional.get();
                if(user == exam.getCourse().getUser()){
                    model.addAttribute("exam", exam);
                    return "teacher/exams/edit";
                }
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }

    @PostMapping("edit")
    public String editChapterPost(@Valid @ModelAttribute Exam exam, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult);
            System.out.println(bindingResult);
            System.out.println(bindingResult);
            return "teacher/exams/edit";
        }
        examService.add(exam);
        return "redirect:/roleTeacher/exam/edit/" + exam.getExamId();
    }
}
