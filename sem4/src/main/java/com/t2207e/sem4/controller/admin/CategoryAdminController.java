package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.Category;
import com.t2207e.sem4.entity.Course;
import com.t2207e.sem4.entity.Role;
import com.t2207e.sem4.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    @GetMapping("list")
    public String course(Model model) {
        List<Category> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "admin/category/index";
    }

    @GetMapping("add")
    public String add(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/add";
    }

    @PostMapping("add")
    public String addCategories(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/category/list";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Optional<Category> categoryOptional = categoryService.getCategoryId(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            model.addAttribute("category", category);
            return "admin/category/edit";
        }
        return "redirect:/admin/category/list";
    }

    @PostMapping("edit")
    public String edit( @ModelAttribute("updateCategory") Category updateCategory) {
        categoryService.updateCategory( updateCategory);
        return "redirect:/admin/category/list";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id) {
       Optional<Category> categoryOptional = categoryService.getCategoryId(id);
       if (categoryOptional.isPresent()) {
           Category category = categoryOptional.get();
           if(category.getStatus() == 1){
               category.setStatus(2);
           } else {
               category.setStatus(1);
           }
           category.setCreateAt(new Date(System.currentTimeMillis()));
           categoryService.addCategory(category);
       }
       return "redirect:/admin/category/list";
    }
}
