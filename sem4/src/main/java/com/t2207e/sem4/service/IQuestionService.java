package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.Question;

import java.util.List;
import java.util.Optional;

public interface IQuestionService {
    List<Question> getAllQuestion();
    Optional<Question> getQuestionById(Integer id);
    void add(Question question);
    void deleteById(Integer id);
    List<Question> getQuestionsByExamAndStatus(Exam exam, Integer status);
}
