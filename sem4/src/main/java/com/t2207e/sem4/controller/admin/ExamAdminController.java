package com.t2207e.sem4.controller.admin;

import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/exam")
public class ExamAdminController {
    private final ExamService examService;

    public ExamAdminController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("list")
    public String getList(Model model){
        List<Exam> exams = examService.getAllExam();
        model.addAttribute("exams", exams);
        return "admin/exams/index";
    }

    @GetMapping("hidden/{id}")
    public String hidden(@PathVariable int id){
        Optional<Exam> examOptional = examService.getExamById(id);
        if(examOptional.isPresent()){
            Exam exam = examOptional.get();
            if(exam.getStatus()==1){
                exam.setStatus(2);
            }
            else {
                exam.setStatus(1);
            }
            examService.add(exam);
        }
        return "redirect:/admin/exam/list";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable int id, Model model){
        Optional<Exam> examOptional = examService.getExamById(id);
        if(examOptional.isPresent()){
            Exam exam = examOptional.get();
            model.addAttribute("exam", exam);
        }
        return "admin/exams/detail";
    }
}
