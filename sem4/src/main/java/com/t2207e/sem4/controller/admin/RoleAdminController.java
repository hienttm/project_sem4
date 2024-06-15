package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("admin/role")
@Controller
public class RoleAdminController {
    private final RoleService roleService;

    public RoleAdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("list")
    public String List(Model model){
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        return "admin/roles/index";
    }

    @GetMapping("add")
    public String add(Model model){
        Role role = new Role();
        model.addAttribute("role", role);
        return "admin/roles/add";
    }

    @PostMapping("add")
    public String save(@Valid @ModelAttribute Role role, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "admin/roles/add";
        }
        if(roleService.existsByRoleName(role.getRoleName())){
            String exception = "Role Name has been existed";
            model.addAttribute("exception", exception);
            return "admin/roles/add";
        }
        roleService.add(role);
        return "redirect:/admin/role/list";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isPresent()){
            Role role = roleOptional.get();
            model.addAttribute("role", role);
            return "admin/roles/edit";
        }
        return "redirect:/admin/role/list";
    }

    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute Role role, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "admin/roles/edit";
        }
        if(roleService.existsByRoleName(role.getRoleName()) && !Objects.equals(role.getRoleName(), roleService.getRoleById(role.getRoleId()).get().getRoleName())){
            String exception = "Role Name has been existed";
            model.addAttribute("exception", exception);
            return "admin/roles/add";
        }
        role.setUpdateAt(new Date(System.currentTimeMillis()));
        roleService.add(role);
        return "redirect:/admin/role/list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id){
        roleService.deleteById(id);
        return "redirect:/admin/role/list";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<Role> roleOptional = roleService.getRoleById(id);
        if(roleOptional.isPresent()){
            Role role = roleOptional.get();
            if(role.getStatus() == 1){
                role.setStatus(2);
            }
            else {
                role.setStatus(1);
            }
            role.setUpdateAt(new Date(System.currentTimeMillis()));
            roleService.add(role);
        }
        return "redirect:/admin/role/list";
    }

}
