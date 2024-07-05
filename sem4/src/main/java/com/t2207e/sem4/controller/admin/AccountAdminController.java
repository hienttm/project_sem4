package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin/account")
public class AccountAdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final HelperService helperService;
    private final TeacherRegisterService teacherRegisterService;

    public AccountAdminController(UserService userService, RoleService roleService, UserRoleService userRoleService, HelperService helperService, TeacherRegisterService teacherRegisterService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.helperService = helperService;
        this.teacherRegisterService = teacherRegisterService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("list")
    public String GetList(Model model){
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/accounts/index";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable int id, Model model){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            model.addAttribute("user", user);

            List<Role> roles = roleService.getRolesByStatus(1);
            model.addAttribute("roles", roles);

            List<UserRole> userRoles = userRoleService.getUserRolesByUser(user);

            List<Role> rolesByUser = userRoles.stream().map(UserRole::getRole).toList();
            model.addAttribute("rolesByUser", rolesByUser);

            return "admin/accounts/detail";
        }
        return "redirect:/admin/account/list";
    }

    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, @RequestBody(required = false) MultipartFile fileImage,@RequestParam(name = "roleUpdate", required = false) List<String> rolesUpdate) throws IOException {
        if(bindingResult.hasErrors()){
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roles", roles);
            List<UserRole> userRoles = userRoleService.getUserRolesByUser(user);
            List<Role> rolesByUser = userRoles.stream().map(UserRole::getRole).toList();
            model.addAttribute("rolesByUser", rolesByUser);
            return "admin/accounts/detail";
        }
        //File Update
        if(!fileImage.isEmpty()){
            user.setImage(helperService.ConvertFromImageToBase64String(fileImage));
        }
        userService.add(user);

        //Role Update
        if(rolesUpdate.isEmpty()){
            List<Role> roles = roleService.getAllRole();
            model.addAttribute("roles", roles);
            List<UserRole> userRoles = userRoleService.getUserRolesByUser(user);
            List<Role> rolesByUser = userRoles.stream().map(UserRole::getRole).toList();
            model.addAttribute("rolesByUser", rolesByUser);

            model.addAttribute("exceptionRole", "Role cannot be null");
            return "admin/accounts/detail";
        }
        else {
            List<UserRole> currentRoles = userRoleService.getUserRolesByUser(user);

            List<String> currentRoleNames = currentRoles.stream()
                    .map(userRole -> userRole.getRole().getRoleName())
                    .toList();

            for (UserRole userRole : currentRoles) {
                if (!rolesUpdate.contains(userRole.getRole().getRoleName())) {
                    userRoleService.deleteById(userRole.getId());
                }
            }

            for (String roleName : rolesUpdate) {
                if (!currentRoleNames.contains(roleName)) {
                    if (Objects.equals(roleName, "ROLE_TEACHER")) {
                        Optional<TeacherRegister> teacherRegisterOptional = teacherRegisterService.getTeacherRegisterByUser_UserId(user.getUserId());
                        if (teacherRegisterOptional.isEmpty()){
                            TeacherRegister teacherRegister = new TeacherRegister();
                            teacherRegister.setStatus(1);
                            teacherRegister.setUser(user);
                            teacherRegisterService.save(teacherRegister);
                        }
                    }
                    UserRole userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(roleService.getRoleByRoleName(roleName).get());
                    userRoleService.add(userRole);
                }
            }
        }

        return "redirect:/admin/account/list";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getStatus() == 1){
                user.setStatus(2);
            }
            else {
                user.setStatus(1);
            }
            user.setUpdateAt(new Date(System.currentTimeMillis()));
            userService.add(user);
        }
        return "redirect:/admin/account/list";
    }

    @GetMapping("resetPassword/{id}")
    public String resetPassword(@PathVariable int id){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword("$2a$12$2yPxxyf0QlS7eCzb0NtVSufCx1xq/BPv6gkXT8Vz.Lo.gJLS3Khzu");
            user.setUpdateAt(new Date(System.currentTimeMillis()));
            userService.add(user);
        }
        return "redirect:/admin/account/list";
    }
}
