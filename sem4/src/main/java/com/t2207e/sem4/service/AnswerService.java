package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Answer;
import com.t2207e.sem4.repository.IAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService implements IAnswerService{
    private final IAnswerRepository answerRepository;

    @Autowired
    public AnswerService(IAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> getAllAnswer() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<Answer> getAnswerById(Integer id) {
        return answerRepository.findById(id);
    }

    @Override
    public void add(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public void deleteById(Integer id) {
        answerRepository.deleteById(id);
    }
}
