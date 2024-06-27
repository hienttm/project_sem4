package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Exam;
import com.t2207e.sem4.entity.Question;
import com.t2207e.sem4.repository.IQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService{
    private final IQuestionRepository questionRepository;

    @Autowired
    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(Integer id) {
        return questionRepository.findById(id);
    }

    @Override
    public void add(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void deleteById(Integer id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> getQuestionsByExamAndStatus(Exam exam, Integer status) {
        return questionRepository.getQuestionsByExamAndStatus(exam, status);
    }
}
