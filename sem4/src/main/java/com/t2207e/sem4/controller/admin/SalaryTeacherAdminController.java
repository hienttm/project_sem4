package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.dto.TeacherSalaryDTO;
import com.t2207e.sem4.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/salary")
public class SalaryTeacherAdminController {
    private final UserService userService;

    public SalaryTeacherAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("list")
    public String list(Model model){
        List<TeacherSalaryDTO> teacherSalaryDTOs = userService.GetAllSalaryTeacher();
        model.addAttribute("teacherSalaryDTOs", teacherSalaryDTOs);
        return "admin/salaries/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id){
        return "admin/salaries/detail";
    }
}
