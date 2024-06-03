package com.t2207e.sem4.controller.home;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home/account")
public class AccountController {
    @GetMapping("detail")
    public String detail(Model model) {
        return "/home/users/account/detail";
    }

}
