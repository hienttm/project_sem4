package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherRoleService teacherRoleService;
    private final RoleService roleService;
    private final CourseService courseService;
    private final TeacherRegisterService teacherRegisterService;
    private final UserService userService;

    public TeacherController(TeacherRoleService teacherRoleService, RoleService roleService, CourseService courseService, TeacherRegisterService teacherRegisterService, UserService userService) {
        this.teacherRoleService = teacherRoleService;
        this.roleService = roleService;
        this.courseService = courseService;
        this.teacherRegisterService = teacherRegisterService;
        this.userService = userService;
    }

    @GetMapping("list")
    public String List(Model model){
        List<UserRole> ur = teacherRoleService.getAllTeacher(roleService.getRoleByRoleName("ROLE_TEACHER").get());
        model.addAttribute("ur", ur);
        return "home/teacher/index";
    }

    @GetMapping("/detail/{id}")
    public String Detail(@PathVariable Integer id, Model model){
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User teacher = userOptional.get();
            model.addAttribute("teacher", teacher);
            List<Course> getTeacherCourse = courseService.getCoursesByUser(teacher);
            model.addAttribute("teacher_course", getTeacherCourse);
            return "home/teacher/detail";
        } else {
            return "redirect:/teacher/list";
        }

    }
}
