package com.t2207e.sem4.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roleTeacher")
public class DetailTeacherController {
    @GetMapping("detail")
    public String detail(){
        return "teacher/index";
    }
}
