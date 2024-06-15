package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AccountAdminController {
    private final UserService userService;

    public AccountAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String GetList(Model model){
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/accounts/index";
    }
}
