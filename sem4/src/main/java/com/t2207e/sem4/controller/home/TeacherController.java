package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.service.ITeacherRoleService;
import com.t2207e.sem4.service.RoleService;
import com.t2207e.sem4.service.TeacherRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class TeacherController {
    private final TeacherRoleService teacherRoleService;
    private final RoleService roleService;

    public TeacherController(TeacherRoleService teacherRoleService, RoleService roleService) {
        this.teacherRoleService = teacherRoleService;
        this.roleService = roleService;
    }

    @GetMapping("teacher")
    public String teacher(){
        return "home/teacher/index";
    }

    @GetMapping("list")
    public String List(Model model){
        List<UserRole> ur = teacherRoleService.getAllTeacher(roleService.getRoleByRoleName("ROLE_TEACHER").get());
        model.addAttribute("ur", ur);
        return "home/teacher/index";
    }
}
