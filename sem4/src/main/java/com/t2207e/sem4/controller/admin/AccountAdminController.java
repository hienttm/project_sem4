package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.HelperService;
import com.t2207e.sem4.service.RoleService;
import com.t2207e.sem4.service.UserRoleService;
import com.t2207e.sem4.service.UserService;
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
import java.util.Optional;

@Controller
@RequestMapping("/admin/account")
public class AccountAdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final HelperService helperService;

    public AccountAdminController(UserService userService, RoleService roleService, UserRoleService userRoleService, HelperService helperService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.helperService = helperService;
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

            List<Role> roles = roleService.getAllRole();
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
            for (String roleName : rolesUpdate) {
                Optional<UserRole> userRoleOptional = userRoleService.getUserRoleByUserAndRole_RoleName(user, roleName);
                if(userRoleOptional.isEmpty()){
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
