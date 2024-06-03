package com.t2207e.sem4.service;

import com.t2207e.sem4.entity.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface IUserAnswerService {
    List<UserAnswer> getAllUserAnswer();
    Optional<UserAnswer> getUserAnswerById(Integer id);
    void add(UserAnswer userAnswer);
    void deleteById(Integer id);
}
