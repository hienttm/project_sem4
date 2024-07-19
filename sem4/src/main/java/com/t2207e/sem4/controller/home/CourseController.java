package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.CourseDTO;
import com.t2207e.sem4.dto.ExamUserDTO;
import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.entity.*;
import com.t2207e.sem4.service.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final ChapterService chapterService;
    private final UserService userService;
    private final ExamService examService;
    private final UserAnswerService userAnswerService;
    private final OrderDetailService orderDetailService;
    private final ReviewService reviewService;
    private final TeacherRegisterService teacherRegisterService;

    public CourseController(CourseService courseService, ChapterService chapterService, UserService userService, ExamService examService, UserAnswerService userAnswerService, OrderDetailService orderDetailService, ReviewService reviewService, TeacherRegisterService teacherRegisterService) {
        this.courseService = courseService;
        this.chapterService = chapterService;
        this.userService = userService;
        this.examService = examService;
        this.userAnswerService = userAnswerService;
        this.orderDetailService = orderDetailService;
        this.reviewService = reviewService;
        this.teacherRegisterService = teacherRegisterService;
    }

    @GetMapping("/list/{page}")
    public String List(@RequestParam(value = "searchInput", required = false) String searchInput, @RequestParam(value = "course_type_name", required = false) String course_type_name, @PathVariable Integer page, Model model){
        model.addAttribute("searchInput", searchInput);
        model.addAttribute("course_type_name", course_type_name);

        String searchName = "";
        if (searchInput != null) {
            searchName = searchInput;
        }

        //Total Page
        Integer totalCourse = courseService.countCoursesByCourseNameContainingAndStatusAndCourseType_TypeNameContaining(searchName, 1, (course_type_name == null?"":course_type_name));
        double totalPage = Math.ceil((double) totalCourse / 6);

        model.addAttribute("totalPage", (int) totalPage);

        //Get List
        model.addAttribute("page", page);
        List<CourseDTO> courseDTOs = courseService.GetAllCourseProcedurePaging(searchName, page, 6, (course_type_name == null?"":course_type_name));
        model.addAttribute("courseDTOs", courseDTOs);
        return "home/courses/list";
    }

    @GetMapping("/detail/{id}")
    public String Detail(@PathVariable Integer id, Model model){

        String message = (String) model.getAttribute("message");
        model.addAttribute("message", message);

        Optional<Course> courseOptional = courseService.getCourseById(id);
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            model.addAttribute("course", course);

            Optional<TeacherRegister> teacherRegisterOptional = teacherRegisterService.getTeacherRegisterByUser_UserId(course.getUser().getUserId());
            if(teacherRegisterOptional.isPresent()){
                TeacherRegister teacherRegister = teacherRegisterOptional.get();
                model.addAttribute("teacherRegister", teacherRegister);
            }

            List<Course> coursesHaveSameName = courseService.getCoursesByUser(course.getUser());
            model.addAttribute("coursesHaveSameName", coursesHaveSameName);

            List<Chapter> chapters = chapterService.getChaptersByCourseAndStatus(course, 1);
            model.addAttribute("chapters", chapters);

            List<Review> reviews = reviewService.getReviewsByCourseId(course.getCourseId());
            model.addAttribute("reviews", reviews);

            List<Exam> examCounts = examService.getExamsByCourse(course);
            model.addAttribute("countExam", examCounts.size());

            final Double[] starTotal = new Double[1];
            starTotal[0] = 0.0;
            reviews.forEach(review -> {
                starTotal[0] += review.getStarNumber();
            });

            Double averageStar = starTotal[0] / (reviews.size());
            if(!averageStar.isNaN()){
                BigDecimal bd = new BigDecimal(averageStar).setScale(1, RoundingMode.HALF_UP);
                Double averageStarTrue = bd.doubleValue();
                model.addAttribute("averageStar", averageStarTrue);
            }
            else {
                model.addAttribute("averageStar", averageStar);
            }

            Integer countUserBuyCourse = orderDetailService.countOrderDetailsByCourse_CourseId(course.getCourseId());
            model.addAttribute("countUserBuyCourse", countUserBuyCourse);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
            if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
                String username = authentication.getName();
                Optional<User> userOptional = userService.getUserByUsername(username);
                if(userOptional.isPresent()){
                    User user = userOptional.get();
                    model.addAttribute("user", user);

                    List<Review> reviewsByUser = reviewService.getReviewsByUserAndCourse(user, course);
                    if(!reviewsByUser.isEmpty()){
                        model.addAttribute("reviewByUser", reviewsByUser.getFirst());
                    }

                    List<OrderDetailByUserDTO> orderDetailByUserDTOs = courseService.GetOrderDetailByUserIdProcedure(user.getUserId());

                    boolean checkBuy = orderDetailByUserDTOs.stream().anyMatch(dto -> dto.getCourseId() == course.getCourseId());

                    model.addAttribute("checkBuy", checkBuy);
                    if(!checkBuy && course.getStatus()!=1){
                        return "redirect:/course/list/1";
                    }

                    List<Exam> exams = examService.getExamsByCourseAndStatus(course, 1);

                    List<ExamUserDTO> examUserDTOs = new ArrayList<>();
                    for (Exam exam : exams) {
                        ExamUserDTO examUserDTO = new ExamUserDTO();
                        examUserDTO.setExam(exam);
                        List<UserAnswer> userAnswerByUserAndExamId = userAnswerService.getUserAnswersByUserAndExam_ExamId(user, exam.getExamId());
                        if(userAnswerByUserAndExamId.isEmpty()){
                            examUserDTO.setCountAnswers(-1);
                            examUserDTO.setCountTrueAnswers(-1);
                        }
                        else {
                            final int[] countTrueAnswer = {0};
                            userAnswerByUserAndExamId.forEach(userAnswer -> {
                                if(userAnswer.getAnswer()!=null){
                                    if(userAnswer.getAnswer().isTof()){
                                        countTrueAnswer[0]++;
                                    }
                                }
                            });
                            examUserDTO.setCountAnswers(userAnswerByUserAndExamId.size());
                            examUserDTO.setCountTrueAnswers(countTrueAnswer[0]);
                        }

                        examUserDTOs.add(examUserDTO);
                    }

                    model.addAttribute("examUserDTOs", examUserDTOs);
                }
            }
            else{
                if(course.getStatus()!=1){
                    return "redirect:/course/list/1";
                }
            }

            return "home/courses/detail";
        }
        return "redirect:/course/list/1";
    }
}
