package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.entity.Answer;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.Question;
import com.t2207e.sem4.entity.User;
import com.t2207e.sem4.service.AnswerService;
import com.t2207e.sem4.service.ExamService;
import com.t2207e.sem4.service.QuestionService;
import com.t2207e.sem4.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("roleTeacher/api/question")
public class QuestionApiTeacherController {
    private final ExamService examService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    public QuestionApiTeacherController(ExamService examService, QuestionService questionService, AnswerService answerService, UserService userService) {
        this.examService = examService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.userService = userService;
    }

    @PostMapping("add/{examId}")
    public String addApi(@PathVariable int examId, @RequestParam(required = false) String question, @RequestParam(required = false) List<String> answer, @RequestParam(required = false) Integer trueAnswer){
        if(question==null || question.isEmpty())
            return "Question can't be null";
        if(answer.get(0) == null || answer.get(0).isEmpty() || answer.get(1) == null || answer.get(1).isEmpty() || answer.get(2) == null || answer.get(2).isEmpty() || answer.get(3) == null || answer.get(3).isEmpty())
            return "The Answer can't be null";
        Set<String> uniqueAnswers = new HashSet<>(answer);
        if (uniqueAnswers.size() < 4) {
            return "The answers must be different";
        }
        if(trueAnswer==null)
            return "Please choose the true answer";
        Optional<Exam> examOptional = examService.getExamById(examId);
        if(examOptional.isPresent()){
            Question questionAdd = new Question();
            questionAdd.setQuestion(question);
            questionAdd.setExam(examOptional.get());
            questionAdd.setStatus(1);
            questionService.add(questionAdd);

            for(int i=0; i<4; i++){
                Answer answerAdd = new Answer();
                answerAdd.setQuestion(questionAdd);
                answerAdd.setAnswerContent(answer.get(i));
                answerAdd.setTof(trueAnswer == i+1);
                answerService.add(answerAdd);
            }

            return "Success";
        }
        return "error ExamId";
    }

    @GetMapping("hidden/{id}")
    public String hiddenQuestion(@PathVariable int id){
        Optional<Question> questionOptional = questionService.getQuestionById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && questionOptional.isPresent()) {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username).get();
            Question question = questionOptional.get();
            if(question.getExam().getCourse().getUser() == user){
                question.setStatus(2);
                questionService.add(question);
                return "Success";
            }
            return "You don't have the right to delete";
        }
        return "Error";
    }
}
