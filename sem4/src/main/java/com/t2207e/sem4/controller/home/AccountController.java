package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.HelperService;
import com.t2207e.sem4.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("home/account")

public class AccountController {
    private final UserService userService;
    private final HelperService helperService;
    public AccountController(UserService userService, HelperService helperService) {
        this.userService = userService;
        this.helperService = helperService;
    }
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("detail")
    public String detail(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> user=userService.getUserByUsername(username);
            model.addAttribute("user", user.get());
        }
        return "/home/users/accountDetail";
    }
    @PostMapping("updateAccount")
    public String update(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult, @RequestBody(required = false) MultipartFile fileImage) throws IOException {
        if(bindingResult.hasErrors()){
            return "/home/users/accountDetail";
        }
        user.setUpdateAt(new Date(System.currentTimeMillis()));
        if(fileImage != null && !fileImage.isEmpty()) {
            user.setImage(helperService.ConvertFromImageToBase64String(fileImage));
        }
        userService.add(user);
        return "/home/users/accountDetail";
    }
}
