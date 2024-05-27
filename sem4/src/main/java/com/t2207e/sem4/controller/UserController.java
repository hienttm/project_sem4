package com.t2207e.sem4.controller;

import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.entity.Token;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.entity.UserRole;
import com.t2207e.sem4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final TeacherRegisterService teacherRegisterService;

    public UserController(UserService userService, UserRoleService userRoleService, RoleService roleService, PasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService, TeacherRegisterService teacherRegisterService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.teacherRegisterService = teacherRegisterService;
    }
    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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

//    forgotPassword
    @GetMapping("forgotPassword")
    public String ForgotPassword(){
        return "forgotPassword";
    }
    @PostMapping("checkExistMail")
    public String checkExitMail(@RequestParam("email") String email,Model model){

        Optional<User> userCheckOptional = userService.findFirstByEmail(email);
        if(userCheckOptional.isPresent()){
            //nếu tồn lại:tạo Token, gửi mail
            //tạo Token:
            String token= UUID.randomUUID().toString();
            Token token1 = new Token();
            token1.setToken(token);
            token1.setUser(userCheckOptional.get());
            tokenService.add(token1);

            //gửi mail:
            emailService.sendEmail(userCheckOptional.get().getEmail(), token);

            String message_checkmail="Hãy Click vào đường link được gửi vào Email để Reset lại mật khẩu";
            model.addAttribute("message_checkmail", message_checkmail);
        }else {
            //không tồn tại: nhập lại email
            String message_checkmail="Email không tồn lại. Vui lòng nhập lại Email";
            model.addAttribute("message_checkmail", message_checkmail);
        }
        return "/forgotPassword";
    }
    @GetMapping("resetPassworUrl/{token}")
    public String resetPassworUrl(@PathVariable String token, Model model){
        if (token==null || token.isEmpty()){
            //token rỗng

            String message_checkToken="Đường link không hợp lệ, quý khách vui lòng kiểm tra lại";
            model.addAttribute("message_checkToken",message_checkToken);
            return "redirect:/forgotPassword";
        }
        // kiểm tra token
        Optional<Token> checkToken=tokenService.findByToken(token);
        if(checkToken.isPresent()){
            Token tokenEntity = checkToken.get();

            // Lấy thời gian tạo token
            Date tokenCreationTime = tokenEntity.getCreateAt();

            // Lấy thời gian hiện tại
            Date currentTime = new Date(System.currentTimeMillis());

            // Tính toán thời gian đã trôi qua từ khi tạo token
            long durationMillis = currentTime.getTime() - tokenCreationTime.getTime();
            long minutesPassed = TimeUnit.MILLISECONDS.toMinutes(durationMillis);
            System.out.println("minutesPassed: " + minutesPassed);

            // Kiểm tra nếu thời gian đã trôi qua vượt quá 2 phút
            if (minutesPassed > 2) {
                // Token hết hạn
                String message_checkToken="Đường link đã hết hạn, quý khách vui lòng thao tác lại";
                model.addAttribute("message_checkToken",message_checkToken);
                return "/forgotPassword";
                // Xử lý logic khi token hết hạn
            } else {
                // Token còn hiệu lực
                Integer userId=tokenEntity.getUser().getUserId();
                model.addAttribute("userId", userId);
                return "/resetForgotPassword";
                // Xử lý logic khi token còn hiệu lực
            }


        }else{
            //token sai
            System.out.println("Token sai");
            String message_checkToken="Đường link không hợp lệ, quý khách vui lòng kiểm tra lại";
            model.addAttribute("message_checkToken",message_checkToken);
            return "/forgotPassword";
        }
    }
    @GetMapping("resetForgotPassword")
    public String resetForgotPassword(Model model){
        return "resetForgotPassword";
    }
    @PostMapping("checkResetForgotPassword")
    public String checkResetForgotPassword(@RequestParam("password") String password, Model model, @RequestParam("rePassword") String rePassword,@RequestParam("userId") Integer userId ) {
        System.out.println("userId: " + userId);
        if (!Objects.equals(rePassword, password)) {
            String message_checkpassword = "Password and Re-Password are different!";
            model.addAttribute("message_checkpassword", message_checkpassword);
            return "resetForgotPassword";
        }
        String encodedPassword = passwordEncoder.encode(password);

        Optional<User> userCheckOptional = userService.getUserById(userId);
        if (userCheckOptional.isPresent()) {
            User user = userCheckOptional.get();
            user.setPassword(encodedPassword);
            String message_checkpassword = "Đổi mật khẩu thành công, Vui lòng đăng nhập lại!";
            model.addAttribute("message_checkpassword", message_checkpassword);
            userService.add(user);

            return "/loginPage";
        } else {
            return "redirect:/loginPage";

            // Xử lý trường hợp không tìm thấy người dùng với userId cung cấp
        }

    }

    //Register Teacher
    @GetMapping("registerTeacher")
    public String registerTeacher(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            // Người dùng chưa đăng nhập
            return "redirect:/login";
        }
        //lấy thông tin user
        Optional<User> user1 = userService.getUserByUsername(authentication.getName());
        User user = user1.get();
        model.addAttribute("user", user);
        //
        TeacherRegister teacherRegister=new TeacherRegister();
        model.addAttribute("teacherRegister", teacherRegister);
        return "/home/users/registerTeacher";
    }
    @PostMapping("registerTeacher")
    public String registerTeacher(@Valid @ModelAttribute TeacherRegister teacherRegister, BindingResult bindingResult, Model model, @RequestParam("username") String username) {
        if(bindingResult.hasErrors()){
            System.out.println("BBBB");
            return "/home/users/registerTeacher";
        }
        Optional<User>user1 = userService.getUserByUsername(username);
        teacherRegister.setUser(user1.get());
        teacherRegisterService.save(teacherRegister);

        //gửi mail thông báo
        emailService.sendMailNotiRegisterTeacherStatus(user1.get().getEmail());
        return "redirect:/";
    }
    @PostMapping("updateUser")
    public String Update(User user, Model model) {
        userService.add(user);
        return "redirect:/registerTeacher";
    }
}
