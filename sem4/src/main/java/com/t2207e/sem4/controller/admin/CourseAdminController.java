package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.ChapterService;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.ExamService;
import com.t2207e.sem4.service.StatusCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/course")
@RequiredArgsConstructor
public class CourseAdminController {
    private final CourseService courseService;
    private final StatusCourseService statusCourseService;
    private final ChapterService chapterService;
    private final ExamService examService;
    @GetMapping("list")
    public String courses(Model model) {
        List<Object> courses = courseService.getAllCoursesSelected();
        model.addAttribute("courses", courses);
        return "admin/course/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable int id, Model model){
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            model.addAttribute("course", course);

            List<StatusCourse> statusCourses = statusCourseService.getStatusCoursesByCourse_CourseId(course.getCourseId());
            model.addAttribute("statusCourses", statusCourses);

            List<Chapter> chapters = chapterService.getChaptersByCourse(course);
            model.addAttribute("chapters", chapters);

            List<Exam> exams = examService.getExamsByCourse(course);
            model.addAttribute("exams", exams);
        }
        return "admin/course/detail";
    }

    @PostMapping("changeStatusCourse/{id}")
    public String changeStatusCourse(@PathVariable int id, @RequestParam int sta, @RequestParam String description){
        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent() && sta>=1 && sta<=4){
            Course course = courseOptional.get();
            if(sta!=course.getStatus()){
                course.setStatus(sta);
                courseService.add(course);

                StatusCourse statusCourse = new StatusCourse();
                statusCourse.setDescription(description);
                statusCourse.setUpdateAt(new Date(System.currentTimeMillis()));
                statusCourse.setCourse(course);
                if (sta == 1){
                    statusCourse.setStatusName("APPROVED");
                    statusCourse.setColor("success");
                }
                else if(sta == 2){
                    statusCourse.setStatusName("NOT APPROVED");
                    statusCourse.setColor("primary");
                }
                else if(sta == 3){
                    statusCourse.setStatusName("ASK TO EDIT");
                    statusCourse.setColor("warning");
                }
                else{
                    statusCourse.setStatusName("REJECTED");
                    statusCourse.setColor("danger");
                }

                statusCourseService.add(statusCourse);
            }
        }
        return "redirect:/admin/course/list";
    }

    @GetMapping("changeStatusChapter/{id}")
    public String changeStatusChapter(@PathVariable int id){
        Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
        if (chapterOptional.isPresent()){
            Chapter chapter = chapterOptional.get();
            if(chapter.getStatus() == 1){
                chapter.setStatus(2);
            }
            else {
                chapter.setStatus(1);
            }
            chapterService.add(chapter);
            return "redirect:/admin/course/detail/" + chapter.getCourse().getCourseId();
        }
        return "redirect:/admin/course/list";
    }

    @GetMapping("changeStatusExam/{id}")
    public String changeStatusExam(@PathVariable int id){
        Optional<Exam> examOptional = examService.getExamById(id);
        if (examOptional.isPresent()){
            Exam exam = examOptional.get();
            if(exam.getStatus() == 1){
                exam.setStatus(2);
            }
            else {
                exam.setStatus(1);
            }
            examService.add(exam);
            return "redirect:/admin/course/detail/" + exam.getCourse().getCourseId();
        }
        return "redirect:/admin/course/list";
    }
}
