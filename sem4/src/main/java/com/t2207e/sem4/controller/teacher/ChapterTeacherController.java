package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.ChapterService;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.HelperService;
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
@RequestMapping("roleTeacher/chapter")
public class ChapterTeacherController {
    private final ChapterService chapterService;
    private final HelperService helperService;
    private final CourseService courseService;
    private final UserService userService;

    public ChapterTeacherController(ChapterService chapterService, HelperService helperService, CourseService courseService, UserService userService) {
        this.chapterService = chapterService;
        this.helperService = helperService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("add/{courseId}")
    public String addChapter(@PathVariable int courseId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (authentication != null && authentication.isAuthenticated() && courseOptional.isPresent()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Course course = courseOptional.get();

            if(course.getUser() == user){
                Chapter chapter = new Chapter();
                model.addAttribute("chapter", chapter);
                return "teacher/chapters/add";
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }

    @PostMapping("add/{courseId}")
    public String addChapterPost(@Valid @ModelAttribute Chapter chapter, BindingResult bindingResult ,@PathVariable int courseId, Model model, @RequestBody(required = false) MultipartFile fileVideo) throws IOException {
        if(bindingResult.hasErrors()){
            return "teacher/chapters/add";
        }
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if(courseOptional.isPresent()){
            if(!fileVideo.isEmpty()){
                chapter.setChapterVideo(helperService.ConvertFromVideoToBase64String(fileVideo));
            }
            chapter.setCourse(courseOptional.get());
            chapterService.add(chapter);
            return "redirect:/roleTeacher/course/edit/" + courseId;
        }
        return "redirect:/roleTeacher/course/list";
    }

    @GetMapping("edit/{id}")
    public String editChapter(@PathVariable int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
            if (chapterOptional.isPresent()){
                Chapter chapter = chapterOptional.get();
                if(user == chapter.getCourse().getUser()){
                    model.addAttribute("chapter", chapter);
                    return "teacher/chapters/edit";
                }
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }

    @PostMapping("edit")
    public String editChapterPost(@Valid @ModelAttribute Chapter chapter, BindingResult bindingResult, Model model, @RequestBody(required = false) MultipartFile fileVideo) throws IOException {
        if(bindingResult.hasErrors()){
            model.addAttribute("chapter", chapter);
            return "teacher/chapters/edit";
        }
        if(!fileVideo.isEmpty()){
            chapter.setChapterVideo(helperService.ConvertFromVideoToBase64String(fileVideo));
        }
        chapterService.add(chapter);
        return "redirect:/roleTeacher/course/edit/" + chapter.getCourse().getCourseId();
    }

    @GetMapping("delete/{id}")
    public String deleteChapter(@PathVariable int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
            if (chapterOptional.isPresent()){
                Chapter chapter = chapterOptional.get();
                if(user == chapter.getCourse().getUser()){
                    chapter.setStatus(2);
                    chapterService.add(chapter);
                    return "redirect:/roleTeacher/course/edit/" + chapter.getCourse().getCourseId();
                }
            }
            return "redirect:/roleTeacher/course/list";
        }
        return "redirect:/login";
    }
}
