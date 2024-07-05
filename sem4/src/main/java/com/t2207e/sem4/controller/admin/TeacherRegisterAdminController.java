package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.dto.TeacherSalaryDTO;
import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/teacherRegister")
public class TeacherRegisterAdminController {
    private final UserService userService;
    private final OrderDetailService orderDetailService;
    private final TeacherRegisterService teacherRegisterService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;

    public TeacherRegisterAdminController(UserService userService, OrderDetailService orderDetailService, TeacherRegisterService teacherRegisterService, RoleService roleService, UserRoleService userRoleService) {
        this.userService = userService;
        this.orderDetailService = orderDetailService;
        this.teacherRegisterService = teacherRegisterService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
    }

    @GetMapping("list")
    public String list(Model model){
        List<TeacherRegister> teacherRegisters = teacherRegisterService.listTeacherRegisters();
        model.addAttribute("teacherRegisters", teacherRegisters);
        return "admin/teachers/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable Integer id, Model model){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();

            Optional<TeacherRegister> teacherRegisterOptional = teacherRegisterService.getTeacherRegisterByUser_UserId(user.getUserId());
            if (teacherRegisterOptional.isPresent()){
                TeacherRegister teacherRegister = teacherRegisterOptional.get();
                model.addAttribute("teacherRegister", teacherRegister);
                Integer userId = teacherRegister.getUser().getUserId();
                model.addAttribute("userId", userId);
                return "admin/teachers/detail";
            }
            return "redirect:/admin/teacherRegister/list";
        }
        return "redirect:/admin/teacherRegister/list";
    }

    @GetMapping("status/{id}")
    public String changeStatus(@PathVariable Integer id){
        Optional<TeacherRegister> teacherRegisterOptional = teacherRegisterService.getTeacherRegisterById(id);
        Optional<Role> roleOptional = roleService.getRoleByRoleName("ROLE_TEACHER");
        if(teacherRegisterOptional.isPresent() && roleOptional.isPresent()){
            TeacherRegister teacherRegister = teacherRegisterOptional.get();
            User user = teacherRegister.getUser();
            Role role = roleOptional.get();
            if(teacherRegister.getStatus()==1){
                teacherRegister.setStatus(2);
                Optional<UserRole> userRoleOptional = userRoleService.getUserRoleByUserAndRole_RoleName(user, "ROLE_TEACHER");
                if (userRoleOptional.isPresent()){
                    userRoleService.deleteById(userRoleOptional.get().getId());
                }
            }
            else {
                teacherRegister.setStatus(1);
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                userRoleService.add(userRole);
            }
            teacherRegister.setUpdateAt(new Date(System.currentTimeMillis()));
            teacherRegisterService.save(teacherRegister);
        }
        return "redirect:/admin/teacherRegister/list";
    }
}
