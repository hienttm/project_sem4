package com.t2207e.sem4.controller.teacher;

import com.t2207e.sem4.entity.Answer;
import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.Question;
import com.t2207e.sem4.service.AnswerService;
import com.t2207e.sem4.service.ExamService;
import com.t2207e.sem4.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("roleTeacher/api/question")
public class QuestionApiTeacherController {
    private final ExamService examService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionApiTeacherController(ExamService examService, QuestionService questionService, AnswerService answerService) {
        this.examService = examService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("add/{examId}")
    public String addApi(@PathVariable int examId, @RequestParam(required = false) String question, @RequestParam(required = false) List<String> answer, @RequestParam(required = false) Integer trueAnswer){
        if(question==null)
            return "Question can't be null";
        if(answer.get(0) == null || answer.get(1) == null || answer.get(2) == null || answer.get(3) == null)
            return "The Answer can't be null";
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
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            question.setStatus(2);
            questionService.add(question);
            return "Success";
        }
        return "Error Question";
    }
}
