package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.dto.TeacherSalaryDTO;
import com.t2207e.sem4.entity.Order;
import com.t2207e.sem4.entity.Policy;
import com.t2207e.sem4.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final PolicyService policyService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final CourseService courseService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;

    public AdminController(PolicyService policyService, RoleService roleService, UserRoleService userRoleService, CourseService courseService, OrderService orderService, OrderDetailService orderDetailService, UserService userService) {
        this.policyService = policyService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.courseService = courseService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("dashboard")
    public String dashboard(Model model){
        List<Policy> policies = policyService.getAllPolicy();
        Optional<Policy> optionalPolicy = policies.stream().findFirst();

        if (optionalPolicy.isPresent()) {
            model.addAttribute("policy", optionalPolicy.get());
        } else {
            model.addAttribute("policy", new Policy());
        }

        Integer countUser = userRoleService.countUserRoleByRole_RoleName("ROLE_USER");
        model.addAttribute("countUser", countUser);

        Integer countTeacher = userRoleService.countUserRoleByRole_RoleName("ROLE_TEACHER");
        model.addAttribute("countTeacher", countTeacher);

        Integer countCourse = courseService.countCoursesByStatus(1);
        model.addAttribute("countCourse", countCourse);

        List<Order> orders = orderService.getOrdersByStatus(1);
        AtomicReference<Double> totalRevenue = new AtomicReference<>(0.0);
        orders.forEach(order -> {
            totalRevenue.updateAndGet(v -> v + order.getTotal());
        });
        model.addAttribute("totalRevenue", totalRevenue.get());

        List<RevenueDTO> revenueDays = orderService.TotalDayProcedure();
        List<RevenueDTO> revenueWeeks = orderService.TotalWeekProcedure();
        List<RevenueDTO> revenueMonths = orderService.TotalMonthProcedure();
        List<RevenueDTO> revenueYears = orderService.TotalYearProcedure();

        model.addAttribute("revenueDays", revenueDays);
        model.addAttribute("revenueWeeks", revenueWeeks);
        model.addAttribute("revenueMonths", revenueMonths);
        model.addAttribute("revenueYears", revenueYears);

        List<TeacherSalaryDTO> teacherSalaryDTOs = userService.GetAllSalaryTeacher();
        model.addAttribute("teacherSalaryDTOs", teacherSalaryDTOs);

        return "admin/dashboard";
    }

    @PostMapping("policy/edit")
    public String editPolicy(@Valid @ModelAttribute Policy policy, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult);
            redirectAttributes.addFlashAttribute("validatePolicy", "Policy error");
            return "redirect:/admin/dashboard";
        }
        policy.setUpdateAt(new Date(System.currentTimeMillis()));
        policyService.add(policy);
        return "redirect:/admin/dashboard";
    }
}
