package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.CourseType;
import com.t2207e.sem4.service.CourseTypeService;
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

@Controller
@RequestMapping("admin/courseType")
public class CourseTypeAdminController {
    private final CourseTypeService courseTypeService;

    public CourseTypeAdminController(CourseTypeService courseTypeService) {
        this.courseTypeService = courseTypeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("list")
    public String GetList(Model model){
        List<CourseType> courseTypes = courseTypeService.getAllCourseType();
        model.addAttribute("courseTypes", courseTypes);
        return "admin/courseTypes/index";
    }

    @GetMapping("add")
    public String add(Model model){
        CourseType courseType = new CourseType();
        model.addAttribute("courseType", courseType);
        return "admin/courseTypes/add";
    }

    @PostMapping("add")
    public String save(@Valid @ModelAttribute CourseType courseType, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "admin/courseTypes/add";
        }
        if(courseTypeService.existsByTypeName(courseType.getTypeName())){
            String exception = "Type Name has been existed";
            model.addAttribute("exception", exception);
            return "admin/courseTypes/add";
        }
        courseTypeService.add(courseType);
        return "redirect:/admin/courseType/list";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable int id, Model model){
        Optional<CourseType> courseTypeOptional = courseTypeService.getCourseTypeById(id);
        if(courseTypeOptional.isPresent()){
            CourseType courseType = courseTypeOptional.get();
            model.addAttribute("courseType", courseType);
            return "admin/courseTypes/edit";
        }
        return "redirect:/admin/courseTypes/list";
    }

    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute CourseType courseType, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "admin/courseTypes/edit";
        }
        System.out.println(courseTypeService.getCourseTypeById(courseType.getCourseTypeId()).get().getTypeName());
        if(courseTypeService.existsByTypeName(courseType.getTypeName()) && !Objects.equals(courseType.getTypeName(), courseTypeService.getCourseTypeById(courseType.getCourseTypeId()).get().getTypeName())){
            String exception = "Type Name has been existed";
            model.addAttribute("exception", exception);
            return "admin/courseTypes/edit";
        }
        courseTypeService.add(courseType);
        return "redirect:/admin/courseType/list";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<CourseType> courseTypeOptional = courseTypeService.getCourseTypeById(id);
        if(courseTypeOptional.isPresent()){
            CourseType courseType = courseTypeOptional.get();
            if(courseType.getStatus() == 1){
                courseType.setStatus(2);
            }
            else {
                courseType.setStatus(1);
            }
            courseType.setCreateAt(new Date(System.currentTimeMillis()));
            courseTypeService.add(courseType);
        }
        return "redirect:/admin/courseType/list";
    }
}
