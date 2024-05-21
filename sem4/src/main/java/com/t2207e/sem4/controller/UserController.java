package com.t2207e.sem4.controller;

import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.service.RoleService;
import com.t2207e.sem4.service.UserRoleService;
import com.t2207e.sem4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRoleService userRoleService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("login")
    public String Login(){
        return "loginPage";
    }

    @GetMapping("register")
    public String RegisterForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("register")
    public String Register(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, @RequestParam String rePassword){

        System.out.println("SAAAA");

        //Validated
        if(bindingResult.hasErrors()){
            System.out.println("BBBB");
            return "register";
        }

        //Check USERNAME
        Optional<User> userCheckOptional = userService.getUserByUsername(user.getUsername());
        if(userCheckOptional.isPresent()){
            String exception = "Username has been existed";
            model.addAttribute("exception", exception);
            return "register";
        }

        //Check PHONE
        Optional<User> userPhoneOptional = userService.getUserByPhone(user.getPhoneNumber());
        if(userPhoneOptional.isPresent()){
            String exception = "Phone Number has been existed";
            model.addAttribute("exceptionP", exception);
            return "register";
        }

        //Check rePassword
        if(!Objects.equals(rePassword, user.getPassword())){
            String exception = "Password and Re-Password are different!";
            model.addAttribute("exceptionRP", exception);
            return "register";
        }

        //Add USER
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.add(user);

        //Add USER_ROLE
        UserRole userRole = new UserRole();
        userRole.setUser(userService.getUserByUsername(user.getUsername()).get());
        userRole.setRole(roleService.getRoleByRoleName("ROLE_USER").get());
        userRoleService.add(userRole);

        return "redirect:/login";
    }

}
