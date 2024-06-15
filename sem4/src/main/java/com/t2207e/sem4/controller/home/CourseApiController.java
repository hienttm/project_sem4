package com.t2207e.sem4.controller.home;

import com.t2207e.sem4.dto.OrderDetailByUserDTO;
import com.t2207e.sem4.entity.Chapter;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.ChapterService;
import com.t2207e.sem4.service.CourseService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseApiController {
    private final UserService userService;
    private final CourseService courseService;
    private final ChapterService chapterService;

    public CourseApiController(UserService userService, CourseService courseService, ChapterService chapterService) {
        this.userService = userService;
        this.courseService = courseService;
        this.chapterService = chapterService;
    }

    @GetMapping("/getVideo")
    public String GetVideo(@RequestParam int id, @RequestParam int index){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userService.getUserByUsername(username);
            Optional<Chapter> chapterOptional = chapterService.getChapterById(id);
            if (userOptional.isPresent() && chapterOptional.isPresent()) {
                User user = userOptional.get();
                Chapter chapter = chapterOptional.get();
                List<OrderDetailByUserDTO> orderDetailByUserDTOs = courseService.GetOrderDetailByUserIdProcedure(user.getUserId());

                boolean checkBuy = orderDetailByUserDTOs.stream().anyMatch(dto -> dto.getCourseId() == chapter.getCourse().getCourseId());

                if(checkBuy || index<chapter.getCourse().getFreeNumbers()){
                    return chapter.getChapterVideo();
                }
                else {
                    return "The user has not purchased the course";
                }
            }
            else {
                return "Video does not exist";
            }
        }
        return "User is not authenticated";
    }
}
