package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.HelperService;
import com.t2207e.sem4.service.OrderDetailService;
import com.t2207e.sem4.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("home/account")

public class AccountController {
    private final UserService userService;
    private final HelperService helperService;
    private final PasswordEncoder passwordEncoder;
    private final OrderDetailService orderDetailService;

    public AccountController(UserService userService, HelperService helperService, PasswordEncoder passwordEncoder, OrderDetailService orderDetailService) {
        this.userService = userService;
        this.helperService = helperService;
        this.passwordEncoder = passwordEncoder;
        this.orderDetailService = orderDetailService;
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

            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrder_User(user.get());
            model.addAttribute("orderDetails", orderDetails);
        }
        return "/home/users/accountDetail";
    }
    @PostMapping("updateAccount")
    public String update(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult, MultipartFile fileImage) {
        if(bindingResult.hasErrors()){
            return "/home/users/accountDetail";
        }
        if(fileImage != null && !fileImage.isEmpty()) {
            try {
                user.setImage(helperService.ConvertFromImageToBase64String(fileImage));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        user.setUpdateAt(new Date(System.currentTimeMillis()));
        userService.add(user);
        return "redirect:/home/account/detail";
    }
    @PostMapping("changePassword")
    public String changePassword(@RequestParam String oldpasswordInput, @RequestParam String newpasswordInput, @RequestParam String confirmpasswordInput, @RequestParam String username, Model model, RedirectAttributes redirectAttributes) {

        if(!confirmpasswordInput.equals(newpasswordInput)) {
            redirectAttributes.addFlashAttribute("message", "Password and Re-Password are different!");
            redirectAttributes.addFlashAttribute("statusMessage", "error");
            System.out.println("Password and Re-Password are different!");
            return "redirect:/home/account/detail";
        }
        if(newpasswordInput.length()<6) {
            redirectAttributes.addFlashAttribute("message", "New password should be at least 6 characters!");
            redirectAttributes.addFlashAttribute("statusMessage", "error");
            return "redirect:/home/account/detail";
        }
        User user=userService.getUserByUsername(username).get();

        if(!passwordEncoder.matches(oldpasswordInput,user.getPassword())) {
            redirectAttributes.addFlashAttribute("message", "Old password is wrong!");
            redirectAttributes.addFlashAttribute("statusMessage", "error");
            System.out.println("Old password is incorrect!");
            return "redirect:/home/account/detail";
        }else{
            String encodedNewpasswordInput =passwordEncoder.encode(newpasswordInput);
            user.setPassword(encodedNewpasswordInput);
            userService.add(user);
            redirectAttributes.addFlashAttribute("message", "New password has been changed!");
            redirectAttributes.addFlashAttribute("statusMessage", "success");
            System.out.println("Change password is success");
            return "redirect:/home/account/detail";
        }

    }
}
