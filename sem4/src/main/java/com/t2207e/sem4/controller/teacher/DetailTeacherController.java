package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.dto.RevenueDTO;
import com.t2207e.sem4.entity.OrderDetail;
import com.t2207e.sem4.entity.TeacherRegister;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.OrderDetailService;
import com.t2207e.sem4.service.TeacherRegisterService;
import com.t2207e.sem4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("roleTeacher")
public class DetailTeacherController {
    private final UserService userService;
    private final TeacherRegisterService teacherRegisterService;
    private final OrderDetailService orderDetailService;

    public DetailTeacherController(UserService userService, TeacherRegisterService teacherRegisterService, OrderDetailService orderDetailService) {
        this.userService = userService;
        this.teacherRegisterService = teacherRegisterService;
        this.orderDetailService = orderDetailService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("detail")
    public String detail(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Optional<TeacherRegister> teacherRegisterOptional = teacherRegisterService.getTeacherRegisterByUser_UserId(user.getUserId());
            if(teacherRegisterOptional.isPresent()){
                TeacherRegister teacherRegister = teacherRegisterOptional.get();
                model.addAttribute("teacher", teacherRegister);

                List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByCourse_User_UserId(teacherRegister.getUser().getUserId());
                model.addAttribute("orderDetails", orderDetails);

                List<RevenueDTO> revenueDays = orderDetailService.RevenueChartDayTeacherProcedure(teacherRegister.getUser().getUserId());
                List<RevenueDTO> revenueWeeks = orderDetailService.RevenueChartWeekTeacherProcedure(teacherRegister.getUser().getUserId());
                List<RevenueDTO> revenueMonths = orderDetailService.RevenueChartMonthTeacherProcedure(teacherRegister.getUser().getUserId());
                List<RevenueDTO> revenueYears = orderDetailService.RevenueChartYearTeacherProcedure(teacherRegister.getUser().getUserId());

                model.addAttribute("revenueDays", revenueDays);
                model.addAttribute("revenueWeeks", revenueWeeks);
                model.addAttribute("revenueMonths", revenueMonths);
                model.addAttribute("revenueYears", revenueYears);

                return "teacher/detail";
            }
            return "redirect:/";
        }
        return "redirect:/login";
    }


    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute TeacherRegister teacherRegister, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "teacher/detail";
        }
        teacherRegisterService.save(teacherRegister);
        return "redirect:/roleTeacher/detail";
    }
}
