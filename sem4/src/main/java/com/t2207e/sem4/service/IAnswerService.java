package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface IAnswerService {
    List<Answer> getAllAnswer();
    Optional<Answer> getAnswerById(Integer id);
    void add(Answer answer);
    void deleteById(Integer id);
}
